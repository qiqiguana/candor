from pathlib import Path
from .logger import logger
from langchain_ollama import ChatOllama
from jinja2 import Environment, FileSystemLoader
import subprocess
import os
from langchain_core.output_parsers import PydanticOutputParser
from .output_entities import ErrorInfo, InitialTestFile, InspectionResult, Requirements, TestCase, TestCasePlan, TestCases, TestPlan
from .utils import Analyzer, CoverageProcessor, FileUtils, extract_error_message, extract_source_metadata, merge_code, parse_jacoco_csv
from langchain.callbacks.streaming_stdout import StreamingStdOutCallbackHandler
import pickle
import tensorflow as tf

from torch.utils.tensorboard import SummaryWriter

# /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/java/original/id_147.java
class TestCaseGenerator:
    def __init__(self, config):
        self.config=config
        self.template_path=config.prompt_template_path/config.generator
        self.data_path=config.data_path
        self.source_file_path=self.config.data_path/self.config.relative_source_file_path
        self.test_file_path=self.config.data_path/self.config.relative_test_file_path
        
        # set up analyzer
        self.analyzer=Analyzer(self.config)
        # analyse source code
        self.source_code=FileUtils.read_file(self.source_file_path)
        self.package_name,self.imports,self.class_name=extract_source_metadata(self.source_code)
        self.source_file_numbered=FileUtils.number_lines(self.source_code)
        
        # set up coverage processor
        self.coverage_processor=CoverageProcessor("jacoco",str(self.config.coverage_report_path/"jacoco.xml"),self.data_path)
 
        # set up LLMs 
        self.llama70b=ChatOllama(model="llama3.1:70b",callbacks=[StreamingStdOutCallbackHandler()],num_predict=10000)            
        self.deepseek=ChatOllama(model="deepseek:70b",callbacks=[StreamingStdOutCallbackHandler()],num_predict=10000)
        
        """set up agents  -- planner, (requirement engineer,competitor, plan_fixer), tester, inspector, """
        max_parsing_attempts=5
        jinja_env=Environment(loader=FileSystemLoader(self.config.prompt_template_path/self.config.generator))
        ## planner
        self.planner_parser=PydanticOutputParser(pydantic_object=TestPlan)
        self.planner= (self.llama70b | self.planner_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.planner_system_prompt=jinja_env.get_template("planner_system.jinja")
        self.planner_user_prompt=jinja_env.get_template("planner_user.jinja")
        ## tester
        self.tester_parser=PydanticOutputParser(pydantic_object=TestCases)
        self.tester= (self.llama70b | self.tester_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.tester_system_prompt=jinja_env.get_template("tester_system.jinja")
        self.tester_user_prompt=jinja_env.get_template("tester_user.jinja")
        ## inspector
        self.inspector_parser=PydanticOutputParser(pydantic_object=ErrorInfo)
        self.inspector= (self.llama70b | self.inspector_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.inspector_system_prompt=jinja_env.get_template("inspector_system.jinja")
        self.inspector_user_prompt=jinja_env.get_template("inspector_user.jinja")
        ## single case fixer
        self.single_case_fixer_parser=PydanticOutputParser(pydantic_object=TestCase)
        self.single_case_fixer= (self.llama70b | self.single_case_fixer_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.single_case_fixer_system_prompt=jinja_env.get_template("single_case_fixer_system.jinja")
        self.single_case_fixer_user_prompt=jinja_env.get_template("single_case_fixer_user.jinja")
        ## requirement engineer
        self.requirement_engineer_parser=PydanticOutputParser(pydantic_object=Requirements)
        self.requirement_engineer= (self.llama70b | self.requirement_engineer_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.requirement_engineer_system_prompt=jinja_env.get_template("requirement_engineer_system.jinja")
        self.requirement_engineer_user_prompt=jinja_env.get_template("requirement_engineer_user.jinja")
        ## competitor
        self.competitor_parser=PydanticOutputParser(pydantic_object=TestPlan)
        self.competitor= (self.llama70b | self.competitor_parser).with_retry(stop_after_attempt=max_parsing_attempts)
        self.competitor_system_prompt=jinja_env.get_template("competitor_system.jinja")
        self.competitor_user_prompt=jinja_env.get_template("competitor_user.jinja")
        ## plan fixer
        self.plan_fixer_parser=PydanticOutputParser(pydantic_object=TestPlan)
        self.plan_fixer= (self.llama70b | self.plan_fixer_parser).with_retry(stop_after_attempt=max_parsing_attempts)   
        self.plan_fixer_system_prompt=jinja_env.get_template("plan_fixer_system.jinja")
        self.plan_fixer_user_prompt=jinja_env.get_template("plan_fixer_user.jinja")
        
        # id_class_mapping for HumanEvalJava
        self.id_class_mapping=pickle.load((self.config.data_path/"class_id_mapping.pkl").open("rb"))
        self.id=self.id_class_mapping[self.class_name]
        
        # record failed tests
        self.failed_tests=[]
        
        # set up tensorboard writer
        tensorboard_log_dir =  f"tensorboard_logs/{self.id}"
        self.writer= SummaryWriter(tensorboard_log_dir)
        
    def run(self):
        """
        Run the test case generation process.
        """
        """"
        Generate unit tests for a given source file. 
        """
        try:
            self.coverage_processor.parse_coverage_report()
            self.iteration=0
            self.line_coverage = self.coverage_processor.calculate_line_coverage_rate_for_file(self.config.relative_source_file_path)
            logger.info(f"Initial line coverage: {self.line_coverage}")
            self._log2tensorboard()
            # improve coverage
            logger.info(f"Improving coverage for test file {self.test_file_path}")
            while self.line_coverage<self.config.target_line_coverage and self.iteration<self.config.max_attempts:
                self.iteration+=1
                logger.info(f"Current line coverage: {self.line_coverage}")
                try:
                    test_plan=self._generate_test_plan()
                    # test_plan=self._fix_plan(test_plan)
                    self._generate_test_with_plan(test_plan)
                    self._check_line_coverage_increase()
                    self._log2tensorboard()
                except Exception as e:
                    logger.error(f"Error generating test cases: {e}")
                    continue
        except Exception as e:
            logger.error(f"Error generating test cases: {e}")
        finally:
            self.writer.close()
            logger.info(f"Final line coverage: {self.line_coverage}")
            logger.info(f"Test generation process completed. Check the test file at {self.test_file_path}")
            logger.info(f"Tensorboard logs saved at {self.writer.log_dir}")
            subprocess.run(
                self.config.test_command.split(),
                capture_output=True,
                text=True,
                cwd=os.getcwd(),
            )
    def _generate_test_plan(self):
        """
        Generate a test plan using the planner agent.
        """


        lines_to_cover=self.coverage_processor.file_lines_not_executed.get(self.source_file_path,[])
        test_code=FileUtils.read_file(self.test_file_path)
        
        # render prompts
        planner_system_prompt=self.planner_system_prompt.render()
        planner_user_prompt=self.planner_user_prompt.render({
             "source_file_name":self.config.relative_source_file_path,
             "source_file_numbered":self.source_file_numbered,
             "lines_to_cover":lines_to_cover,
             "test_file_name":self.config.relative_test_file_path,
             "test_code":test_code,
             "format_instructions":self.planner_parser.get_format_instructions(),
        })
        logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT PLANNER: Generating test plan ----- 🚀 🚀 🚀 🚨🚨🚨 ")
        plan=self.planner.invoke([("system", planner_system_prompt), ("user", planner_user_prompt)]).dict()
         
        return plan
    
    def _generate_test_with_plan(self,plan):
        test_code=FileUtils.read_file(self.test_file_path)
        attempt=0
        # tester generate tests
        tester_system_prompt=self.tester_system_prompt.render()
        tester_user_prompt=self.tester_user_prompt.render({
            "source_code":self.source_code,
            "test_code":test_code,
            "test_plan":plan,
            "format_instructions":self.tester_parser.get_format_instructions(),
        })
        logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT TESTER: Generating test cases ----- 🚀 🚀 🚀 🚨🚨🚨 ")
        response=self.tester.invoke([("system", tester_system_prompt), ("user", tester_user_prompt)])
        new_tests=response.test_cases
        
        #### add a special testNothing case to cover the class declaration
        if "testNothing" not in test_code:
            test_nothing=TestCase(test_behavior="testNothing", test_name="testNothing", new_imports_code="", test_code=f"""
                                @Test
                                    public void testNothing(){{
                                        {self.class_name} s = new {self.class_name}();
                                        }}
                                """)
            new_tests=[test_nothing]+new_tests
        
        #### validate all test cases
        for new_test in new_tests:
            self._validate_and_serialize_test_code(new_test)
        if len(self.failed_tests)==0:
                return

        # fix the failed test cases
        all_failed_tests=self.failed_tests.copy()
        for failed_test in all_failed_tests:
            attempt=0
            feedback=None
            self.failed_tests=[]
            while attempt<self.config.max_attempts:
                attempt+=1
                # inspect failed tests
                logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT INSPECTOR: Inspecting failed test ----- 🚀 🚀 🚀 🚨🚨🚨 ")
                inspector_system_prompt=self.inspector_system_prompt.render()
                inspector_user_prompt=self.inspector_user_prompt.render({
                    "failed_test":failed_test,
                    "format_instructions":self.inspector_parser.get_format_instructions(),
                    "source_code":self.source_code,
                })
                feedback=self.inspector.invoke([("system", inspector_system_prompt), ("user", inspector_user_prompt)])
                # fix the test case based on the feedback
                logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT SINGLE CASE FIXER: Fixing failed test ----- 🚀 🚀 🚀 🚨🚨🚨 ")
                single_case_fixer_system_prompt=self.single_case_fixer_system_prompt.render()
                single_case_fixer_user_prompt=self.single_case_fixer_user_prompt.render({
                    "feedback":feedback,
                    "format_instructions":self.single_case_fixer_parser.get_format_instructions(),
                    "source_code":self.source_code,
                })
                new_test=self.single_case_fixer.invoke([("system", single_case_fixer_system_prompt), ("user", single_case_fixer_user_prompt)])
                valid=self._validate_and_serialize_test_code(new_test)
                if valid:
                    # break if pass
                    new_line_coverage=self._check_line_coverage_increase()
                    if new_line_coverage>self.config.target_line_coverage:
                        logger.info(f"Target line coverage reached: {new_line_coverage}")
                        self.writer.close()
                        return
                    else:
                        break
                else:
                    # otherwise, clean up failed test and continue
                    failed_test=self.failed_tests[-1]
                    
            
          
    # def _generate_test_with_plan(self,plan):
    #     attempt=0
    #     feedbacks=None
    #     test_code=FileUtils.read_file(self.test_file_path)
    #     while attempt<self.config.max_attempts:
    #         attempt+=1
    #         # tester generate tests
    #         tester_system_prompt=self.tester_system_prompt.render()
    #         tester_user_prompt=self.tester_user_prompt.render({
    #             "source_code":self.source_code,
    #             "test_code":test_code,
    #             "test_plan":plan,
    #             "feedbacks":feedbacks,
    #             "format_instructions":self.tester_parser.get_format_instructions(),
    #         })
    #         logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT TESTER: Generating test cases ----- 🚀 🚀 🚀 🚨🚨🚨 ")
    #         response=self.tester.invoke([("system", tester_system_prompt), ("user", tester_user_prompt)])
    #         new_tests=response.test_cases
    #         for new_test in new_tests:
    #             self._validate_and_serialize_test_code(new_test)
                
    #         # inspector inspect tests
    #         if len(self.failed_tests)==0:
    #             break
            
    #         feedbacks=[]
    #         for failed_test in self.failed_tests:
    #             # inspect failed tests
    #             logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT INSPECTOR: Inspecting failed test ----- 🚀 🚀 🚀 🚨🚨🚨 ")
    #             inspector_system_prompt=self.inspector_system_prompt.render()
    #             inspector_user_prompt=self.inspector_user_prompt.render({
    #                 "failed_tests":failed_test,
    #                 "format_instructions":self.inspector_parser.get_format_instructions(),
    #                 "source_code":self.source_code,
    #             })
    #             feedback=self.inspector.invoke([("system", inspector_system_prompt), ("user", inspector_user_prompt)])
                
    #         inspector_system_prompt=self.inspector_system_prompt.render()
    #         inspector_user_prompt=self.inspector_user_prompt.render({
    #             "failed_tests":self.failed_tests,
    #             "format_instructions":self.inspector_parser.get_format_instructions(),
    #             "source_code":self.source_code,
    #         })
    #         print("$"*100)
    #         print(inspector_user_prompt)
    #         feedback=self.inspector.invoke([("system", inspector_system_prompt), ("user", inspector_user_prompt)])
    #         # clean up failed tests
    #         self.failed_tests=[]
    
    def _log2tensorboard(self):
        jacoco_pdf=parse_jacoco_csv(self.config.coverage_report_path/"jacoco.csv", self.id_class_mapping)
        self.line_coverage,self.branch_coverage=jacoco_pdf.loc[self.id]["line_coverage"], jacoco_pdf.loc[self.id]["branch_coverage"]
        self.writer.add_scalar("line_coverage", self.line_coverage, self.iteration)
        self.writer.add_scalar("branch_coverage", self.branch_coverage, self.iteration)
    
    def _check_line_coverage_increase(self):
        subprocess.run(
                self.config.test_command.split(),
                capture_output=True,
                text=True,
                cwd=os.getcwd(),
            )
        self.coverage_processor.parse_coverage_report()
        new_line_coverage = (
            self.coverage_processor.calculate_line_coverage_rate_for_file(
                self.config.relative_source_file_path
            )
        )
        if new_line_coverage > self.line_coverage:
            logger.info(
                f"""
                {"#"*70}
                🚀📈 LINE COVERAGE UPDATE 📈🚀 
                ➡️  Line coverage increased from 🔴  {self.line_coverage*100:.2f}% to 🟢 {new_line_coverage*100:.2f}% 🎯
                {"#"*70}
                """ 
            )
            self.line_coverage = new_line_coverage

        else:
            logger.info(
                f"""
                {"#"*70}
                        🟡 📊 LINE COVERAGE STATUS 📊 🟡
                        🔁 No Change: Coverage remains at 🔵 {self.line_coverage*100:.2f}% 
                        {"#"*70}
                        """

                        )
        return new_line_coverage
    
    def _validate_and_serialize_test_code(self,generated_unittest):
        """
        Validate and serialize test code.
        """
        # validate test case
        new_test_code = generated_unittest.test_code
        new_imports_code = generated_unittest.new_imports_code
        assert (
            new_test_code != ""
        ), "New test code is empty in the generated unittest"
        FileUtils.backup_code(self.test_file_path)
        test_file_code = FileUtils.read_file(self.test_file_path)
        test_block_nodes = self.analyzer.get_test_nodes(
            source_file_path=self.test_file_path
        )
         
        # get last test block node
        if len(test_block_nodes) > 0:
            last_test_block_node = test_block_nodes[-1]

            indent_level = last_test_block_node.start_point[1]
            line_number = last_test_block_node.end_point[0] + 1

            modified_src_code = merge_code(
                code_to_insert=new_test_code,
                org_src_code=test_file_code,
                indent_level=indent_level,
                line_number=line_number,
            )
            for new_import in new_imports_code.splitlines():
                if new_import not in test_file_code:
                    modified_src_code = merge_code(
                        code_to_insert=new_import,
                        org_src_code=modified_src_code,
                        indent_level=0,
                        line_number=2,
                    )
        else:
            # TODO:// Find a better way to handle this case
            modified_src_code = merge_code(
                code_to_insert=new_test_code,
                org_src_code=test_file_code,
                indent_level=0,
                line_number=-1,
            )
            for new_import in new_imports_code.splitlines():
                if new_import not in test_file_code:
                    modified_src_code = merge_code(
                        code_to_insert=new_import,
                        org_src_code=modified_src_code,
                        indent_level=0,
                        line_number=2,
                    )
        # check syntax using javaparse
        # check syntax, execution, and assertion
        
        if self.analyzer.check_syntax( # proceed with only syntactically correct test code
            self.test_file_path, modified_src_code
        ):
            
            with open(self.test_file_path, "w") as file:
                file.write(modified_src_code)
                
            try:
                result = subprocess.run(
                    self.config.test_command.split(),
                    capture_output=True,
                    text=True,
                    timeout=300, 
                    cwd=os.getcwd(),
                )
                if result.returncode == 0:
                    logger.info(f"Test passed for\n{new_test_code}")
                    return True
                else: # return only CalledProcessError
                    execution_errors = extract_error_message( "java", result.stdout + result.stderr)
                    self._revert_test_file(new_test_code, execution_errors)
                    return False
            except Exception as e:      # catch timeout error mostly
                self._revert_test_file(new_test_code, str(e))
                return False
                
        # if there are errors, add to failed tests

        return False
    
    def _revert_test_file(self,new_test_code,error_message):
        """
        Revert the test file to the last version.
        """
        logger.info(f"Test failed for\n{new_test_code}")
        lang = "java"
        self.failed_tests.append({"test_code": new_test_code, "error": error_message})
        FileUtils.revert(self.test_file_path)
        subprocess.run(
        self.config.test_command.split(),
        capture_output=True,
        text=True,
        cwd=os.getcwd(),
        )