from pathlib import Path
import pandas as pd
import pickle
from langchain_huggingface import ChatHuggingFace, HuggingFaceEndpoint
import re
from .utils import extract_source_metadata, Analyzer, FileUtils,merge_code

# source_dir=Path("/home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/java/original/")
# class_id_mapping=pickle.load(open("/home/qinghua/projects/matg/data/HumanEvalJava/matg/class_id_mapping.pkl", "rb"))

# def parse_jacoco_csv(jacoco_csv_report_path,class_id_mapping):
#     """
#     Parse the JaCoCo CSV report and return a DataFrame with coverage information.
#     Args:
#         jacoco_csv_report_path (str): Path to the JaCoCo CSV report.
#         class_id_mapping (dict): Mapping of class names to IDs.
#         Returns:
#         pd.DataFrame: DataFrame containing coverage information.
#     """
#     pdf=pd.read_csv(jacoco_csv_report_path)
#     pdf = pdf[~pdf["CLASS"].str.contains(r"\.", regex=True)]
#     pdf["CLASS"]=pdf["CLASS"].apply(lambda x: x.split(".")[0])
#     pdf["id"]=pdf["CLASS"].apply(lambda x: class_id_mapping[x])
#     pdf["line_coverage"]=pdf["LINE_COVERED"]/(pdf["LINE_COVERED"]+pdf["LINE_MISSED"])
#     pdf["branch_coverage"]=pdf["BRANCH_COVERED"]/(pdf["BRANCH_COVERED"]+pdf["BRANCH_MISSED"])
#     pdf.set_index("id", inplace=True)
#     return pdf


# def _log2tensorboard(id):

#     jacoco_pdf=parse_jacoco_csv("/home/qinghua/projects/matg/data/HumanEvalJava/matg/target/site/jacoco/jacoco.csv", class_id_mapping)
#     line_coverage,branch_coverage=jacoco_pdf.loc[id]["line_coverage"], jacoco_pdf.loc[id]["branch_coverage"]
#     print("id:", id)
#     print(line_coverage, branch_coverage)
    
# if __name__=="__main__":
#     # jacoco_pdf=parse_jacoco_csv("/home/qinghua/projects/matg/data/HumanEvalJava/matg/target/site/jacoco/jacoco.csv", class_id_mapping)
#     # print(jacoco_pdf.head())
#     # ids=[120,105,149,104,88,157,162,58]
#     # ids= [ f'id_{id}' for id in ids]
#     # for id in ids:
#     #     _log2tensorboard(id)
    
#     # print(FileUtils.get_all_files(source_dir))
#     # print(extract_source_metadata(source_dir/"id_142.java"))
#     llm = HuggingFaceEndpoint(repo_id="meta-llama/Llama-3.3-70B-Instruct")
#     llm=ChatHuggingFace(llm=llm)
#     print(llm.invoke(("user","What is the capital of France?")))
# print(class_id_mapping)

def get_class_id_mapping(dir):
    """
    Get a mapping of class names to IDs from the Java files in the specified directory.
    Args:
        dir (str): Path to the directory containing Java files.
    Returns:
        dict: Mapping of class names to IDs.
    """
    class_id_mapping = {}
    for file in Path(dir).iterdir():
        source_code= file.read_text(encoding="utf-8")
        package_name,imports,class_name=extract_source_metadata(source_code)
        if file.suffix == ".java":
            class_id = file.stem
            class_id_mapping[class_name] = class_id
    return class_id_mapping

# project_dir=Path("/home/qinghua/projects/matg/data/experiments/Leetcode/candor_new/run_0/")
# dir=project_dir/"src/main/java/original"
# class_id_mapping=get_class_id_mapping(dir)
# print(class_id_mapping)
# pickle_path= project_dir/"class_id_mapping.pkl"
# pickle.dump(class_id_mapping, open(pickle_path, "wb"))
# readme_path="readmes.pkl"
# readmes= pickle.load(open(readme_path, "rb"))
# print(readmes["id_2154.java"])

# project_dir="/home/qinghua/projects/matg/data/experiments/Leetcode/candor_new/run_2"
# difficulty_path="/home/qinghua/projects/matg/leetcode_class_difficulty_mapping.csv"
# import pandas as pd
# pdf=pd.read_csv(difficulty_path)
# # get the CLASS of all easy problems
# easy_classes=pdf[pdf["DIFFICULTY"]=="Easy"]["CLASS"].tolist()
# easy_src_fname=[f"id_{c[8:]}.java" for c in easy_classes]
# easy_test_fname=[f"id_{c[8:]}Test.java" for c in easy_classes]
# # delete eay src and test files from the project directory
# src_dir=Path(project_dir)/"src/main/java/original"
# test_dir=Path(project_dir)/"src/test/java/original"
# for fname in easy_src_fname:
#     file_path=src_dir/fname
#     if file_path.exists():
#         file_path.unlink()
#         print(f"Deleted {file_path}")
#     else:
#         print(f"{file_path} does not exist")
# for fname in easy_test_fname:
#     file_path=test_dir/fname
#     if file_path.exists():
#         file_path.unlink()
#         print(f"Deleted {file_path}")
#     else:
#         print(f"{file_path} does not exist")


"""insert docsting to the leetcode source files"""
project_dir=Path("/home/qinghua/projects/matg/data/experiments/Leetcode/candor_new/run_0/")
dir=project_dir/"src/main/java/original"
analyzer= Analyzer()
readme_path="readmes.pkl"
readmes= pickle.load(open(readme_path, "rb"))
# iterate all java files in the directory
# for file in dir.iterdir():
#     if file.suffix == ".java":
#         src_file = FileUtils.read_file(src_file_path)
file="id_0005.java"
file_path=dir/file
src_file = FileUtils.read_file(file_path)
methods = analyzer.get_function_blocks(file_path)
method=methods[0]
start, end = method.start_point, method.end_point
comment=readmes[file]["description"]
text = re.sub(r'<[^>]+>', '', comment)
# Replace multiple spaces/newlines with single space
clean_comment = re.sub(r'\s+', ' ', text).strip()
comment=f'/**\n{clean_comment}\n*/\n'
new_src_file=merge_code(src_file,comment,2,start[0]+1)
with open(file_path, "w") as f:
    f.write(new_src_file)
    
# iterate all java files in the directory
for file in dir.iterdir():
    if file.suffix == ".java":
        src_file = FileUtils.read_file(file)
        file_path=dir/file
        src_file = FileUtils.read_file(file_path)
        methods = analyzer.get_function_blocks(file_path)
        method=methods[0]
        start, end = method.start_point, method.end_point
        comment=readmes[file.name]["description"]
        text = re.sub(r'<[^>]+>', '', comment)
        # Replace multiple spaces/newlines with single space
        clean_comment = re.sub(r'\s+', ' ', text).strip()
        comment=f'/**\n{clean_comment}\n*/\n'
        new_src_file=merge_code(src_file,comment,2,start[0]+1)
        with open(file_path, "w") as f:
            f.write(new_src_file)
