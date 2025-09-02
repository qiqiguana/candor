"""
run with the command
nohup python run.py > "logs/result_$(date +%Y%m%d_%H%M%S).log" 2>&1 &
"""
import pickle
import subprocess
from pathlib import Path 
import sys
sys.path.append("/home/qinghua/projects/matg/")
from logger import logger
package_name="original"
# challenging_cases=[157,81,3,124,105,127,57,8,58,46]
# seletected cases to generate
# selected=[74,145,93]
# selected_names=[f"id_{id}" for id in selected]
class_id_mapping=pickle.load(open("/home/qinghua/projects/matg/class_id_mapping.pkl", "rb"))
id_class_mapping={v:k for k,v in class_id_mapping.items()}

"""initialization"""

# data_path=Path("/home/qinghua/projects/matg/data/experiments/HumanEvalJava/candor_new/run_1/initialize")
# source_file_path=data_path/"src"/"main"/"java"/package_name
# test_file_path=data_path/"src"/"test"/"java"/package_name
# test_file_path.mkdir(parents=True, exist_ok=True)
# n_file=0
# total_file=len(list(source_file_path.iterdir()))
# n_attempted_initialization=0
# n_successful_initialization=0
# for f in source_file_path.iterdir():
#     n_file+=1
#     logger.info(f"\n\n Processing file {n_file}/{total_file}: {f}")
#     full_test_file_path=test_file_path/f"{f.stem}Test.java"
#     if full_test_file_path.exists():
#         logger.info(f"Test file already exists at {full_test_file_path}")
#         continue
#     n_attempted_initialization+=1
#     fname=f.stem
#     class_name= id_class_mapping[fname]
#     pom=str(data_path/"pom.xml")
#     # if fname not in selected_names:
#     #     continue
#     command=[
#         "python", "-m", "matg.main","initialize",
#         "--data-path", str(data_path),
#         "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
#         "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
#         "--coverage-report-path", str(data_path/"target/site/jacoco/"), # folder of jacoco report
#         "--test-command", f"mvn -f {pom} clean -Dtest={class_name} test jacoco:report",
#         "--generator", "matg",
#         "--max-attempts", "5",
#     ]
#     subprocess.run(command, check=True)
#     if full_test_file_path.exists():
#         n_successful_initialization+=1
# logger.info(f"Initialization completed: {n_successful_initialization}/{n_attempted_initialization} successful initializations.")

"""generate to improve coverage"""

data_path=Path("/home/qinghua/projects/matg/data/experiments/HumanEvalJava/candor_new/run_2/generate")
source_file_path=data_path/"src"/"main"/"java"/package_name
test_file_path=data_path/"src"/"test"/"java"/package_name
test_file_path.mkdir(parents=True, exist_ok=True)
n_file=0
total_file=len(list(source_file_path.iterdir()))

for f in source_file_path.iterdir():
    n_file+=1
    logger.info(f"\n\n Processing file {n_file}/{total_file}: {f}")
    full_test_file_path=test_file_path/f"{f.stem}Test.java"

    fname=f.stem
    class_name= id_class_mapping[fname]
    pom=str(data_path/"pom.xml")
    # if fname not in selected_names:
    #     continue
    command=[
        "python", "-m", "matg.main","generate",
        "--data-path", str(data_path),
        "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
        "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
        "--coverage-report-path", str(data_path/"target/site/jacoco/"), # folder of jacoco report
        "--test-command", f"mvn -f {pom} clean -Dtest={class_name} test jacoco:report",
        "--generator", "matg",
        "--max-attempts", "3",
    ]
    subprocess.run(command, check=True)
    

"""fix oracles"""
# runs=[1,2]
# data_path=Path("/home/qinghua/projects/matg/data/experiments/HumanEvalJava/candor_new/run_1/fix")
# source_file_path=data_path/"src"/"main"/"java"/package_name
# test_file_path=data_path/"src"/"test"/"java"/package_name
# test_file_path.mkdir(parents=True, exist_ok=True)
# n_file=0
# total_file=len(list(source_file_path.iterdir()))

# for f in source_file_path.iterdir():
#     n_file+=1
#     logger.info(f"\n\n Processing file {n_file}/{total_file}: {f}")
#     full_test_file_path=test_file_path/f"{f.stem}Test.java"

#     fname=f.stem
#     class_name= id_class_mapping[fname]
#     pom=str(data_path/"pom.xml")
#     # if fname not in selected_names:
#     #     continue
#     command=[
#         "python", "-m", "matg.main","oracle-fixer",
#         "--data-path", str(data_path),
#         "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
#         "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
#         "--test-command", f"mvn -f {pom} clean -Dtest={class_name} test jacoco:report",
#         "--generator", "matg",
#     ]
#     subprocess.run(command, check=True)
    