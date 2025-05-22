# Multi-agent Test Generation (MATG)
MATG generate test case files for **a single source file**, with the aim of enhancing coverage and oracle correctness. 

### Project Outline
- data. Dataset folder
- experiments. 
  - csv file records coverage change: line coverage, branch coverage
  - 

- main.py. Main interface 
- utils.py. Util functions 
- generators.py. All test case generators.
- config.py. all configurations
- evaluate.py. Evaluation 
- data/HumanEvalJava/matg/class_id_mapping.pkl. Maps class name to file name (id), e.g., 'WordsInSentence'-> 'id_143'

### Usage

1. Initialization
2. Generation
3. Evaluation



run Evosuite with this command
Install evosuite 1.2.0 runtime in maven, so that the generated tests can be executed

java -jar ./evosuite-1.2.0.jar \
  -prefix original \
  -projectCP target/classes \
  -Dtest_dir=./src/test/java/original \
  -Dsearch_budget=300

java -jar ./evosuite-1.2.0.jar \
  -prefix original \
  -projectCP target/classes \
  -Dtest_dir=./src/test/java/original \
  -Dsearch_budget=3600


<!-- evosuite -->
1. install evosuite 1.2.0 from github
  1.1. download evosuite source code
  1.2. ``mvn compile``
  1.3. ``mvn pakcage``
2. (optional) if you have messed up your local mvn and it is refusing to use local mvn, keep searching in central maven hub ... remove this folder ~/.m2/repository/org/evosuite/plugins/evosuite-maven-plugin/1.2.0/_remote.repositories and then run ``mvn clean install -U``


3. generate evosuite tests using
nohup mvn evosuite:generate evosuite:export > "evosuite_generation_$(date +%Y%m%d_%H%M%S).log" 2>&1 &

4. flip a tag in the generated test using flip.py

5. ``mvn clean test jacoco:report``




<!-- Useful tools -->
<!-- change package -->
python -m pack_change --source-dir /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/java/original --target-dir /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/java/branch

python -m pack_change --source-dir /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/test/java/original --target-dir /home/qinghua/projects/matg/data/HumanEvalJava/matg/src/test/java/oracle1

mvn -f /home/qinghua/projects/matg/data/HumanEvalJava/matg/ test -Dtest="oracle1.*Test"


nohup python -m matg.experiments.preliminary.generate.run > "logs/result_$(date +%Y%m%d_%H%M%S).log" 2>&1 &