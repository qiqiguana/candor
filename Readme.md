# Hallucination to Consensus: Multi-Agent LLMs for End-to-End  Test Generation with Accurate Oracles 
This paper proposes a novel approach CANDOR, leveraging multiple LLM agent to generate unit tests for Java programs. 

# Abstract
Unit testing plays a critical role in ensuring software correctness. However, writing unit tests manually is labor-intensive, especially for strong typed languages like Java, motivating the need for automated approaches. Traditional methods primarily rely on search-based or randomized algorithmss to generate tests that achieve high code coverage and produce regression oracles, which are assertions derived from the program’s current behavior rather than its intended functionality. Recent advances in large language models (LLMs) have enabled oracle generation from natural language descriptions, aligning better with user requirements. However, existing LLM-based methods often require LLM fine-tuning or rely on external tools such as EvoSuite for test prefix generation.

In this work, we propose CANDOR, a novel end-to-end, prompt engineering-based LLM framework for automated unit test generation in Java. CANDOR orchestrates multiple specialized LLM agents to collaboratively generate complete JUnit tests, including both high-quality test prefixes and accurate oracles. To mitigate the notorious hallucinations in LLMs and improve oracle correctness, we introduce a novel strategy that engages multiple reasoning LLMs in a panel discussion and generate accurate oracles based on consensus. Additionally, to reduce the verbosity of reasoning LLMs' outputs, we propose a novel dual-LLM pipeline to produce concise and structured oracle evaluations. 

Our experiments on the HumanEvalJava and LeetCodeJava datasets show that CANDOR can generate accurate oracles and is slightly better than EvoSuite in generating tests with high line coverage and clearly superior in terms of mutation score. Moreover, CANDOR significantly outperforms the state-of-the-art, prompt-based test generator \empirical, achieving improvements of 15.8 to 25.1 percentage points in oracle correctness on both correct and faulty source code.  Further ablation studies confirm the critical contributions of key agents in improving test prefix quality and oracle accuracy.

## Environment Setup
This code is based on Langchain library, which communicate with a local Ollama instance to interact with LLMs. 

#### Step 1: Ollama Installation
- 1.1 Ollama installation
```bash
curl -fsSL https://ollama.com/install.sh | sh
```
- 1.2 Pull LLMs
```bash
ollama pull llama3.1:70b
ollama pull deepseek-r1:70b
```

###  Step 2: Python Packages Installation
```bash
pip install -r requirements.txt
```

## CANDOR scripts


This repository contains key scripts of CANDOR, including:
- ``intialize.py``: Given a source code file, this script generates a scaffolding test file accordingly.
- ``generate.py``: Given the source code file and the scaffolding test file produced by ``initialize.py``, this script produces more test cases to enhance test coverage. 
- ``fix_oracle.py``: This script checks and fixes the oracle of each test case produced by ``initialize.py`` and ``generate.py``. 
- main.py: This is the main entry point of CANDOR.
- ``Templates``: Directory of prompts.
- ``data'': Directory of datasets.
- ``queries``: Directory of scm files, useful for source code parsing.
- ``utils.py``: Utility functions.
- ``output_entities.py``: Entity classes used to define LLM output format.
- ``parse_readme.py``: Utility script to parse the descriptions from Leetcode dataset.
- ``config.py``: Configurations and settings.
- ``logger.py``: Logging format definitions.  

## CANDOR Usage
CANDOR is implemented as a python module. To use CANDOR, please do the following:

- Add candor to your local PYTHONPATH
```bash
export PYTHONPATH=$PYTHONPATH:{{PYTHONPATH}}
```
- run ``initialize.py``. Replace ``{data_path}, {relative_source_file_path}, {relative_test_file_path}, coverage_report_path, pom_path`` with actual paths. 

```bash
python -m code.main initialize --data-path {datapath} --relative-source-file-path {relative_source_file_path} --coverage-report-path {coverage_report_path} --test-command "mvn -f {pom_path} clean test jacoco:report" --generator
```
- Run ``generate.py``. 

```bash
python -m code.main generate --data-path {datapath} --relative-source-file-path {relative_source_file_path} --coverage-report-path {coverage_report_path} --test-command "mvn -f {pom_path} clean test jacoco:report" --generator "matg" --target-line-coverage, "0.99",
```

- Run ``fix_oracle.py``. 

```bash
python -m code.main oracle-fixer --data-path {datapath} --relative-source-file-path {relative_source_file_path} --coverage-report-path {coverage_report_path} --test-command "mvn -f {pom_path} clean test jacoco:report" --generator "matg"  --target-line-coverage, "0.99",
```
