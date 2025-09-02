from pathlib import Path
import pickle
import random
import pandas as pd
from preprocess_leetcode import extract_readme_content

human=Path("data/HumanEvalJava/candor_initialize_1/src/main/java/original")
leetcode=Path("data/leetcode_dataset/candor/src/main/java/original")
leetcode_pkl= Path("/home/qinghua/projects/matg/data/leetcode_dataset/candor/readmes.pkl")
leetcode_readmes = pickle.load(open(leetcode_pkl, "rb"))

new_readmes, easy, medium, hard = extract_readme_content(leetcode_readmes)
print(f"Extracted {len(new_readmes)} readmes with descriptions and difficulties.")
easy_keys, medium_keys, hard_keys = list(easy.keys()), list(medium.keys()), list(hard.keys())
sampled_easy,sampled_medium,sampled_hard=[], [], []
for file in leetcode.iterdir():
    if file.name in easy_keys:
        sampled_easy.append(file.name)
    elif file.name in medium_keys:
        sampled_medium.append(file.name)
    elif file.name in hard_keys:
        sampled_hard.append(file.name)

print(f"Sampled {len(sampled_easy)} easy, {len(sampled_medium)} medium, and {len(sampled_hard)} hard problems.")


columns=["id", "n_lines", "difficulty"]
leetcode_pdf= pd.DataFrame(columns=columns)

for file in leetcode.iterdir():
    difficulty=None
    if file.name in sampled_easy:
        difficulty = "Easy"
    elif file.name in sampled_medium:
        difficulty = "Medium"
    elif file.name in sampled_hard:
        difficulty = "Hard"
    code=file.read_text(encoding="utf-8")
    n_lines=len(code.splitlines())
    leetcode_pdf.loc[len(leetcode_pdf)]={
        "id": file.name,
        "n_lines": n_lines,
        "difficulty": difficulty
    }
print("LeetCode DataFrame:",leetcode_pdf.head())
print(leetcode_pdf.describe())
grouped_stats = leetcode_pdf.groupby("difficulty").describe()

print("\nStatistics grouped by difficulty:")
print(grouped_stats)

human_pdf = pd.DataFrame(columns=columns)
for file in human.iterdir():
    code= file.read_text(encoding="utf-8")
    n_lines=len(code.splitlines())
    human_pdf.loc[len(human_pdf)] = {
        "id": file.name,
        "n_lines": n_lines,
        "difficulty": "-"
    }
print(human_pdf.describe())