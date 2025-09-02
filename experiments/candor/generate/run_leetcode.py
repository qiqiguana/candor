"""
run with the command
nohup python run.py > "logs/result_$(date +%Y%m%d_%H%M%S).log" 2>&1 &
"""

import subprocess
from pathlib import Path 
import sys
sys.path.append("/home/qinghua/projects/matg/")
from logger import logger
from tqdm import tqdm

package_name="original"
runs=[0,1,2]
for run in runs:
    data_path=Path(f"/home/qinghua/projects/matg/data/experiments/Leetcode/candor/run_{run}/generate") # java project root
    source_file_path=data_path/"src"/"main"/"java"/package_name
    test_file_path=data_path/"src"/"test"/"java"/package_name
    test_file_path.mkdir(parents=True, exist_ok=True)
    # selected=[ 157,71, 124, 87,93,117,129,92,69,10,149,105,120,58,135,89,137,80,68,128,17,91,142,148, 90,125,57,81,158,111,119,64]
    # selected_names=[f"id_{id}" for id in selected]


    # """generate for one file"""
    # command=[
    #         "python","-m", "matg.main", "generate",
    #         "--data-path", str(data_path),
    #         "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
    #         "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
    #         "--coverage-report-path", str(data_path/"target/site/jacoco/"), # folder of jacoco report
    #         "--test-command", "mvn -f {pom} clean test jacoco:report".format(pom=str(data_path/"pom.xml")),
    #         "--generator", "matg",
    #         "--max-attempts", "3",
    #         "--target-line-coverage", "0.99",
    #     ]
    # subprocess.run(command, check=True)


    """generate for all files"""
    for f in tqdm(source_file_path.iterdir()):
        logger.info(f"\n\n Processing file: {f}")
        fname=f.stem
        # if fname not in selected_names:
        #     logger.info(f"skip {fname}")
        #     continue
        command=[
            "python","-m", "matg.main", "generate",
            "--data-path", str(data_path),
            "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
            "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
            "--coverage-report-path", str(data_path/"target/site/jacoco/"), # folder of jacoco report
            "--test-command", "mvn -f {pom} clean test jacoco:report".format(pom=str(data_path/"pom.xml")),
            "--generator", "matg",
            "--max-attempts", "5",
            "--target-line-coverage", "0.99",
        ]
        subprocess.run(command, check=True)