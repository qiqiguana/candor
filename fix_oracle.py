from pathlib import Path

from .logger import logger
from langchain_ollama import ChatOllama
from jinja2 import Environment, FileSystemLoader
from langchain_core.output_parsers import PydanticOutputParser
from langchain_core.output_parsers.json import parse_json_markdown
from langchain_core.exceptions import OutputParserException
from .output_entities import Requirements, CuratorReport, OracleAnalysisReport, TestCaseParserResult
from .utils import Analyzer, FileUtils, extract_source_metadata, replace_code_block
from langchain_core.callbacks import StreamingStdOutCallbackHandler
import pickle
import csv

class RobustPydanticOutputParser(PydanticOutputParser):
    """PydanticOutputParser that handles JSON wrapped in markdown fences or prose."""
    def parse(self, text):
        try:
            return super().parse(text)
        except OutputParserException:
            try:
                json_obj = parse_json_markdown(text)
                return self.pydantic_object(**json_obj)
            except Exception:
                raise


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
        if self.config.doc_file: 
            # load external doc file if any, e.g. for Leetcode
            doc_file_path=self.config.data_path/self.config.doc_file
            fname=Path(self.source_file_path).name
            doc_file=pickle.load(doc_file_path.open("rb"))
            docstring = doc_file[fname]["description"]
        else:
            # extract doctring from source file, e.g. for HumanEvalJava
            focal_method,docstring=FileUtils.parse_source_file(self.source_file_path)
        self.description=docstring
        self.config.test_command=self.config.test_command.replace("jacoco:report",f"-Dtest={self.class_name}Test jacoco:report") # test only one test file to save time
        # set up LLMs
        self.llama70b=ChatOllama(model="llama3.1:70b",callbacks=[StreamingStdOutCallbackHandler()],num_predict=10000)            
        self.deepseek=ChatOllama(model="deepseek-r1:70b",callbacks=[StreamingStdOutCallbackHandler()],num_predict=2000)
        
        # set up agents
        max_parsing_attempts=3
        jinja_env=Environment(loader=FileSystemLoader(self.config.prompt_template_path/self.config.generator))
        ## requirement engineer
        self.requirement_engineer_parser=RobustPydanticOutputParser(pydantic_object=Requirements)
        self.requirement_engineer= (self.llama70b | self.requirement_engineer_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.requirement_engineer_system_prompt=jinja_env.get_template("requirement_engineer_system.jinja")
        self.requirement_engineer_user_prompt=jinja_env.get_template("requirement_engineer_user.jinja")
        # test case parser
        self.test_case_parser_parser=RobustPydanticOutputParser(pydantic_object=TestCaseParserResult)
        self.test_case_parser= (self.llama70b | self.test_case_parser_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.test_case_parser_system_prompt=jinja_env.get_template("test_case_parser_system.jinja")
        self.test_case_parser_user_prompt=jinja_env.get_template("test_case_parser_user.jinja")
        ## competitor
        self.competitor_parser=RobustPydanticOutputParser(pydantic_object=OracleAnalysisReport)
        self.competitor_system_prompt=jinja_env.get_template("competitor_system.jinja")
        self.competitor_user_prompt=jinja_env.get_template("competitor_user.jinja")
        ## competitor summariser
        self.competitor_summariser_parser=RobustPydanticOutputParser(pydantic_object=OracleAnalysisReport)
        self.competitor_summariser=(self.llama70b| self.competitor_summariser_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.competitor_summariser_system_prompt=jinja_env.get_template("competitor_summariser_system.jinja")
        self.competitor_summariser_user_prompt=jinja_env.get_template("competitor_summariser_user.jinja")
        ## curator
        self.curator_parser=RobustPydanticOutputParser(pydantic_object=CuratorReport)
        self.curator= (self.llama70b | self.curator_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.curator_system_prompt=jinja_env.get_template("curator_system.jinja")
        self.curator_user_prompt=jinja_env.get_template("curator_user.jinja")
        
        # id_class_mapping
        self.id_class_mapping=pickle.load((self.config.data_path/"class_id_mapping.pkl").open("rb"))
        self.id=self.id_class_mapping[self.class_name]
        
        # set up team size
        self.team_size=3
        
        # save curator report result
        csv_file_path = self.config.data_path / "curator_report.csv"
        self.csv_file= csv_file_path.open(mode="a", newline="",encoding="utf-8")
        self.csv_writer=csv.writer(self.csv_file)
        # csv_file.writerow([ "test_file_path", "test_method_name", "judgement", "test_case_code" ])

    def run(self):
        # backup the code
        FileUtils.backup_code(self.test_file_path)
        try:
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
                test_prefix, assertORexception, oracle_text, test_prefix_wo_oracle,test_method_name=FileUtils.parse_test_method(self.test_file_path,test_code)
                full_test_name=f'{self.package_name}.{self.class_name}::{test_method_name}'
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
                    competitor_response=self.deepseek.invoke([("system", competitor_system_prompt), ("user", competitor_user_prompt)])
                    logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT INTERPRETER {i}: Extracting structured oracle evaluation ----- 🚀 🚀 🚀 🚨🚨🚨 ")
                    competitor_summariser_system_prompt=self.competitor_summariser_system_prompt.render()
                    competitor_summariser_user_prompt=self.competitor_summariser_user_prompt.render({
                        "thoughts":competitor_response,
                        "test_code":test_code,
                        "format_instructions":self.competitor_summariser_parser.get_format_instructions(),
                    })
                    competitor_response=self.competitor_summariser.invoke([("system", competitor_summariser_system_prompt), ("user", competitor_summariser_user_prompt)])
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
                self.csv_writer.writerow([ self.test_file_path, full_test_name, curator_response.judgement, curator_response.test_case_code ])
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
                
            self.csv_file.close()
        except Exception as e:
            logger.error(f"Error in oracle fixer: {self.test_file_path} \n{e}")
            FileUtils.revert(self.test_file_path)