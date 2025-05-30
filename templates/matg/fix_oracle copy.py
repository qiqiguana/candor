from pathlib import Path


from .logger import logger
from langchain_ollama import ChatOllama
from jinja2 import Environment, FileSystemLoader
import subprocess
import os
from langchain_core.output_parsers import PydanticOutputParser
from .output_entities import ErrorInfo, InitialTestFile, InspectionResult, Requirements, TestCase, TestCasePlan, TestCases, TestPlan, CuratorReport, OracleAnalysisReport,TestCaseParserResult
from .utils import Analyzer, CoverageProcessor, FileUtils, extract_error_message, extract_source_metadata, merge_code, parse_jacoco_csv,replace_code_block
from langchain.callbacks.streaming_stdout import StreamingStdOutCallbackHandler
import pickle
import tensorflow as tf

from torch.utils.tensorboard import SummaryWriter

# /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/java/original/id_147.java
# python -m matg.main oracle-fixer --data-path /home/qinghua/projects/matg/data/HumanEvalJava/matg/ --relative-source-file-path src/main/java/original/id_147.java --relative-test-file-path src/test/java/original/id_147Test.java --test-command "mvn -f /home/qinghua/projects/matg/data/HumanEvalJava/matg/pom.xml" --generator matg
class OracleFixer:
    def __init__(self,config):
        self.config=config
        self.template_path=config.prompt_template_path/config.generator
        self.data_path=config.data_path
        self.source_file_path=self.config.data_path/self.config.relative_source_file_path
        self.test_file_path=self.config.data_path/self.config.relative_test_file_path
        self.analyzer=Analyzer(self.config)
        self.test_file=FileUtils.read_file(self.test_file_path)
        
        # analyse source code
        self.source_code=FileUtils.read_file(self.source_file_path)
        self.package_name,self.imports,self.class_name=extract_source_metadata(self.source_code)
        self.source_file_numbered=FileUtils.number_lines(self.source_code)
        self.description=FileUtils.extract_description(self.source_code)

        # set up LLMs
        self.llama70b=ChatOllama(model="llama3.1:70b",callbacks=[StreamingStdOutCallbackHandler()],num_predict=10000)            
        self.deepseek=ChatOllama(model="deepseek-r1:70b",callbacks=[StreamingStdOutCallbackHandler()],num_predict=1000)
        
        # set up agents
        max_parsing_attempts=3
        jinja_env=Environment(loader=FileSystemLoader(self.config.prompt_template_path/self.config.generator))
        ## requirement engineer
        self.requirement_engineer_parser=PydanticOutputParser(pydantic_object=Requirements)
        self.requirement_engineer= (self.llama70b | self.requirement_engineer_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.requirement_engineer_system_prompt=jinja_env.get_template("requirement_engineer_system.jinja")
        self.requirement_engineer_user_prompt=jinja_env.get_template("requirement_engineer_user.jinja")
        # test case parser
        self.test_case_parser_parser=PydanticOutputParser(pydantic_object=TestCaseParserResult)
        self.test_case_parser= (self.llama70b | self.test_case_parser_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.test_case_parser_system_prompt=jinja_env.get_template("test_case_parser_system.jinja")
        self.test_case_parser_user_prompt=jinja_env.get_template("test_case_parser_user.jinja")
        ## competitor
        self.competitor_parser=PydanticOutputParser(pydantic_object=OracleAnalysisReport)
        self.competitor= (self.deepseek | self.competitor_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.competitor_system_prompt=jinja_env.get_template("competitor_system.jinja")
        self.competitor_user_prompt=jinja_env.get_template("competitor_user.jinja")
        ## curator
        self.curator_parser=PydanticOutputParser(pydantic_object=CuratorReport)
        self.curator= (self.llama70b | self.curator_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.curator_system_prompt=jinja_env.get_template("curator_system.jinja")
        self.curator_user_prompt=jinja_env.get_template("curator_user.jinja")
        
        # id_class_mapping for HumanEvalJava
        self.id_class_mapping=pickle.load((self.config.data_path/"class_id_mapping.pkl").open("rb"))
        self.id=self.id_class_mapping[self.class_name]
        
        # set up tensorboard writer
        tensorboard_log_dir =  f"tensorboard_logs/{self.id}"
        self.writer= SummaryWriter(tensorboard_log_dir)
        
        # set up team size
        self.team_size=3
        
    def run(self):
        test_nodes=self.analyzer.get_test_nodes(self.test_file_path)

        # get requirements & specifications
        logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT REQUIREMENT ENGINEER: Generating Requirements ----- 🚀 🚀 🚀 🚨🚨🚨 ")
        requirement_engineer_system_prompt=self.requirement_engineer_system_prompt.render()
        requirement_engineer_user_prompt=self.requirement_engineer_user_prompt.render({
            "description":self.description,
            "format_instructions":self.requirement_engineer_parser.get_format_instructions(),
        })
        requiements=self.requirement_engineer.invoke([("system", requirement_engineer_system_prompt), ("user", requirement_engineer_user_prompt)])
        
        for test_node in test_nodes:
            start_line,end_line=test_node.start_point[0],test_node.end_point[0]
            indentation=test_node.start_point[1]
            test_code="\n".join(self.test_file.splitlines()[start_line:end_line+1])
            if "testNothing" in test_code:
                continue
            # parse test case into test prefix and oracle
            logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT TEST CASE PARSER: PARSING TEST CASE ----- 🚀 🚀 🚀 🚨🚨🚨 ")
            test_case_parser_system_prompt=self.test_case_parser_system_prompt.render()
            test_case_parser_user_prompt=self.test_case_parser_user_prompt.render({
                "test_code":test_code,
                "format_instructions":self.test_case_parser_parser.get_format_instructions(),
            })
            test_case_parser_response=self.test_case_parser.invoke([("system", test_case_parser_system_prompt), ("user", test_case_parser_user_prompt)])
            
            logger.info(f"Discussing test case \n{test_code}")
            # start the conversation to fix the oracle
            oracle_analysis_reports=[]
            for i in range(self.team_size):
                # competitor generate new oracle
                logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT COMPETITOR {i}: Checking oracle ----- 🚀 🚀 🚀 🚨🚨🚨 ")
                competitor_system_prompt=self.competitor_system_prompt.render()
                competitor_user_prompt=self.competitor_user_prompt.render({
                    "description":self.description,
                    "test_code":test_code,
                    "test_prefix":test_case_parser_response.test_prefix,
                    "test_oracle":test_case_parser_response.test_oracle,
                    "format_instructions":self.competitor_parser.get_format_instructions(),
                    "requirements":requiements
                })
                try:
                    competitor_response=self.competitor.invoke([("system", competitor_system_prompt), ("user", competitor_user_prompt)])
                    
                except Exception as e:
                    logger.info(f"Competitor {i} failed to check oracle: {e}")
                    competitor_response=OracleAnalysisReport(judgement=True,old_oracle="",new_oracle="",explanation="Sorry, this test case is too complex for me to analyze. Please count me out for the discussion.")
                finally:
                    oracle_analysis_reports.append(competitor_response)
                
            # curator analyze the responses
            logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT CURATOR: Summarising discussions ----- 🚀 🚀 🚀 🚨🚨🚨 ")
            curator_system_prompt=self.curator_system_prompt.render()
            curator_user_prompt=self.curator_user_prompt.render({
                "description":self.description,
                "test_code":test_code,
                "test_prefix":test_case_parser_response.test_prefix,
                "test_oracle":test_case_parser_response.test_oracle,
                "format_instructions":self.curator_parser.get_format_instructions(),
                "oracle_analysis_reports":oracle_analysis_reports
            })
            curator_response=self.curator.invoke([("system", curator_system_prompt), ("user", curator_user_prompt)])
            # check if the oracle is correct
            if curator_response.judgement:
                logger.info(f"Oracle is correct for test case \n{test_code}")
                continue
            else:
                logger.info(f"Oracle is wrong for test case \n{test_code}\n Replacing!")
                # if the oracle is wrong, replace the old oracle with the new oracle
                new_test_code=curator_response.test_case_code
                new_test_file=replace_code_block(self.test_file,new_test_code,indentation,start_line,end_line)
                with open(self.test_file_path,"w") as f:
                    f.write(new_test_file)
#   python -m matg.main oracle-fixer --data-path /home/qinghua/projects/matg/data/HumanEvalJava/matg/ --relative-source-file-path src/main/java/fixed_oracle/id_147.java --relative-test-file-path src/test/java/fixed_oracle/id_147Test.java --test-command "mvn -f /home/qinghua/projects/matg/data/HumanEvalJava/matg/pom.xml test -Dtest="fixed_oracle.*Test"" --generator matg