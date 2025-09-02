from .logger import logger
import re
import os
import xml.etree.ElementTree as ET
from typing import Dict, List
import shutil
from importlib import resources
from typing import Any, Dict, List

from grep_ast import filename_to_lang
from tree_sitter_languages import get_language, get_parser


import pandas as pd
class FileUtils:
    @staticmethod
    def read_file(path: str) -> str:
        try:
            with open(path, "r") as file:
                return file.read()
        except FileNotFoundError:
            logger.info(f"File not found: {path}")
        except Exception as e:
            logger.info(f"Error reading file {path}: {e}")
            raise

    @staticmethod
    def number_lines(code: str) -> str:
        return "\n".join(f"{i + 1} {line}" for i, line in enumerate(code.splitlines()))

    @staticmethod
    def backup_code(file_path: str) -> None:
        backup_path = f"{file_path}.bak"
        try:
            shutil.copyfile(file_path, backup_path)
        except Exception as e:
            logger.info(f"Failed to create backup file for {file_path}: {e}")
            raise

    @staticmethod
    def revert(file_path: str) -> None:
        backup_path = f"{file_path}.bak"
        try:
            if os.path.exists(backup_path):
                shutil.copyfile(backup_path, file_path)
            else:
                logger.info(f"No backup file found for {file_path}")
                raise FileNotFoundError(f"No backup file found for {file_path}")
        except Exception as e:
            logger.info(f"Failed to revert file {file_path}: {e}")
            raise
    
    @staticmethod
    def extract_description(source_code):
        match = re.search(r"/\*\*(.*?)\* Example", source_code, re.DOTALL)

        if match:
            extracted_comment = match.group(1).strip()
            # Clean up leading asterisks and spaces
            extracted_comment = re.sub(r"^\s*\*\s?", "", extracted_comment, flags=re.MULTILINE)
            return(extracted_comment)
        else:
            return("No comment found before 'Examples:'")
    @staticmethod
    def extract_lines(source_code, start_line, end_line):
        """
        Extracts the focal method from the source code based on the provided start and end lines.
        
        Args:
            source_code (str): The complete source code as a string.
            start_line (int): The starting line number of the focal method (1-based index).
            end_line (int): The ending line number of the focal method (1-based index).
        
        Returns:
            str: The extracted focal method as a string.
        """
        lines = source_code.splitlines()
        # Adjust for 0-based indexing
        return "\n".join(lines[start_line:end_line+1])
    
    @staticmethod
    def extract_text(source_code, start_row, start_column,end_row,end_column):
        """
        Extracts a specific text segment from the source code based on the provided start and end positions.

        Args:
            source_code (str): The complete source code as a string.
            start_row (int): The starting row number (0-based index).
            start_column (int): The starting column number (0-based index).
            end_row (int): The ending row number (0-based index).
            end_column (int): The ending column number (0-based index).

        Returns:
            str: The extracted text segment.
        """
        lines = source_code.splitlines()
        if start_row < 0 or end_row >= len(lines):
            raise ValueError("Row indices are out of bounds.")
        
        extracted_lines = lines[start_row:end_row + 1]
        if start_row == end_row:
            return extracted_lines[0][start_column:end_column]
        else:
            extracted_lines[0] = extracted_lines[0][start_column:]
            extracted_lines[-1] = extracted_lines[-1][:end_column+1]
            return "\n".join(extracted_lines).strip()
    
    @staticmethod
    def parse_source_file(src_file_path):
        """extract focal method and docstring from source file"""
        src_file = FileUtils.read_file(src_file_path)
        analyzer = Analyzer()
        docstrings = analyzer.find_docstring(src_file_path)
        methods = analyzer.get_function_blocks(src_file_path)
        
        if not methods:
            return None, None
        
        method = methods[0]
        start, end = method.start_point, method.end_point
        focal_method = FileUtils.extract_lines(src_file, start[0], end[0])
        return focal_method, docstrings

    @staticmethod
    def parse_test_method(test_file_path, test_method):
        """test_file_path is only used to extract language
            extract test prefix, test_oracle, assertion or exception and test_method without oracle
            returns: (test_prefix,assertORexception,GTassert, test_prefix_wo_oracle,test_method_name)
        """
        analyzer = Analyzer()
        if "assert" in test_method:
            assertORexception = 0
        elif "try" in test_method and "catch" in test_method:
            assertORexception = -1
        else:
            assertORexception = 1
        # extract test method name
        test_method_name_nodes=analyzer.get_test_method_name_nodes(test_file_path,test_method.encode("utf-8"))
        start, end = test_method_name_nodes[0].start_point, test_method_name_nodes[0].end_point
        test_method_name=FileUtils.extract_text(test_method, start[0],start[1],end[0],end[1])
        # extract test prefix and oracle
        if assertORexception !=0:
            return test_method,assertORexception, "", test_method,test_method_name
        
        assertion_oracle = analyzer.find_assertion_nodes(test_file_path,test_method.encode("utf-8"))
        test_prefix = test_method
        oracle_text = FileUtils.extract_lines(test_method, assertion_oracle[0].start_point[0], assertion_oracle[0].end_point[0]).strip()
        test_prefix_wo_oracle = test_method.replace(oracle_text, "").strip()
        return test_prefix, assertORexception, oracle_text, test_prefix_wo_oracle,test_method_name
    
import javalang

def extract_source_metadata(source_code):
    tree = javalang.parse.parse(source_code)
    package = tree.package.name if tree.package else None
    imports = [imp.path for imp in tree.imports]
    class_decl = next(tree.filter(javalang.tree.ClassDeclaration))[1]
    return package, imports,class_decl.name


async def stream_langchain_llm(prompts,llm):
    """
    Stream the output of a language model (LLM) in chunks.
    """
    chunks = []
    for chunk in llm.astream(prompts):
       print(chunk,flush=True)
       chunks.append(chunk)
    return "".join(chunks)


###############################
"""Error parse"""




def extract_error_message(lang: str, fail_message: str) -> str:
    if lang == "python":
        return extract_error_message_python(fail_message)
    elif lang == "java":
        return extract_error_message_java(fail_message)
    elif lang == "go":
        return extract_error_message_go(fail_message)
    else:
        return fail_message.strip()


def extract_error_message_python(fail_message):
    """
    Extracts and returns the error message from the provided failure message.

    Parameters:
        fail_message (str): The failure message containing the error message to be extracted.

    Returns:
        str: The extracted error message from the failure message, or an empty string if no error message is found.

    """
    try:
        # Define a regular expression pattern to match the error message
        MAX_LINES = 20
        pattern = r"={3,} FAILURES ={3,}(.*?)(={3,}|$)"
        match = re.search(pattern, fail_message, re.DOTALL)
        if match:
            err_str = match.group(1).strip("\n")
            err_str_lines = err_str.split("\n")
            if len(err_str_lines) > MAX_LINES:
                # show last MAX_lines lines
                err_str = "...\n" + "\n".join(err_str_lines[-MAX_LINES:])
            return err_str
        return fail_message.strip()
    except Exception as e:
        return fail_message.strip()
    
 

def extract_error_message_java(fail_message):
    """
    Extracts and returns the error message from the provided Java Maven failure message.

    Parameters:
        fail_message (str): The failure message containing the error message to be extracted.

    Returns:
        str: The extracted error message from the failure message, or the full message if no specific error message is found.
    """
    lines=fail_message.split("\n")
    lines=[line for line in lines if "ERROR" in line or "FAILURE" in line]
    return "\n".join(lines).strip() 

def extract_error_message_go(fail_message):
    try:
        # Define a regular expression pattern to match the error message
        MAX_LINES = 20
        # Regex pattern to capture error details including method names
        pattern = r"(?i)(--- FAIL: [^\n]*\n.*?)(?=\n\[|--- FAIL:|\Z)"
        matches = re.findall(pattern, fail_message, re.MULTILINE | re.DOTALL)

        if matches:
            err_str = ""
            for match in matches:
                err_str += match.strip("\n") + "\n"

            err_str_lines = err_str.split("\n")
            if len(err_str_lines) > MAX_LINES:
                # Show last MAX_LINES lines
                err_str = "...\n" + "\n".join(err_str_lines[-MAX_LINES:])
            return err_str.strip()
        return fail_message.strip()
    except Exception as e:
        return fail_message.strip()


"""parse jacoco report"""
def parse_jacoco_csv(jacoco_csv_report_path,class_id_mapping):
    """
    Parse the JaCoCo CSV report and return a DataFrame with coverage information.
    Args:
        jacoco_csv_report_path (str): Path to the JaCoCo CSV report.
        class_id_mapping (dict): Mapping of class names to IDs.
        Returns:
        pd.DataFrame: DataFrame containing coverage information.
    """
    pdf=pd.read_csv(jacoco_csv_report_path)
    pdf = pdf[~pdf["CLASS"].str.contains(r"\.", regex=True)] # remove inner classes
    pdf["CLASS"]=pdf["CLASS"].apply(lambda x: x.split(".")[0])
    pdf["id"]=pdf["CLASS"].apply(lambda x: class_id_mapping[x])
    pdf["line_coverage"]=pdf["LINE_COVERED"]/(pdf["LINE_COVERED"]+pdf["LINE_MISSED"])
    pdf["branch_coverage"]=pdf["BRANCH_COVERED"]/(pdf["BRANCH_COVERED"]+pdf["BRANCH_MISSED"])
    pdf.set_index("id", inplace=True)
    return pdf



class CoverageProcessor:
    def __init__(self, coverage_type, code_coverage_report_path,data_path) -> None:
        """
        Initializes the CoverageProcessor with the given configuration.

        Args:
            config (Dict[str, Any]): The configuration dictionary.
        """
        self.data_path = str(data_path)
        self.coverage_type = coverage_type
        self.code_coverage_report_path = code_coverage_report_path

        self.line_coverage_rate = 0.00
        self.file_lines_executed = {}  # src_file -> [line_numbers]
        self.file_lines_not_executed = {}  # src_file -> [line_numbers]

        self.file_lines_with_missing_branch= {}  # src_file -> [line_numbers]
        self.file_n_branch_missed= {}  # src_file -> [number of branch missed]
        self.file_n_branch_covered= {}  # src_file -> [number of branch covered]
        
    def parse_coverage_report(self) -> Dict[str, List[int]]:
        """
        Parses the appropriate coverage report based on the coverage type.

        Returns:
            Dict[str, List[int]]: A dictionary where keys are filenames and values are lists of covered line numbers.
        """
        coverage_type_parsers = {
            "lcov": self.parse_coverage_report_lcov,
            "cobertura": self.parse_coverage_report_cobertura,
            "jacoco": self.parse_coverage_report_jacoco,
        }

        if self.coverage_type in coverage_type_parsers:
            coverage_type_parsers[self.coverage_type]()
            self.line_coverage_rate = self.calculate_line_coverage_rate()
        else:
            raise ValueError(
                "Invalid coverage tool. Please specify either 'cobertura', 'jacoco', or 'lcov'."
            )

    def parse_coverage_report_lcov(self):
        self._check_file_exists(self.code_coverage_report_path)
        self._check_file_extension([".info"], self.code_coverage_report_path)

        self.file_lines_executed.clear()
        self.file_lines_not_executed.clear()

        current_file = None
        with open(self.code_coverage_report_path, "r") as file:
            lines = file.readlines()
            for line in lines:
                if line.startswith("SF:"):
                    current_file = line.strip().split(":", 1)[1]
                    if current_file not in self.file_lines_executed:
                        self.file_lines_executed[current_file] = []
                    if current_file not in self.file_lines_not_executed:
                        self.file_lines_not_executed[current_file] = []
                elif line.startswith("DA:") and current_file:
                    parts = line.strip().split(":")[1].split(",")
                    hits = int(parts[1])
                    if hits > 0:
                        line_number = int(parts[0])
                        self.file_lines_executed[current_file].append(line_number)
                    else:
                        line_number = int(parts[0])
                        self.file_lines_not_executed[current_file].append(line_number)
                elif line.startswith("end_of_record"):
                    current_file = None

    def parse_coverage_report_cobertura(self):
        self._check_file_exists(self.code_coverage_report_path)
        self._check_file_extension([".xml"], self.code_coverage_report_path)

        self.file_lines_executed.clear()
        self.file_lines_not_executed.clear()

        tree = ET.parse(self.code_coverage_report_path)
        root = tree.getroot()

        for cls in root.findall(".//class"):
            name_attr = cls.get("filename")
            if name_attr not in self.file_lines_executed:
                self.file_lines_executed[name_attr] = []
            if name_attr not in self.file_lines_not_executed:
                self.file_lines_not_executed[name_attr] = []
            for line in cls.findall(".//line"):
                line_number = int(line.get("number"))
                hits = int(line.get("hits"))
                if hits > 0:
                    self.file_lines_executed[name_attr].append(line_number)
                else:
                    self.file_lines_not_executed[name_attr].append(line_number)

    def parse_coverage_report_jacoco(self):
        self._check_file_exists(self.code_coverage_report_path)
        self._check_file_extension([".xml"], self.code_coverage_report_path)
        self.file_lines_executed.clear()
        self.file_lines_not_executed.clear()
        self.file_n_branch_covered.clear()
        self.file_n_branch_missed.clear()
        self.file_lines_with_missing_branch.clear()
        tree = ET.parse(self.code_coverage_report_path)
        root = tree.getroot()

        for package in root.findall(".//package"):
            # package_name = package.get("name").replace("/", ".")
            for sourcefile in package.findall(".//sourcefile"):
                filename = sourcefile.get("name")
                full_filename = self.find_source_file(filename)
                full_filename = full_filename.replace(self.data_path + "/", "")
                if full_filename not in self.file_lines_executed:
                    self.file_lines_executed[full_filename] = []
                if full_filename not in self.file_lines_not_executed:
                    self.file_lines_not_executed[full_filename] = []
                if full_filename not in self.file_n_branch_covered:
                    self.file_n_branch_covered[full_filename]=0
                if full_filename not in self.file_n_branch_missed:
                    self.file_n_branch_missed[full_filename]=0
                if full_filename not in self.file_lines_with_missing_branch:
                    self.file_lines_with_missing_branch[full_filename]=[]
                for line in sourcefile.findall(".//line"):
                    line_number = int(line.get("nr"))  # nr is the line number
                    covered = int(line.get("ci"))  # ci is the covered lines
                    # missing = int(line.get("mi"))  # mi is the missed lines
                    if covered > 0:
                        self.file_lines_executed[full_filename].append(line_number)
                    else:
                        self.file_lines_not_executed[full_filename].append(line_number)
                    
                    # handle branch coverage
                    covered_branch=int(line.get("cb"))
                    missed_branch=int(line.get("mb"))
                    total_branch=covered_branch+missed_branch
                    if total_branch> 0:
                        
                        self.file_n_branch_covered[full_filename]+=covered_branch
                        self.file_n_branch_missed[full_filename]+=missed_branch
                    if missed_branch > 0:
                        self.file_lines_with_missing_branch[full_filename].append(line_number)
                          
        if len(self.file_lines_executed) == 1:
            logger.info("Only missing one ")
            
    def find_source_file(self, filename: str):
        for root, dirs, files in os.walk(self.data_path):
            for file in files:
                if "src" in root and filename in file:
                    return os.path.join(root, file)

    def calculate_line_coverage_rate_for_file(self, src_file):
        src_file=str(src_file)
        lines_executed = self.file_lines_executed.get(src_file, [])
        lines_not_executed = self.file_lines_not_executed.get(src_file, [])
        if len(lines_executed) + len(lines_not_executed) == 0:
            line_cov = 0.00
        else:
            line_cov = len(lines_executed) / (
                len(lines_executed) + len(lines_not_executed)
            )
        return line_cov
    
    def calculate_branch_coverage_rate_for_file(self, src_file):
        src_file=str(src_file)
        n_branch_covered = self.file_n_branch_covered.get(src_file, 0)
        n_branch_missed = self.file_n_branch_missed.get(src_file, 0)
        if n_branch_covered + n_branch_missed == 0:
            branch_cov = 1.00
        else:
            branch_cov = n_branch_covered / (n_branch_covered + n_branch_missed)
        return branch_cov
    
    def calculate_line_coverage_rate(self) -> float:
        total_executed_lines = sum(
            len(lines) for lines in self.file_lines_executed.values()
        )
        total_missed_lines = sum(
            len(lines) for lines in self.file_lines_not_executed.values()
        )
        if total_executed_lines + total_missed_lines == 0:
            return 0.00
        return round(
            total_executed_lines / (total_executed_lines + total_missed_lines), 2
        )

    def _check_file_exists(self, file_path: str):
        if not os.path.exists(file_path):
            raise FileNotFoundError(f"File '{file_path}' not found.")

    def _check_file_extension(self, exts: List[str], file_path: str):
        if not any(file_path.endswith(ext) for ext in exts):
            raise ValueError(f"File '{file_path}' is not in {exts} format.")


"""code analysis"""
def merge_code(
    org_src_code: str,
    code_to_insert: str,
    indent_level: int,
    line_number: int,
) -> None:
    org_src_code_lines = org_src_code.splitlines()
    code_to_insert = reset_indentation(code_to_insert)
    code_to_insert = adjust_indentation(code_to_insert, indent_level)
    if line_number < 0:
        org_src_code_lines.append(code_to_insert)
    else:
        org_src_code_lines.insert(line_number, code_to_insert)
    modified_src_code = "\n".join(org_src_code_lines)
    return modified_src_code

def replace_code_block(
    org_src_code: str,
    code_to_insert: str,
    indent_level: int,
    start_line: int,
    end_line: int
) -> str:
    """
    Replace a block of code between start_line and end_line with code_to_insert.
    Line numbers are 0-based.
    """
    org_src_code_lines = org_src_code.splitlines()

    # Normalize and indent the new code block
    code_to_insert = reset_indentation(code_to_insert)
    code_to_insert = adjust_indentation(code_to_insert, indent_level)
    insert_lines = code_to_insert.splitlines()

    # Remove the original block
    new_src_lines = (
        org_src_code_lines[:start_line] +
        insert_lines +
        org_src_code_lines[end_line+1:]
    )

    modified_src_code = "\n".join(new_src_lines)
    return modified_src_code



def reset_indentation(code: str) -> str:
    """Reset the indentation of the given code to zero-based indentation."""
    lines = code.splitlines()
    if not lines:
        return code
    min_indent = min(len(line) - len(line.lstrip()) for line in lines if line.strip())
    return "\n".join(line[min_indent:] if line.strip() else line for line in lines)


def adjust_indentation(code: str, indent_level: int) -> str:
    """Adjust the given code to the specified base indentation level."""
    lines = code.splitlines()
    adjusted_lines = [" " * indent_level + line for line in lines]
    return "\n".join(adjusted_lines)


class Analyzer:
    def __init__(self, config=None) -> None:
        self.config= config

    def get_language_by_filename(self, filename: str) -> str:
        """
        Gets the language identifier based on the filename.

        Args:
            filename (str): The name of the file.

        Returns:
            str: The language identifier.
        """
        return filename_to_lang(filename)

    def get_covered_function_blocks(
        self, executed_lines: List[int], source_file_path: str
    ) -> List[Any]:
        """
        Retrieves covered function blocks based on executed lines and source_file_path.

        Args:
            executed_lines (List[int]): List of executed line numbers.
            source_file_path (str): The name of the file being analyzed.

        Returns:
            List[Any]: A list of covered function blocks.
        """
        function_blocks = self.get_function_blocks(source_file_path=source_file_path)
        return self._get_covered_blocks(function_blocks, executed_lines)

    def get_covered_method_blocks(
        self, executed_lines: List[int], source_file_path: str
    ) -> List[Any]:
        """
        Retrieves covered method blocks based on executed lines and source_file_path.

        Args:
            executed_lines (List[int]): List of executed line numbers.
            source_file_path (str): The name of the file being analyzed.

        Returns:
            List[Any]: A list of covered method blocks.
        """
        method_blocks = self.get_method_blocks(source_file_path=source_file_path)
        return self._get_covered_blocks(method_blocks, executed_lines)

    def _get_covered_blocks(
        self, blocks: List[Any], executed_lines: List[int]
    ) -> List[Any]:
        """
        Retrieves covered blocks based on executed lines.

        Args:
            blocks (List[Any]): List of blocks (function or method).
            executed_lines (List[int]): List of executed line numbers.

        Returns:
            List[Any]: A list of covered blocks.
        """
        covered_blocks = []
        covered_block_executed_lines = []

        for block in blocks:
            # 0 baseed index
            start_point = block.start_point
            end_point = block.end_point

            start_line = start_point[0] + 1
            end_line = end_point[0] + 1

            if any(line in executed_lines for line in range(start_line, end_line + 1)):
                block_executed_lines = [
                    line - start_line + 1 for line in range(start_line, end_line + 1)
                ]
                covered_blocks.append(block)
                covered_block_executed_lines.append(block_executed_lines)

        return covered_blocks, covered_block_executed_lines

    def get_method_blocks(self, source_file_path: str) -> List[Any]:
        """
        Retrieves method blocks from a given file.

        Args:
            source_file_path (str): The name of the file being analyzed.

        Returns:
            List[Any]: A list of method block nodes.
        """
        source_code = self._read_source_file(source_file_path)
        return self.find_method_blocks_nodes(source_file_path, source_code)

    def get_function_blocks(self, source_file_path: str) -> List[Any]:
        """
        Retrieves function blocks from a given file.

        Args:
            source_file_path (str): The name of the file being analyzed.

        Returns:
            List[Any]: A list of function block nodes.
        """
        source_code = self._read_source_file(source_file_path)
        return self.find_function_blocks_nodes(source_file_path, source_code)

    def _read_source_file(self, file_path: str) -> bytes:
        """
        Reads the source code from a file.

        Args:
            file_path (str): The path to the source file.

        Returns:
            bytes: The source code.
        """
        with open(file_path, "rb") as f:
            return f.read()

    def check_syntax(self, source_file_path: str, source_code: str) -> bool:
        """
        Checks the syntax of the provided source code.

        Args:
            source_code (str): The source code to check.

        Returns:
            bool: True if the syntax is correct, False otherwise.
        """
        lang = filename_to_lang(source_file_path)
        parser = get_parser(lang)
        tree = parser.parse(bytes(source_code, "utf8")) 
        return not tree.root_node.has_error
    

    def find_method_blocks_nodes(
        self, source_file_path: str, source_code: bytes
    ) -> List[Any]:
        """
        Finds method block nodes in the provided source code.

        Args:
            source_code (bytes): The source code to analyze.

        Returns:
            List[Any]: A list of method block nodes.
        """
        return self._find_blocks_nodes(
            source_file_path, source_code, ["if_statement", "loop", "return"]
        )

    def find_function_blocks_nodes(
        self, source_file_path: str, source_code: bytes
    ) -> List[Any]:
        """
        Finds function block nodes in the provided source code.

        Args:
            source_code (bytes): The source code to analyze.

        Returns:
            List[Any]: A list of function block nodes.
        """
        return self._find_blocks_nodes(
            source_file_path, source_code, ["definition.function", "definition.method"]
        )

    def _find_blocks_nodes(
        self, source_file_path: str, source_code: bytes, tags: List[str]
    ) -> List[Any]:
        """
        Finds block nodes (method or function) in the provided source code.

        Args:
            source_code (bytes): The source code to analyze.
            tags (List[str]): List of tags to identify blocks.

        Returns:
            List[Any]: A list of block nodes.
        """
        lang = filename_to_lang(source_file_path)
        if lang is None:
            raise ValueError(f"Language not supported for file: {source_file_path}")
        parser = get_parser(lang)
        language = get_language(lang)

        tree = parser.parse(source_code)

        # def traverse_tree(node, depth=0):
        #     print("  " * depth + f"{node.type}: {node.text.decode('utf8')}")
        #     for child in node.children:
        #         traverse_tree(child, depth + 1)

        # traverse_tree(tree.root_node)

        query_scm = self._load_query_scm(lang)
        if not query_scm:
            return []

        query = language.query(query_scm)
        captures = query.captures(tree.root_node)
        # for node, tag in captures:
        #     print(node, tag)
        # print code
        # print(source_code[node.start_byte : node.end_byte].decode("utf8"))

        if not captures:
            logger.error("Tree-sitter query failed to find any captures.")
            return []
        return [node for node, tag in captures if tag in tags]

    def _load_query_scm(self, lang: str) -> str:
        """
        Loads the query SCM file content.

        Args:
            lang (str): The language identifier.

        Returns:
            str: The content of the query SCM file.
        """
        try:
            scm_fname = resources.files(__package__).joinpath(
                "queries", f"tree-sitter-{lang}-tags.scm"
            )
        except KeyError:
            return ""
        if not scm_fname.exists():
            return ""
        return scm_fname.read_text()

    def find_function_block_by_name(
        self, source_file_path: str, method_name: str
    ) -> List[Any]:
        """
        Finds a function block by its name and returns the start and end lines of the function.

        Args:
            source_file_path (str): The path to the source file.
            method_name (str): The name of the method to find.

        Returns:
            Dict[str, int]: A dictionary with 'start_line' and 'end_line' as keys and their corresponding line numbers as values.
        """
        source_code = self._read_source_file(source_file_path)
        lang = filename_to_lang(source_file_path)
        if lang is None:
            raise ValueError(f"Language not supported for file: {source_file_path}")

        parser = get_parser(lang)
        language = get_language(lang)
        tree = parser.parse(source_code)

        query_scm = self._load_query_scm(lang)
        if not query_scm:
            raise ValueError(
                "Failed to load query SCM file for the specified language."
            )

        query = language.query(query_scm)
        captures = query.captures(tree.root_node)

        result = []

        for node, tag in captures:
            if tag == "definition.function" or tag == "definition.method":
                if self._is_function_name(node, method_name, source_code):
                    return node
        raise ValueError(f"Function {method_name} not found in file {source_file_path}")

    def _is_function_name(self, node, method_name: str, source_code: bytes) -> bool:
        """
        Checks if the given node corresponds to the method_name.

        Args:
            node (Node): The AST node to check.
            method_name (str): The method name to find.
            source_code (bytes): The source code.

        Returns:
            bool: True if the node corresponds to the method_name, False otherwise.
        """
        node_text = source_code[node.start_byte : node.end_byte].decode("utf8")
        return method_name in node_text

    def get_import_nodes(self, source_file_path: str) -> List[Any]:
        """
        Retrieves import nodes from a given file.

        Args:
            source_file_path (str): The name of the file being analyzed.

        Returns:
            List[Any]: A list of import nodes.
        """
        source_code = self._read_source_file(source_file_path)
        return self._find_blocks_nodes(source_file_path, source_code, ["import"])

    def get_test_nodes(self, source_file_path: str) -> List[Any]:
        """
        Retrieves test nodes from a given file.

        Args:
            source_file_path (str): The name of the file being analyzed.

        Returns:
            List[Any]: A list of test nodes.
        """
        source_code = self._read_source_file(source_file_path)
        return self._find_blocks_nodes(source_file_path, source_code, ["test.method"])

    def find_docstring(self, source_file_path: str):
        source_code=self._read_source_file(source_file_path)
        lang=filename_to_lang(source_file_path)
        print(lang)
        parser=get_parser(lang)
        language=get_language(lang)
        tree=parser.parse(source_code)
        root=tree.root_node
        comments = self._extract_comments(root, source_code)
        return comments
    
    def _extract_comments(self, node, source_code):
        comments = []
        if node.type =="block_comment":
            text = source_code[node.start_byte:node.end_byte].decode("utf-8")
            comments.append((node.start_byte, node.end_byte, text))
        for child in node.children:
            comments.extend(self._extract_comments(child, source_code))
        return comments
    
    def get_test_method_name_nodes(self, source_file_path: str, source_code:bytes) -> List[Any]:
        """
        Retrieves test method name nodes from a given file.

        Args:
            source_file_path (str): The name of the file being analyzed.

        Returns:
            List[Any]: A list of test method name nodes.
        """
   
        return self._find_blocks_nodes(source_file_path, source_code, ["name.test.method"])

    def find_assertion_nodes(self, source_file_path: str, source_code: bytes) -> List[Any]:
        """
        Finds assertion nodes in the provided source code.

        Args:
            source_code (bytes): The source code to analyze.

        Returns:
            List[Any]: A list of assertion nodes.
        """
        return self._find_blocks_nodes(
            source_file_path, source_code, ["assert.call"]
        )
    
# error="""

# "[\x1b[1;34mINFO\x1b[m] Scanning for projects...\n[\x1b[1;33mWARNING\x1b[m] \n[\x1b[1;33mWARNING\x1b[m] Some problems were encountered while building the effective model for mbxp:HumanEvalJava:jar:1.0\n[\x1b[1;33mWARNING\x1b[m] 'build.plugins.plugin.version' for org.apache.maven.plugins:maven-site-plugin is missing. @ line 58, column 21\n[\x1b[1;33mWARNING\x1b[m] \n[\x1b[1;33mWARNING\x1b[m] It is highly recommended to fix these problems because they threaten the stability of your build.\n[\x1b[1;33mWARNING\x1b[m] \n[\x1b[1;33mWARNING\x1b[m] For this reason, future Maven versions might no longer support building such malformed projects.\n[\x1b[1;33mWARNING\x1b[m] \n[\x1b[1;34mINFO\x1b[m] \n[\x1b[1;34mINFO\x1b[m] \x1b[1m-------------------------< \x1b[0;36mmbxp:HumanEvalJava\x1b[0;1m >-------------------------\x1b[m\n[\x1b[1;34mINFO\x1b[m] \x1b[1mBuilding HumanEvalJava 1.0\x1b[m\n[\x1b[1;34mINFO\x1b[m] \x1b[1m--------------------------------[ jar ]---------------------------------\x1b[m\n[\x1b[1;34mINFO\x1b[m] \n[\x1b[1;34mINFO\x1b[m] \x1b[1m--- \x1b[0;32mmaven-clean-plugin:2.5:clean\x1b[m \x1b[1m(default-clean)\x1b[m @ \x1b[36mHumanEvalJava\x1b[0;1m ---\x1b[m\n[\x1b[1;34mINFO\x1b[m] Deleting /home/qinghua/projects/matg/data/HumanEvalJava/matg/target\n[\x1b[1;34mINFO\x1b[m] \n[\x1b[1;34mINFO\x1b[m] \x1b[1m--- \x1b[0;32mjacoco-maven-plugin:0.8.8:prepare-agent\x1b[m \x1b[1m(default)\x1b[m @ \x1b[36mHumanEvalJava\x1b[0;1m ---\x1b[m\n[\x1b[1;34mINFO\x1b[m] argLine set to -javaagent:/home/qinghua/.m2/repository/org/jacoco/org.jacoco.agent/0.8.8/org.jacoco.agent-0.8.8-runtime.jar=destfile=/home/qinghua/projects/matg/data/HumanEvalJava/matg/target/jacoco.exec\n[\x1b[1;34mINFO\x1b[m] \n[\x1b[1;34mINFO\x1b[m] \x1b[1m--- \x1b[0;32mmaven-resources-plugin:2.6:resources\x1b[m \x1b[1m(default-resources)\x1b[m @ \x1b[36mHumanEvalJava\x1b[0;1m ---\x1b[m\n[\x1b[1;34mINFO\x1b[m] Using 'UTF-8' encoding to copy filtered resources.\n[\x1b[1;34mINFO\x1b[m] skip non existing resourceDirectory /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/resources\n[\x1b[1;34mINFO\x1b[m] \n[\x1b[1;34mINFO\x1b[m] \x1b[1m--- \x1b[0;32mmaven-compiler-plugin:3.11.0:compile\x1b[m \x1b[1m(default-compile)\x1b[m @ \x1b[36mHumanEvalJava\x1b[0;1m ---\x1b[m\n[\x1b[1;34mINFO\x1b[m] Changes detected - recompiling the module! :source\n[\x1b[1;34mINFO\x1b[m] Compiling 160 source files with javac [debug target 11] to target/classes\n[\x1b[1;33mWARNING\x1b[m] /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/java/original/id_101.java:[35,30] non-varargs call of varargs method with inexact argument type for last parameter;\n  cast to java.lang.Object for a varargs call\n  cast to java.lang.Object[] for a non-varargs call and to suppress this warning\n[\x1b[1;34mINFO\x1b[m] /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/java/original/id_87.java: /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/java/original/id_87.java uses unchecked or unsafe operations.\n[\x1b[1;34mINFO\x1b[m] /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/java/original/id_87.java: Recompile with -Xlint:unchecked for details.\n[\x1b[1;34mINFO\x1b[m] \n[\x1b[1;34mINFO\x1b[m] \x1b[1m--- \x1b[0;32mmaven-resources-plugin:2.6:testResources\x1b[m \x1b[1m(default-testResources)\x1b[m @ \x1b[36mHumanEvalJava\x1b[0;1m ---\x1b[m\n[\x1b[1;34mINFO\x1b[m] Using 'UTF-8' encoding to copy filtered resources.\n[\x1b[1;34mINFO\x1b[m] skip non existing resourceDirectory /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/test/resources\n[\x1b[1;34mINFO\x1b[m] \n[\x1b[1;34mINFO\x1b[m] \x1b[1m--- \x1b[0;32mmaven-compiler-plugin:3.11.0:testCompile\x1b[m \x1b[1m(default-testCompile)\x1b[m @ \x1b[36mHumanEvalJava\x1b[0;1m ---\x1b[m\n[\x1b[1;34mINFO\x1b[m] Changes detected - recompiling the module! :dependency\n[\x1b[1;34mINFO\x1b[m] Compiling 160 source files with javac [debug target 11] to target/test-classes\n[\x1b[1;34mINFO\x1b[m] /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/test/java/original/id_111Test.java: /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/test/java/original/id_111Test.java uses unchecked or unsafe operations.\n[\x1b[1;34mINFO\x1b[m] /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/test/java/original/id_111Test.java: Recompile with -Xlint:unchecked for details.\n[\x1b[1;34mINFO\x1b[m] -------------------------------------------------------------\n[\x1b[1;31mERROR\x1b[m] COMPILATION ERROR : \n[\x1b[1;34mINFO\x1b[m] -------------------------------------------------------------\n[\x1b[1;31mERROR\x1b[m] /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/test/java/original/id_142Test.java:[81,25] cannot find symbol\n  symbol:   variable original\n  location: class original.SumSquares1Test\n[\x1b[1;34mINFO\x1b[m] 1 error\n[\x1b[1;34mINFO\x1b[m] -------------------------------------------------------------\n[\x1b[1;34mINFO\x1b[m] \x1b[1m------------------------------------------------------------------------\x1b[m\n[\x1b[1;34mINFO\x1b[m] \x1b[1;31mBUILD FAILURE\x1b[m\n[\x1b[1;34mINFO\x1b[m] \x1b[1m------------------------------------------------------------------------\x1b[m\n[\x1b[1;34mINFO\x1b[m] Total time:  1.293 s\n[\x1b[1;34mINFO\x1b[m] Finished at: 2025-05-09T11:34:21+01:00\n[\x1b[1;34mINFO\x1b[m] \x1b[1m------------------------------------------------------------------------\x1b[m\n[\x1b[1;31mERROR\x1b[m] Failed to execute goal \x1b[32morg.apache.maven.plugins:maven-compiler-plugin:3.11.0:testCompile\x1b[m \x1b[1m(default-testCompile)\x1b[m on project \x1b[36mHumanEvalJava\x1b[m: \x1b[1;31mCompilation failure\x1b[m\n[\x1b[1;31mERROR\x1b[m] \x1b[1;31m/home/qinghua/projects/matg/data/HumanEvalJava/matg/src/test/java/original/id_142Test.java:[81,25] cannot find symbol\x1b[m\n[\x1b[1;31mERROR\x1b[m] \x1b[1;31m  symbol:   variable original\x1b[m\n[\x1b[1;31mERROR\x1b[m] \x1b[1;31m  location: class original.SumSquares1Test\x1b[m\n[\x1b[1;31mERROR\x1b[m] \x1b[1;31m\x1b[m\n[\x1b[1;31mERROR\x1b[m] -> \x1b[1m[Help 1]\x1b[m\n[\x1b[1;31mERROR\x1b[m] \n[\x1b[1;31mERROR\x1b[m] To see the full stack trace of the errors, re-run Maven with the \x1b[1m-e\x1b[m switch.\n[\x1b[1;31mERROR\x1b[m] Re-run Maven using the \x1b[1m-X\x1b[m switch to enable full debug logging.\n[\x1b[1;31mERROR\x1b[m] \n[\x1b[1;31mERROR\x1b[m] For more information about the errors and possible solutions, please read the following articles:\n[\x1b[1;31mERROR\x1b[m] \x1b[1m[Help 1]\x1b[m http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException"
# """
# parsed=extract_error_message_java(error)
# print(parsed)

if __name__ == "__main__":
    # Example usage
    coverage= CoverageProcessor(
        coverage_type="jacoco",
        code_coverage_report_path="/home/qinghua/projects/matg/example_jacoco.xml",
        data_path="/home/qinghua/projects/matg/data/HumanEvalJava/candor_initialize_1"
    )
    coverage.parse_coverage_report()
    file_name="src/main/java/original/id_3.java"
    print(coverage.file_lines_executed)
    print(coverage.file_lines_executed[file_name])
    print(coverage.file_lines_not_executed[file_name])
    print(coverage.file_n_branch_covered[file_name])
    print(coverage.file_n_branch_missed[file_name])
    print(coverage.file_lines_with_missing_branch[file_name])
