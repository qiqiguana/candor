import argparse

from .fix_oracle import OracleFixer
from .config import Config
from .generate import TestCaseGenerator
from .initialize import Initializer
import os
from .logger import logger
def parse_args():
    parser=argparse.ArgumentParser(description="Multi-agent Test Generation")
    subparsers=parser.add_subparsers(dest="command")
    
    # initializer subparser
    initializer_parser=subparsers.add_parser("initialize", help="Initialize the project")
    initializer_parser.add_argument("--data-path", help="Path to the data directory", required=True, type=str)
    initializer_parser.add_argument("--relative-source-file-path", help="Relative path of the source file", required=True, type=str)
    initializer_parser.add_argument("--relative-test-file-path", help="Relative path to the test file ", required=True, type=str)
    initializer_parser.add_argument("--coverage-report-path", help="Path to the code coverage report file", required=True, type=str)
    initializer_parser.add_argument("--test-command", help="Command to run the test file", required=True, type=str)
    initializer_parser.add_argument("--generator", help="Test generator to use", required=True, choices=["matg"])
    initializer_parser.add_argument("--max-attempts", help="Maximum number of attempts to generate the test file", type=int, default=3)
    
    # test generator subparser
    test_generator_parser=subparsers.add_parser("generate", help="Generate tests")
    test_generator_parser.add_argument("--generator", help="Test generator to use", required=True, choices=["matg"])
    test_generator_parser.add_argument("--relative-source-file-path", help="Relative path to the source file", required=True, type=str)
    test_generator_parser.add_argument("--relative-test-file-path", help="Relative path to the test file ", required=True, type=str)
    test_generator_parser.add_argument("--coverage-report-path", help="Path to the code coverage report file", required=True, type=str)
    test_generator_parser.add_argument("--target-line-coverage", help="Target line coverage", type=float, default=0.99)
    test_generator_parser.add_argument("--target-branch-coverage", help="Target branch coverage", type=float, default=0.99)
    test_generator_parser.add_argument("--max-attempts", help="Maximum number of attempts to generate the test file", type=int, default=3)
    test_generator_parser.add_argument("--data-path", help="Path to the data directory", required=True, type=str)
    test_generator_parser.add_argument("--test-command", help="Command to run the test file", required=True, type=str)
    
    # oracle fixer subparser
    oracle_fixer_parser=subparsers.add_parser("oracle-fixer", help="Fix the oracle")
    oracle_fixer_parser.add_argument("--data-path", help="Path to the data directory", required=True, type=str)
    oracle_fixer_parser.add_argument("--relative-source-file-path", help="Relative path to the source file", required=True, type=str)
    oracle_fixer_parser.add_argument("--relative-test-file-path", help="Relative path to the test file ", required=True, type=str)
    oracle_fixer_parser.add_argument("--test-command", help="Command to run the test file", required=True, type=str)
    oracle_fixer_parser.add_argument("--generator", help="Test generator to use", required=True, choices=["matg"])
    oracle_fixer_parser.add_argument("--doc-file", type=str, default=None, help="Path to the documentation file")
    
    return parser.parse_args()
                                    
def main():
    """
    Entry point of the program.
    This function sets up configurations, initializes the program, and generates tests based on the provided command.
    It takes no arguments and returns nothing.
    """
    # logo

    logger.info("""\n\n

                ▗▄▄▄▖  ▗▄▖  ▗▖  ▗▖ ▗▄▄▄    ▗▄▖  ▗▄▄▖ 
                ▐▌    ▐▌ ▐▌ ▐▛▚▖▐▌ ▐▌  █  ▐▌ ▐▌ ▐▌ ▐▌
                ▐▌    ▐▛▀▜▌ ▐▌ ▝▜▌ ▐▌  █  ▐▌ ▐▌ ▐▛▀▚▖
                ▝▚▄▄▖ ▐▌ ▐▌ ▐▌  ▐▌ ▐▙▄▄▀  ▝▚▄▞▘ ▐▌ ▐▌
""")
    logger.info("="*70 + "\n")
    logger.info("🚀 Starting MATG: Multi-Agent LLM-Based Test Generation Framework")
    logger.info("📘 Purpose : Automated test generation for Java using LLM agents")
    logger.info("🧠 Agents  : Planner, Generator, Executor, and Evaluator")
    logger.info("🛠️  Engine  : Powered by matg (Machine-Assisted Test Generation)")
    logger.info("="*70 + "\n\n")
    
    # set up configurations
    args=parse_args()
    config=Config()
    config._initialize_with_args(args)

    # initialize 
    if config.command=="initialize":
        initializer=Initializer(config)
        initializer.run()
    
    # generate tests
    if config.command=="generate":
        test_generator=TestCaseGenerator(config)
        test_generator.run()
    
    # fix oracle
    if config.command=="oracle-fixer":
        oracle_fixer=OracleFixer(config)
        oracle_fixer.run()
if __name__ == "__main__":
    main()
