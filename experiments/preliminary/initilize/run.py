"""
run with the command
nohup python run.py > "logs/result_$(date +%Y%m%d_%H%M%S).log" 2>&1 &
"""

import subprocess
from pathlib import Path 
import sys
sys.path.append("/home/qinghua/projects/matg/")
from logger import logger

# challenging_cases=[157,81,3,124,105,127,57,8,58,46]
# seletected cases to generate
selected=[109,34,69]
selected_names=[f"id_{id}" for id in selected]

data_path=Path("/home/qinghua/projects/matg/data/HumanEvalJava/matg/") # java project root
source_file_path=data_path/"src"/"main"/"java"/"original/"
test_file_path=data_path/"src"/"test"/"java"/"original/"
test_file_path.mkdir(parents=True, exist_ok=True)
 
for f in source_file_path.iterdir():
    logger.info(f"\n\n Processing file: {f}")
    fname=f.stem
    if fname not in selected_names:
        continue
    command=[
        "python", "/home/qinghua/projects/matg/main.py", "initialize",
        "--data-path", str(data_path),
        "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
        "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
        "--coverage-report-path", str(data_path/"target/site/jacoco/"), # folder of jacoco report
        "--test-command", "mvn -f {pom} clean test jacoco:report".format(pom=str(data_path/"pom.xml")),
        "--generator", "matg",
        "--max-attempts", "1",
    ]
    subprocess.run(command, check=True)