"""
run with the command
nohup python run.py > "logs/result_$(date +%Y%m%d_%H%M%S).log" 2>&1 &
"""

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
runs=[1,2,3,4]
data_path=Path("/home/qinghua/projects/matg/data/leetcode_dataset/candor2")


source_file_path=data_path/"src"/"main"/"java"/package_name
test_file_path=data_path/"src"/"test"/"java"/package_name
test_file_path.mkdir(parents=True, exist_ok=True)

for f in source_file_path.iterdir():
    logger.info(f"\n\n Processing file: {f}")
    full_test_file_path=test_file_path/f"{f.stem}Test.java"
    if full_test_file_path.exists():
        logger.info(f"Test file already exists at {full_test_file_path}")
        continue
    fname=f.stem
    # if fname not in selected_names:
    #     continue
    command=[
        "python", "-m", "matg.main","initialize",
        "--data-path", str(data_path),
        "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
        "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
        "--coverage-report-path", str(data_path/"target/site/jacoco/"), # folder of jacoco report
        "--test-command", "mvn -f {pom} clean test jacoco:report".format(pom=str(data_path/"pom.xml")),
        "--generator", "matg",
        "--max-attempts", "2",
    ]
    subprocess.run(command, check=True)