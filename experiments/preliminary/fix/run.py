"""
run with the command
nohup python run.py > "logs/result_$(date +%Y%m%d_%H%M%S).log" 2>&1 &
"""

import subprocess
from pathlib import Path 
import sys
sys.path.append("/home/qinghua/projects/matg/")
from logger import logger

package_name="oracle1"
data_path=Path("/home/qinghua/projects/matg/data/HumanEvalJava/matg/") # java project root
source_file_path=data_path/"src"/"main"/"java"/package_name
test_file_path=data_path/"src"/"test"/"java"/package_name
# test_file_path.mkdir(parents=True, exist_ok=True)
# fname="id_142"
# selected=[162,105,157,129,148,116,87,124,57,145,39,58,10,69]
# selected_names=[f"id_{id}" for id in selected]


 


"""generate for all files"""
files=[ f for f in source_file_path.iterdir()]
for i,f in enumerate(files):
    logger.info(f"\n\n Processing file {i+1}/{len(files)}: {f}")
    fname=f.stem
    # if fname not in selected_names:
    #     logger.info(f"skip {fname}")
    #     continue
    command=[
        "python","-m", "matg.main", "oracle-fixer",
        "--data-path", str(data_path),
        "--relative-source-file-path", str((source_file_path/f"{fname}.java").relative_to(data_path)),
        "--relative-test-file-path", str((test_file_path/f"{fname}Test.java").relative_to(data_path)),
        "--test-command", "mvn -f {pom} clean test jacoco:report".format(pom=str(data_path/"pom.xml")),
        "--generator", "matg",
    ]
    subprocess.run(command, check=True)