import os
import sys
sys.path.append("/home/qinghua/projects/mutahunter/mutahunter/src")
from  pathlib import Path
import subprocess
from tqdm import tqdm
print(os.getcwd())
project_root=Path("/home/qinghua/projects/mutahunter/mutahunter/data/HumanEvalJava/multi_agent")

# for f in tqdm((project_root/"src"/"main"/"java"/"original").iterdir()):
#     print("handling", f)
#     fname=f.stem
#     command = [
#     "python", "/home/qinghua/projects/mutahunter/mutahunter/src/mutahunter/main.py", "initialize",
#     "--test-command", "mvn clean test jacoco:report",
#     "--test-file-path", f"src/test/java/original/{fname}Test.java",
#     "--source-file-path", f"src/main/java/original/{fname}.java",
#     "--max-attempts", "10"
#     ]
#     subprocess.run(command,check=True)

 
for f in tqdm((project_root/"src"/"main"/"java"/"original").iterdir()):
    print("handling", f)
    fname=f.stem
    command = [
    "python", "/home/qinghua/projects/mutahunter/mutahunter/src/mutahunter/main.py", "gen-multi-agent",
    "--test-command", "mvn clean test jacoco:report",
    "--code-coverage-report-path", "/home/qinghua/projects/mutahunter/mutahunter/data/HumanEvalJava/multi_agent/target/site/jacoco/jacoco.xml",
    "--coverage-type", "jacoco",
    "--test-file-path", f"src/test/java/original/{fname}Test.java",
    "--source-file-path", f"src/main/java/original/{fname}.java",
    "--model", "ollama/llama3.1:70b",
    "--target-line-coverage", "0.99",
    "--max-attempts", "3"
    ]
    subprocess.run(command,check=True) 

# python /home/qinghua/projects/mutahunter/mutahunter/src/mutahunter/main.py gen-multi-agent \
#  --test-command "mvn clean test jacoco:report" \
#  --code-coverage-report-path "/home/qinghua/projects/mutahunter/mutahunter/data/HumanEvalJava/multi_agent/target/site/jacoco/jacoco.xml" \
#  --coverage-type jacoco --test-file-path "src/test/java/original/id_101Test.java" \
#  --source-file-path "src/main/java/original/id_101.java" \
#  --model "ollama/llama3.1:70b" \
#  --target-line-coverage 0.99 \
#  --max-attempts 3

# python /home/qinghua/projects/mutahunter/mutahunter/src/mutahunter/main.py gen-multi-agent \
#  --test-command "mvn clean test -Dtest=SumSquares1Test" \
#  --code-coverage-report-path "/home/qinghua/projects/mutahunter/mutahunter/data/HumanEvalJava/multi_agent/target/site/jacoco/jacoco.xml" \
#  --coverage-type jacoco --test-file-path "src/test/java/original/id_142Test.java" \
#  --source-file-path "src/main/java/original/id_142.java" \
#  --model "ollama/llama3.1:70b" \
#  --target-line-coverage 0.99 \
#  --max-attempts 2

# python /home/qinghua/projects/mutahunter/mutahunter/src/mutahunter/main.py gen-multi-agent  --test-command "mvn test jacoco:report"  --code-coverage-report-path "/home/qinghua/projects/mutahunter/mutahunter/data/HumanEvalJava/multi_agent/target/site/jacoco/jacoco.xml"  --coverage-type jacoco --test-file-path "src/test/java/original/id_129Test.java"  --source-file-path "src/main/java/original/id_129.java"  --model "ollama/llama3.1:70b"  --target-line-coverage 0.99  --max-attempts 2


[157,81,3,124,105,127,57,8,58,46]