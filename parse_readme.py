import pickle
readmes=pickle.load(open('readmes.pkl', 'rb'))
class_id_mapping_path = '/home/qinghua/projects/matg/data/leetcode_dataset/candor0/class_id_mapping.pkl'
class_id_mapping= pickle.load(open(class_id_mapping_path, 'rb'))
id_class_mapping = {v: k for k, v in class_id_mapping.items()}
print(id_class_mapping)
class_difficulty_mapping = {}

for class_id, class_name in id_class_mapping.items():
    readme = readmes[f'{class_id}.java']
    difficulty = readme["difficulty"]
    class_difficulty_mapping[class_name] = difficulty

# write the mapping to csv
import csv
with open('leetcode_class_difficulty_mapping.csv', 'w', newline='') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow(['CLASS', 'DIFFICULTY'])
    for class_name, difficulty in class_difficulty_mapping.items():
        writer.writerow([class_name, difficulty])
        