import pandas as pd
oracle_path="/home/qinghua/projects/matg/data/experiments/HumanEvalJava/candor_new/run_0/fix/curator_report.csv"
pdf=pd.read_csv(oracle_path)
# set column names
pdf.columns = ["test_file_path", "test_method_name", "judgement", "test_case_code" ]
print(pdf.head())
# count True and False in judgement column
print(pdf.judgement.value_counts())
print(1-77/780)