from langchain_huggingface import ChatHuggingFace, HuggingFaceEndpoint
from .logger import logger
from langchain_ollama import ChatOllama
from jinja2 import Environment, FileSystemLoader
import subprocess
import os
from langchain_core.output_parsers import PydanticOutputParser
from .output_entities import InitialTestFile
from .utils import FileUtils, extract_error_message, extract_source_metadata
from langchain.callbacks.streaming_stdout import StreamingStdOutCallbackHandler

class Initializer:
    def __init__(self, config):
        self.config=config
        self.template_path=config.prompt_template_path/config.generator
        self.data_path=config.data_path
        self.source_file_path=self.config.data_path/self.config.relative_source_file_path
        self.test_file_path=self.config.data_path/self.config.relative_test_file_path
        # set up LLMs
        # llm = HuggingFaceEndpoint(endpoint_url="https://ks9ha1fe7g9taog7.us-east-1.aws.endpoints.huggingface.cloud")
        # self.llm=ChatHuggingFace(llm=llm)
        self.llm=ChatOllama(model="llama3.1:70b",callbacks=[StreamingStdOutCallbackHandler()])    
        # self.llm=ChatOllama(model="llama3.1:70b" )         
        self.initializer_parser=PydanticOutputParser(pydantic_object=InitialTestFile)
        self.initializer= (self.llm | self.initializer_parser).with_retry(stop_after_attempt=5)
        
        # get prompt templates
        jinja_env=Environment(loader=FileSystemLoader(self.config.prompt_template_path/self.config.generator))
        self.initializer_system_prompt=jinja_env.get_template("initializer_system.jinja")
        self.initializer_user_prompt=jinja_env.get_template("initializer_user.jinja")
       
        # source code analysis
        self.source_code=FileUtils.read_file(self.source_file_path)
        self.package_name,self.imports,self.class_name=extract_source_metadata(self.source_code)
        self.format_instructions=self.initializer_parser.get_format_instructions()
        self.config.test_command=self.config.test_command.replace("jacoco:report",f"-Dtest={self.class_name}Test jacoco:report") # test only one test file to save time
        # record failed test files
        self.failed_tests=[]
        
    def run(self):
        """
        Run the initializer to create the project structure and files.
        """
        try:
            # generate test file if not exists
            if not self.test_file_path.exists():
                result=self._initialize()
                if result:
                    logger.info(f"Test file created at {self.test_file_path}")
                else:
                    logger.error(f"Failed to create {self.test_file_path} after {self.config.max_attempts} attempts.")
            else:
                logger.info(f"Test file already exists at {self.test_file_path}")
                return
        except Exception as e:
            if self.test_file_path.exists():
                self.test_file_path.unlink()
            logger.error(f"Error creating test file: {e}")
        
            
    def _initialize(self):
        """
        Initialize the project structure and files.
        """
        config=self.config
        attempt=0
        while attempt<config.max_attempts:
            attempt+=1
            initializer_system_prompt=self.initializer_system_prompt.render()
            initializer_user_prompt=self.initializer_user_prompt.render({
                 "source_code":self.source_code,
                 "package_name": self.package_name,
                 "imports":self.imports,
                 "class_name":self.class_name,
                 "format_instructions":self.format_instructions,
                 "failed_tests":self.failed_tests,
                 }
            )
            logger.info(f"🚨🚨🚨 🚀 🚀 🚀 ----- 🤖 AGENT Initializer: Generating initial test file ----- 🚀 🚀 🚀 🚨🚨🚨 ")
            response=self.initializer.invoke([
                ("system", initializer_system_prompt),
                ("user", initializer_user_prompt)
            ])
            print(initializer_user_prompt)
            test_file_code=response.test_file_code
            with open(self.test_file_path, "w") as f:
                f.write(test_file_code)
            try:
                # run the test file
                logger.info(f"Running test file: {self.test_file_path}")
                result=subprocess.run(config.test_command.split(),capture_output=True,text=True,cwd=os.getcwd())
                if result.returncode==0:
                    logger.info("Test passed!")
                    return True
                else:
                    logger.info(f"Test failed: {result.stderr} \n {result.stdout}")
                    error_message=result.stderr+result.stdout
                    error_message=extract_error_message("java",error_message)
                    self.failed_tests.append({
                        "code":test_file_code,
                        "error":error_message,
                    })
                    # self.failed_tests=[{
                    #     "code": test_file_code,
                    #     "error": error_message,
                    # }]
                    self.test_file_path.unlink()
            except Exception as e:
                self.test_file_path.unlink()
        return False
            