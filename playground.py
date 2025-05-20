from pathlib import Path
import pandas as pd
import pickle


source_dir=Path("/home/qinghua/projects/matg/data/HumanEvalJava/matg/src/main/java/original/")
class_id_mapping=pickle.load(open("/home/qinghua/projects/matg/data/HumanEvalJava/matg/class_id_mapping.pkl", "rb"))

def parse_jacoco_csv(jacoco_csv_report_path,class_id_mapping):
    """
    Parse the JaCoCo CSV report and return a DataFrame with coverage information.
    Args:
        jacoco_csv_report_path (str): Path to the JaCoCo CSV report.
        class_id_mapping (dict): Mapping of class names to IDs.
        Returns:
        pd.DataFrame: DataFrame containing coverage information.
    """
    pdf=pd.read_csv(jacoco_csv_report_path)
    pdf = pdf[~pdf["CLASS"].str.contains(r"\.", regex=True)]
    pdf["CLASS"]=pdf["CLASS"].apply(lambda x: x.split(".")[0])
    pdf["id"]=pdf["CLASS"].apply(lambda x: class_id_mapping[x])
    pdf["line_coverage"]=pdf["LINE_COVERED"]/(pdf["LINE_COVERED"]+pdf["LINE_MISSED"])
    pdf["branch_coverage"]=pdf["BRANCH_COVERED"]/(pdf["BRANCH_COVERED"]+pdf["BRANCH_MISSED"])
    pdf.set_index("id", inplace=True)
    return pdf


def _log2tensorboard(id):

    jacoco_pdf=parse_jacoco_csv("/home/qinghua/projects/matg/data/HumanEvalJava/matg/target/site/jacoco/jacoco.csv", class_id_mapping)
    line_coverage,branch_coverage=jacoco_pdf.loc[id]["line_coverage"], jacoco_pdf.loc[id]["branch_coverage"]
    print("id:", id)
    print(line_coverage, branch_coverage)
    
if __name__=="__main__":
    # jacoco_pdf=parse_jacoco_csv("/home/qinghua/projects/matg/data/HumanEvalJava/matg/target/site/jacoco/jacoco.csv", class_id_mapping)
    # print(jacoco_pdf.head())
    ids=[120,105,149,104,88,157,162,58]
    ids= [ f'id_{id}' for id in ids]
    for id in ids:
        _log2tensorboard(id)
    
    # print(FileUtils.get_all_files(source_dir))
    # print(extract_source_metadata(source_dir/"id_142.java"))
    