"""
run with the command
nohup python run.py > "logs/result_$(date +%Y%m%d_%H%M%S).log" 2>&1 &
"""
import pickle
import subprocess
from pathlib import Path 
import sys
import os
sys.path.append("/home/qinghua/projects/matg/")
from logger import logger
import argparse
parser = argparse.ArgumentParser(description="Run experiments")
parser.add_argument("--package-name", type=str, default="original", help="Name of the package to run experiments on")
parser.add_argument("--data-path", type=str, default="/home/qinghua/projects/matg/data/experiments/HumanEvalJava/candor_new/run_2", help="Path to the data directory")
parser.add_argument("--mode", type=str, choices=["initialize", "generate", "fix"], default="generate", help="Mode of operation: initialize, generate, or fix oracles")
parser.add_argument("--max-attempts", type=int, default=3, help="Maximum number of attempts for each operation")
parser.add_argument("--doc-file", type=str, default=None, help="Path to the documentation file")
args=parser.parse_args()
package_name=args.package_name
data_path=Path(args.data_path)
mode= args.mode
max_attempts=args.max_attempts
doc_file=args.doc_file
class_id_mapping=pickle.load(open(os.path.join(data_path,"class_id_mapping.pkl"), "rb"))
id_class_mapping={v:k for k,v in class_id_mapping.items()}

"""initialization"""
## nohup python run_candor.py --mode initialize --data-path /home/qinghua/projects/matg/data/experiments/Leetcode/candor_new/run_0  > "logs/candor_new_leetcode_intialize_run_0__$(date +%Y%m%d_%H%M%S).log" 2>&1 &
if mode=="initialize":
    source_file_path=data_path/"src"/"main"/"java"/package_name
    test_file_path=data_path/"src"/"test"/"java"/package_name
    test_file_path.mkdir(parents=True, exist_ok=True)
    n_file=0
    total_file=len(list(source_file_path.iterdir()))
    n_attempted_initialization=0
    n_successful_initialization=0
    for f in source_file_path.iterdir():
        n_file+=1
        logger.info(f"\n\n Processing file {n_file}/{total_file}: {f}")
        full_test_file_path=test_file_path/f"{f.stem}Test.java"
        if full_test_file_path.exists():
            logger.info(f"Test file already exists at {full_test_file_path}")
            continue
        n_attempted_initialization+=1
        fname=f.stem
        class_name= id_class_mapping[fname]
        pom=str(data_path/"pom.xml")
        # if fname not in selected_names:
        #     continue
        command=[
            "python", "-m", "matg.main","initialize",
            "--data-path", str(data_path),
            "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
            "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
            "--coverage-report-path", str(data_path/"target/site/jacoco/"), # folder of jacoco report
            "--test-command", f"mvn -f {pom} clean -Dtest={class_name} test jacoco:report",
            "--generator", "matg",
            "--max-attempts", str(max_attempts),
        ]
        subprocess.run(command, check=True)
        if full_test_file_path.exists():
            n_successful_initialization+=1
    logger.info(f"Initialization completed: {n_successful_initialization}/{n_attempted_initialization} successful initializations.")

"""generate to improve coverage"""
## nohup python run_experiments.py  > "logs/candor_new_intialize_run_2__$(date +%Y%m%d_%H%M%S).log" 2>&1 &
if mode=="generate":
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
            "--max-attempts", str(max_attempts),
        ]
        subprocess.run(command, check=True)
    

"""fix oracles"""
if mode=="fix":
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
            "python", "-m", "matg.main","oracle-fixer",
            "--data-path", str(data_path),
            "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
            "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
            "--test-command", f"mvn -f {pom} clean -Dtest={class_name} test jacoco:report",
            "--generator", "matg",
        ]
        # if doc_file:
        #     command.append("--doc-file")
        #     command.append(doc_file)
        subprocess.run(command, check=True)
        