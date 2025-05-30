"""preprocessing LeetCode data."""
from pathlib import Path
import pickle 
import random
import shutil
target_project_path=Path("/home/qinghua/projects/matg/data/leetcode")
target_full_src_path=target_project_path/"src/main/java/original_full"
target_full_src_path.mkdir(parents=True, exist_ok=True)
target_src_path=target_project_path/"src/main/java/original"
target_src_path.mkdir(parents=True, exist_ok=True)
leetcode_original_path=Path("/home/qinghua/projects/matg/data/leetcode_original/leetcode/solution")
leetcode_readmes={}
readme_pkl_path = target_project_path / "readmes.pkl"
broken_ids=["0679",'0305', '0315', '0421', '0527', '0716', '0720', '0928', '0952',"1274", "2728", "0470", "0339", "2782", "3387", "0690", "2203", "1514", "1377", "2386", "0857", "2936",
    '1157', '1258', '1268', '1505', '1649', '1858', '2031', '2158',"1329","0372","1003",
    '2213', '2286', '2407', '2659', '2812', '2926', '3072', '3291','0094', '0100', '0107', '0173', '0222', '0229', '0288', '0295', '0301', '0310',
    '0331', '0448', '0489', '0536', '0545', '0554', '0617', '0642', '0658', '0692',
    '0699', '0737', '0773', '0802', '0811', '0886', '0952', '0999', '1022', '1038',
    '1169', '1200', '1239', '1268', '1273', '1282', '1291', '1408', '1467', '1548',
    '1604', '1670', '1774', '1993', '2197', '2201', '2288', '2307', '2420', '2463',
    '2508', '2583', '2689', '2761', '2817', '2899', '3003', '3085', '3310', '3376',
    '3385','0019', '0109', '0116', '0139', '0143', '0333', '0365', '0369', '0464', '0536',
 '0538', '0559', '0572', '0624', '0642', '0656', '0707', '0728', '0895', '0901',
 '0951', '0958', '0967', '1022', '1036', '1086', '1171', '1192', '1257', '1311',
 '1403', '1415', '1467', '1469', '1476', '1500', '1943', '2440', '2641', '2646',
 '2824', '2826', '2860', '2865', '2872', '2973', '3067', '3148', '3263', '3319',
 '3349', '3367', '3527',"2998", "1740", "0247", "1243", "0270", "1602", "3394", "1609", "2089", "0606",
    "3286", "2359", "1618", "3321", "2265", "0399", "0021", "1553", "0872", "3315",
    "3243", "2477", "2215", "2316", "2781", "0284", "0653", "2512", "1557", "2424",
    "0051", "0385", "1324", "1485", "0816", "0234", "0450", "2856", "1530", "2258",
    "1115", "1462", "1593", "0784", "0362", "0430", "0015", "1178", "2791","0437", "0515", "0112", "2095", "1236", "1123", "0687", "0671", "1778", "0889",
    "0637", "0145", "3023", "0364", "0250", "1430", "2353", "0654", "1244", "0865",
    "0725", "0742", "1237", "0589", "2096", "0855", "1008", "0508", "0669", "0971",
    "2046", "0590", "0381", "1428", "0102", "1315", "0549", "0702", "0863", "0082",
    "1325", "1810", "1634", "1448", "3063","2385", "0024", "1836", "0543", "3013", "0138", "0510", "0092", "1302", "1116",
    "1214", "0203", "0981", "1028", "0979", "0105", "2476", "2181", "0226", "1382",
    "0513", "0108", "1095", "1120", "0530", "0337", "0445", "1372", "1110", "0148",
    "1261", "0236", "2773", "0919", "0206", "0314", "2753", "0563", "1490", "3157",
    "2816", "1506", "1265", "0124", "0230",
            "1496","3248","0488","0860","0519","0384","0528","0497","2056","0657","0398","0382","0710","2870","1908","0157", "0191", "2736", "2276", "0432", "0820", "1627", "0683", "3094","0308", "1724", "3369", "2867", "1622", "0211", "2519", "1756", "0297","2569", "0281", "1631", "0480", "2709", "0277", "3008", "3064", "2921","0947", "0146", "0721", "0676", "3109", "0588", "1713", "3292", "1964","1632", "2967", "0758", "3031", "2935", "1586", "0604", "0677", "0160","0425", "1166", "1803", "0341", "0850", "1032", "0732", "1839", "0307","0374", "0327", "3187", "0158", "3006", "2426", "1065", "2479", "0304","2416", "1152", "0271", "1628", "3327", "0616","0142", "0212", "3045","0472", "1707", "0140", "3108", "1579", "0715", "3093", "0190", "0278","2940", "0431", "1489", "3253", "1804", "2977", "3165", "0535", "0839","0208"]

for folder in leetcode_original_path.iterdir():
    
    if folder.is_dir():
        print(f"Processing folder: {folder.name}")
        for problem_folder in folder.iterdir():
             if problem_folder.is_dir():
                print(f"Processing problem folder: {problem_folder.name}")
                id= problem_folder.name.split(".")[0]
                if id in broken_ids:
                    continue
                java_name=f"id_{id}.java"
                src_java_path= problem_folder/"Solution.java"
                
                readme_path= problem_folder/"README_EN.md"
                if not src_java_path.exists() or not readme_path.exists():
                    print(f"Source Java file does not exist: {src_java_path}")
                    continue
                content = src_java_path.read_text(encoding="utf-8")
                if "Node" in content or "Tree" in content or "ArrayReader" in content or "Semaphore" in content :
                    continue
                target_jave_path = target_full_src_path /java_name
                shutil.copy(src_java_path, target_jave_path)
                print(f"Copied {src_java_path} to {target_jave_path}")
                leetcode_readmes[java_name] = (problem_folder/"README_EN.md").read_text(encoding="utf-8") 



for java_file in target_full_src_path.iterdir():
    id=java_file.stem.split("_")[1] 
    new_class_name=f"Solution{id}"
    content=(target_full_src_path/java_file).read_text(encoding="utf-8")
    content = content.replace("class Solution", f"class {new_class_name}")
    content= content.replace("public class", "class")
    
    if "Set" in content:
        content="import java.util.Set;\n" + content
    if "List" in content:
        content="import java.util.List;\n" + content
    if "Map" in content:
        content="import java.util.Map;\n" + content
    if "Queue" in content:
        content="import java.util.Queue;\n" + content
    if "Deque" in content:
        content="import java.util.Deque;\n" + content
    if "Stack" in content:
        content="import java.util.Stack;\n" + content
    if "HashSet" in content:
        content="import java.util.HashSet;\n" + content
    if "HashMap" in content:
        content="import java.util.HashMap;\n" + content
    if "ArrayList" in content:
        content="import java.util.ArrayList;\n" + content
    if "LinkedList" in content:
        content="import java.util.LinkedList;\n" + content
    if "Arrays" in content:
        content="import java.util.Arrays;\n" + content
    if "ArrayDeque" in content:
        content="import java.util.ArrayDeque;\n" + content
    if "PriorityQueue" in content:
        content="import java.util.PriorityQueue;\n" + content
    if "Collections" in content:
        content="import java.util.Collections;\n" + content
    if "Random" in content:
        content="import java.util.Random;\n" + content
    if "Comparator" in content:
        content="import java.util.Comparator;\n" + content
    if "DecimalFormat" in content:
        content="import java.text.DecimalFormat;\n" + content
    if "Collectors" in content:
        content="import java.util.stream.Collectors;\n" + content
    if "IntStream" in content:
        content="import java.util.stream.IntStream;\n" + content
    if "IntBinaryOperator" in content:
        content="import java.util.function.IntBinaryOperator;\n" + content    
    if "Objects" in content:
        content="import java.util.Objects;\n" + content
    if "LinkedHashSet" in content:
        content="import java.util.LinkedHashSet;\n" + content

    content="package original;\n\n" + content
    with (target_full_src_path/java_file).open("w", encoding="utf-8") as f:
        f.write(content)

import re
def extract_description_and_difficulty(markdown_text):
    # Extract description content
    description_pattern = r'<!-- description:start -->(.*?)<!-- description:end -->'
    description_match = re.search(description_pattern, markdown_text, re.DOTALL)
    description = description_match.group(1).strip() if description_match else None

    # Extract difficulty
    difficulty_pattern = r'difficulty:\s*(\w+)'
    difficulty_match = re.search(difficulty_pattern, markdown_text)
    difficulty = difficulty_match.group(1) if difficulty_match else None

    return description, difficulty

def extract_readme_content(readmes):
    """
    Extracts the content of the README file.
    """ 
    new_readmes={   }
    for k, v in readmes.items():
        description, difficulty = extract_description_and_difficulty(v)
        if description and difficulty:
            new_readmes[k] = {
                "description": description,
                "difficulty": difficulty
            }
        else:
            print(f"Failed to extract from {k}")
    print(new_readmes["id_0001.java"])
    difficulties=set(v["difficulty"] for v in new_readmes.values())
    easy,medium,hard={}, {}, {}
    for k, v in new_readmes.items():
        if v["difficulty"]=="Easy":
            easy[k]=v["description"]
        elif v["difficulty"]=="Medium":
            medium[k]=v["description"]
        elif v["difficulty"]=="Hard":
            hard[k]=v["description"]
    return new_readmes, easy, medium, hard 

K=50
new_readmes, easy, medium, hard = extract_readme_content(leetcode_readmes)
sampled_easy,sampled_medium,sampled_hard=random.sample(list(easy.keys()), K), random.sample(list(medium.keys()), K), random.sample(list(hard.keys()), K)
sampled_names = sampled_easy + sampled_medium + sampled_hard
for file in target_full_src_path.iterdir():
    if file.name  in sampled_names:
        
        file_path=target_full_src_path/file.name

        shutil.copy(file_path, target_src_path/file.name)
pickle.dump(new_readmes, readme_pkl_path.open("wb"))