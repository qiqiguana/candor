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


