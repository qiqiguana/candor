
import csv
import os
import pickle
import subprocess

# Adjust these paths based on your project structure
project_dir= "/home/qinghua/projects/matg/data/experiments/HumanEvalJava/evosuite/run_0"

test_dir=os.path.join(project_dir,"src/test/java/original")
# Find all *_ESTest.java files
test_files = [f for f in os.listdir(test_dir) if f.endswith("_ESTest.java")]

# Extract original class names from test filenames
class_names = [f.replace("_ESTest.java", "") for f in test_files]

# Run PIT for each class based on test files
# for class_name in class_names:
#     print(f"\n🔍 Running PIT for: {class_name}")
    
#     cmd = [
#         "mvn",
#         f"-DtargetClasses=original.{class_name}",
#         f"-DtargetTests=original.{class_name}_ESTest",
#         "-Djava.awt.headless=true",
#         "org.pitest:pitest-maven:mutationCoverage",
#         "-Dfeatures=+EXPORT",
#         "-f",
#         f"{project_dir}/pom.xml",
#     ]
    
#     result = subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    
#     # Save output to log file
#     log_filename = f"pit_{class_name}.log"
#     with open(log_filename, "w") as log_file:
#         log_file.write(result.stdout)
    
#     if result.returncode == 0:
#         print(f"✅ Success for {class_name}")
#     else:
#         print(f"❌ Failed for {class_name} (see {log_filename})")


from bs4 import BeautifulSoup

def extract_mutation_score_from_file(html_path):
    with open(html_path, 'r', encoding='utf-8') as file:
        soup = BeautifulSoup(file, 'html.parser')

    # Mutation statuses
    killed = 0
    survived = 0

    # Find all <td class='killed'> and <td class='survived'>
    for td in soup.find_all('td', class_=['killed', 'survived']):
        tooltip_span = td.find('span', class_='pop')
        if not tooltip_span:
            continue
        title = tooltip_span.get_text().lower()

        # Determine how many mutations are mentioned in the tooltip
        mutations = title.strip().split('\n')
        count = sum(1 for line in mutations if line.strip())
        
        if 'killed' in title:
            killed += count
        elif 'survived' in title:
            survived += count

    total = killed + survived
    if total == 0:
        print("No mutations found.")
        return 0.0,0,0

    score = killed / total
    return round(score, 2),killed, total

def process_directory(input_dir, output_csv):
    results = []
    class_id_mapping = pickle.load(open(f'{project_dir}/class_id_mapping.pkl', 'rb'))
    id_class_mapping = {v: k for k, v in class_id_mapping.items()}
    
    for filename in os.listdir(input_dir):
        if filename.endswith('java.html'):
            filepath = os.path.join(input_dir, filename)
            id = os.path.splitext(filename)[0].split(".")[0]
            class_name=id_class_mapping[id] 
            score,killed,total = extract_mutation_score_from_file(filepath)
            results.append((class_name, score,killed,total))

    with open(output_csv, 'w', newline='', encoding='utf-8') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(['CLASS', 'mutation_score',"killed", "total"])
        writer.writerows(results)

    print(f"Written mutation scores for {len(results)} classes to {output_csv}")
# # evosuite
# html_dir= f'{project_dir}/target/pit-reports/original'
# save_path = '/home/qinghua/projects/matg/evosuite_0_mutantion_score.csv'  # Change to your filename
# process_directory(html_dir, save_path)
# # candor
# html_dir = 'data/experiments/HumanEvalJava/candor/run_0/generate/target/pit-reports/original'
# save_path = '/home/qinghua/projects/matg/candor_0_mutantion_score.csv'  # Change to your filename

# process_directory(html_dir, save_path)
project_dir="/home/qinghua/projects/matg/data/experiments/Leetcode/evosuite/run_0"
html_dir = f'{project_dir}/target/pit-reports/original'
save_path = '/home/qinghua/projects/matg/evosuite_leetcode_0_mutantion_score.csv'  # Change to your filename
process_directory(html_dir, save_path)
# process empirical data
project_dir="/home/qinghua/projects/matg/data/experiments/HumanEvalJava/empirical/best"
html_dir= f'{project_dir}/target/pit-reports/original'
save_path = '/home/qinghua/projects/matg/empirical_human_0_mutantion_score.csv'  # Change to your filename
process_directory(html_dir, save_path)