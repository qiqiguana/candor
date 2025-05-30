from pydantic import BaseModel, Field
from typing import List, Literal

class Requirements(BaseModel):
    requirements: str= Field(description="The requirements of the source code")
    specifications: str= Field(description="The specifications of the source code")
      
class InitialTestFile(BaseModel):
    test_file_code: str = Field(description="The code of the test file")
     
    
class TestCasePlan(BaseModel):
    name: str = Field(description="The name of the test case")
    description: str = Field(description="The description of the test case")
    # type: Literal["positive", "negative", "edge", "boundary"] = Field(description="The type of test case")
    input: str = Field(description="Input parameters for the test case")
    expected_result: str = Field(description="Expected result for the test case")

class TestPlan(BaseModel):
    test_cases_to_add: List[TestCasePlan] = Field(description="List of new test cases to add")
    conventions: List[str] = Field(description="List of conventions followed in the test suite", default=[])
    test_framework: Literal["JUnit5"] = Field(description="The test framework used", default="JUnit5")
    language: Literal["Java"] = Field(description="The programming language used for the tests",default="Java")

class ErrorInfo(BaseModel):
    test_case_code: str = Field(description="Complete code of the failed test case", default=" no error")
    error_info: str = Field(description="The error information", default=" no error")
    error_type: str = Field(description="The type of the error", default=" no error")
    potential_fix: str = Field(description="The potential fix for the error", default=" no error")
    
class InspectionResult(BaseModel):
    feedbacks: list[ErrorInfo] = Field(description= "List of feedbacks, each including test case code, error information, error type, and potential fix")
    
class TestCase(BaseModel):
    test_behavior:str = Field(description="The behavior of the test case", default=" no behavior")
    test_name:str = Field(description="The name of the test case",default="random_test")
    test_code:str = Field(description="The full java code of the test case. Starting with @Test ")
    new_imports_code:str = Field(description="New import statements needed for the test case",default="")
    # test_tags: list[str] = Field(description="The tags of the test case", default=[])
 
class TestCases(BaseModel):
    test_cases: list[TestCase] = Field(description="The test cases")
    

class OracleAnalysisReport(BaseModel):
    judgement: bool = Field(description="Whether or not the old oracle is correct",default=True)
    old_oracle: str = Field(description="The old oracle",default="")
    new_oracle: str = Field(description="The new oracle if the oracle is wrong; otherwise, it is the same as the old oracle", default="")
    explanation: str = Field(description="The explanation of the judgement and the new oracle",default="Sorry, this test case is too complex for me to analyze. Please count me out for the discussion.")


class CuratorReport(BaseModel):
    judgement: bool = Field(description="Whether or not the old oracle is correct")
    test_case_code: str = Field(description="The new code of the fixed test case with correct oracle. Change only the oracle part if it is wrong")
    

class TestCaseParserResult(BaseModel):
    test_prefix: str = Field(description="The input of the test case")
    test_oracle: str = Field(description="The oracle of the test case")
    