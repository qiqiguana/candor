# CANDOR: Multi-Agent LLM-Based End-to-End Test Generation with Accurate Oracles

Replication package for the paper *"Hallucination to Consensus: Multi-Agent LLMs for End-to-End Test Generation with Accurate Oracles"*.

CANDOR orchestrates multiple specialized LLM agents to collaboratively generate complete JUnit 5 tests for Java programs, including both high-coverage test prefixes and accurate oracles. It uses a panel discussion strategy with multiple reasoning LLMs to reach consensus on oracle correctness, mitigating hallucinations.

## Prerequisites

| Requirement | Version |
|-------------|---------|
| Python | 3.10+ |
| Java | 11+ |
| Maven | 3.6+ |
| GPU VRAM | 48 GB+ (for 70B parameter models) |

Our experiments were conducted on a single NVIDIA RTX 6000 Ada (48 GB VRAM). Multi-GPU setups have not been tested and may require additional Ollama configuration.

## Environment Setup

### Step 1: Install Ollama and pull LLMs

```bash
curl -fsSL https://ollama.com/install.sh | sh
ollama pull llama3.1:70b
ollama pull deepseek-r1:70b
```

Verify the models are available:

```bash
ollama list
```

### Step 2: Install Python dependencies

```bash
pip install -r requirements.txt
```

### Step 3: Set PYTHONPATH

CANDOR is invoked as a Python module. Set `PYTHONPATH` to the **parent** directory of this repository:

```bash
export PYTHONPATH=$PYTHONPATH:/path/to/parent/of/this/repo
```

For example, if this repo is at `/home/user/projects/tgen`, then:

```bash
export PYTHONPATH=$PYTHONPATH:/home/user/projects
```

## Datasets

All datasets are under `data/experiments/`. Each dataset has a `notest/` directory containing:

- `src/main/java/original/*.java` -- Java source files under test
- `pom.xml` -- Maven build configuration with JaCoCo and Pitest plugins
- `class_id_mapping.pkl` -- mapping from class names to file IDs

| Dataset | Description | Files |
|---------|-------------|-------|
| `HumanEvalJava` | HumanEval benchmark (correct source) | 160 |
| `HumanEvalJava_mut_0` | HumanEval with Pitest mutants (variant 0) | 160 |
| `HumanEvalJava_mut_1` | HumanEval with Pitest mutants (variant 1) | 160 |
| `HumanEvalJava_mut_2` | HumanEval with Pitest mutants (variant 2) | 160 |
| `Leetcode` | LeetCode problems (correct source) | 150 |
| `Leetcode_mut_0` | LeetCode with Pitest mutants (variant 0) | 150 |
| `Leetcode_mut_1` | LeetCode with Pitest mutants (variant 1) | 150 |
| `Leetcode_mut_2` | LeetCode with Pitest mutants (variant 2) | 150 |

Each mutant dataset contains the same files as the original, but with mutations applied to the source code via Pitest bytecode mutation + CFR decompilation. Files that could not be cleanly decompiled are left as originals.

The `Leetcode/notest/` directory also contains `readmes.pkl`, which holds natural language problem descriptions used by the oracle fixing step.

## Running CANDOR (Single File)

CANDOR has three sequential steps. Each step can also be used independently (e.g., run only initialization, or skip straight to oracle fixing on an existing test file). All commands are run from the parent directory of this repository.

Note: Step III (Oracle Fixing) is the most time-consuming step, as it involves multiple agents per test case -- each oracle is evaluated by a panel of reasoning LLMs, summarised by interpreters, and consolidated by the curator.

### Step I: Initialization

The Initializer agent generates a scaffolding test file with one test case.

```bash
python -m tgen.main initialize \
  --data-path /path/to/dataset/notest \
  --relative-source-file-path src/main/java/original/id_76.java \
  --relative-test-file-path src/test/java/original/id_76Test.java \
  --coverage-report-path /path/to/dataset/notest/target/site/jacoco/ \
  --test-command "mvn -f /path/to/dataset/notest/pom.xml clean test jacoco:report" \
  --generator matg \
  --max-attempts 3
```

Note: The code automatically adds `-Dtest=<ClassName>Test` to the test command based on the source file.

### Step II: Test Prefix Generation

The Planner, Tester, and Inspector agents iteratively generate additional test cases to improve coverage.

```bash
python -m tgen.main generate \
  --data-path /path/to/dataset/notest \
  --relative-source-file-path src/main/java/original/id_76.java \
  --relative-test-file-path src/test/java/original/id_76Test.java \
  --coverage-report-path /path/to/dataset/notest/target/site/jacoco/ \
  --test-command "mvn -f /path/to/dataset/notest/pom.xml clean test jacoco:report" \
  --generator matg \
  --target-line-coverage 0.99 \
  --target-branch-coverage 0.99 \
  --max-attempts 3
```

### Step III: Oracle Fixing

The Requirement Engineer, Panelist (DeepSeek R1), Interpreter, and Curator agents evaluate and fix each test oracle via panel discussion.

```bash
python -m tgen.main oracle-fixer \
  --data-path /path/to/dataset/notest \
  --relative-source-file-path src/main/java/original/id_76.java \
  --relative-test-file-path src/test/java/original/id_76Test.java \
  --test-command "mvn -f /path/to/dataset/notest/pom.xml clean test jacoco:report" \
  --generator matg
```

For the LeetCode dataset, pass `--doc-file readmes.pkl` to provide natural language problem descriptions to the oracle fixer.

## Creating Mutants (Optional)

Mutant creation is not part of CANDOR's core pipeline, but we include the `create_mutants.py` script we used to generate the mutant datasets for our experiments. The mutant datasets (`*_mut_0`, `*_mut_1`, `*_mut_2`) were created using this script, which automates the full Pitest + CFR pipeline:

```bash
python create_mutants.py
```

This script performs the following steps for each dataset (HumanEvalJava, Leetcode):

1. **Compile** the original source with `mvn compile`
2. **Run Pitest** with the `+EXPORT` feature to generate bytecode mutants (`.class` files) in `target/pit-reports/export/`
3. **Decompile** each mutant `.class` file back to `.java` using CFR (`tools/cfr-0.152.jar`)
4. **Organize** into three variant datasets (`mut_0`, `mut_1`, `mut_2`), each selecting a different mutant per class
5. **Verify** compilation and revert any files where CFR decompilation produced invalid Java (e.g., loss of generic type information, missing anonymous inner classes)

### Manual workflow (for reference)

If you need to create mutants manually:

1. Ensure `pom.xml` has the Pitest plugin configured (already included in all datasets)
2. Run Pitest with the `+EXPORT` feature flag:

```bash
cd data/experiments/HumanEvalJava/notest
mvn org.pitest:pitest-maven:mutationCoverage \
  -DtargetClasses="original.*" \
  -DfailWhenNoMutations=false \
  -Dfeatures="+EXPORT"
```

3. Exported mutants appear at `target/pit-reports/export/original/<ClassName>/mutants/<N>/<package>.<ClassName>.class`
4. Decompile with CFR:

```bash
java -jar tools/cfr-0.152.jar \
  target/pit-reports/export/original/ClassName/mutants/0/original.ClassName.class \
  --extraclasspath target/classes
```

5. Place decompiled `.java` files into the mutant dataset directory structure

## Repository Structure

```
tgen/
├── main.py                  # CLI entry point (initialize / generate / oracle-fixer)
├── initialize.py            # Step I: Initializer agent (Llama 3.1 70B)
├── generate.py              # Step II: Planner, Tester, Inspector agents
├── fix_oracle.py            # Step III: Panelist, Interpreter, Curator agents
├── config.py                # Project configuration (auto-detects project root)
├── logger.py                # Logging configuration
├── utils.py                 # File I/O, code analysis, coverage parsing utilities
├── output_entities.py       # Pydantic models for structured LLM outputs
├── run_candor.py            # Batch runner for all files in a dataset
├── create_mutants.py        # Automated mutant creation (Pitest + CFR pipeline)
├── parse_readme.py          # Utility to parse LeetCode problem descriptions
├── requirements.txt         # Python dependencies
├── __init__.py              # Package marker
├── tools/
│   └── cfr-0.152.jar        # CFR Java decompiler (for mutant creation)
├── templates/
│   └── matg/                # Jinja2 prompt templates for all LLM agents
│       ├── initializer_system.jinja / initializer_user.jinja
│       ├── planner_system.jinja / planner_user.jinja
│       ├── tester_system.jinja / tester_user.jinja
│       ├── inspector_system.jinja / inspector_user.jinja
│       ├── requirement_engineer_system.jinja / requirement_engineer_user.jinja
│       ├── competitor_system.jinja / competitor_user.jinja
│       ├── competitor_summariser_system.jinja / competitor_summariser_user.jinja
│       ├── curator_system.jinja / curator_user.jinja
│       ├── test_case_parser_system.jinja / test_case_parser_user.jinja
│       ├── single_case_fixer_system.jinja / single_case_fixer_user.jinja
│       └── plan_fixer_system.jinja / plan_fixer_user.jinja
├── queries/
│   └── tree-sitter-java-tags.scm   # Tree-sitter queries for Java AST parsing
└── data/
    └── experiments/
        ├── HumanEvalJava/           # 160 correct Java source files
        ├── HumanEvalJava_mut_0/     # Pitest mutant variant 0
        ├── HumanEvalJava_mut_1/     # Pitest mutant variant 1
        ├── HumanEvalJava_mut_2/     # Pitest mutant variant 2
        ├── Leetcode/                # 150 correct Java source files
        ├── Leetcode_mut_0/          # Pitest mutant variant 0
        ├── Leetcode_mut_1/          # Pitest mutant variant 1
        └── Leetcode_mut_2/          # Pitest mutant variant 2
```

## LLM Configuration

CANDOR uses two locally-hosted LLMs via Ollama:

| Role | Model | Purpose |
|------|-------|---------|
| Basic LLM | Llama 3.1 70B | Initializer, Planner, Tester, Inspector, Requirement Engineer, Interpreter, Curator |
| Reasoning LLM | DeepSeek R1 70B | Panelist agents in oracle fixing (panel discussion) |

The Interpreter (basic LLM) always post-processes each Panelist's verbose reasoning output to extract structured oracle evaluations, forming a dual-LLM pipeline.

## Scope and Limitations

This work lays the foundation for multi-agent LLM-based test generation. Currently, CANDOR operates at the method level only. Handling inter-class dependencies and more complex project structures is the focus of our ongoing work, to be published soon. Nevertheless, the strategies used in this work -- coverage-driven iterative generation, multi-agent panel discussion for oracle consensus, and the dual-LLM pipeline -- can serve as building blocks for future test generation research.

## Contact

If you encounter any issues or have questions, please contact qinghua.xu@ul.ie.
