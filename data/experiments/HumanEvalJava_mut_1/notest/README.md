# Overview

This directory contains the source code from HumanEval that are modified to represent 3 scenarios (each of which on its own Java package):

- **Scenario 1**: The samples only contain the import statements, function’s signature and implementation.
- **Scenario 2**: The samples include the function’s signature, implementation and a docstring with the function’s
  description but excluding the input-output examples.
- **Scenario 3**: The samples include the function’s signature, implementation and a docstring with the function’s
  description, and input-output examples, but we exclude its implementation.
- **Original**: We use the sample without excluding anything.

The folder `/src/test/` contains JUnit tests we semi-automatically created for these samples.


**Files**:
- `HumanEval_java_v1.jsonl` is the original JSONL file from the extended HumanEval dataset.
- `statistics.txt` contains the statistics of the samples in the original dataset.
- `Problematic Samples.txt` are the samples that had problematic input/output examples, that we manually fixed.




## How to run the tests and measure the coverage
This maven project uses JaCoCo to measure test coverage.
From command line:
1. `mvn clean`
2. `mvn package`
3. `mvn jacoco:report`

This will produce a folder `site` inside `target` that has teh results of the coverage (summarized in `jacoco.csv`).