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
data_path=Path("/home/qinghua/projects/matg/data/HumanEvalJava/matg/") # java project root
source_file_path=data_path/"src"/"main"/"java"/package_name
test_file_path=data_path/"src"/"test"/"java"/package_name
test_file_path.mkdir(parents=True, exist_ok=True)
fname="id_142"
selected=[162,105,157,129,148,116,87,124,57,145,39,58,10,69]
selected_names=[f"id_{id}" for id in selected]


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
for f in source_file_path.iterdir():
    logger.info(f"\n\n Processing file: {f}")
    fname=f.stem
    if fname not in selected_names:
        logger.info(f"skip {fname}")
        continue
    command=[
        "python","-m", "matg.main", "generate",
        "--data-path", str(data_path),
        "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
        "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
        "--coverage-report-path", str(data_path/"target/site/jacoco/"), # folder of jacoco report
        "--test-command", "mvn -f {pom} clean test jacoco:report".format(pom=str(data_path/"pom.xml")),
        "--generator", "matg",
        "--max-attempts", "3",
        "--target-line-coverage", "0.99",
    ]
    subprocess.run(command, check=True)