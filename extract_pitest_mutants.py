import subprocess
import os
from pathlib import Path
import pickle
def decompile_class_file(class_file_path, output_dir, cfr_jar_path="/home/qinghua/projects/matg/cfr-0.152.jar"):
    if not os.path.exists(class_file_path):
        raise FileNotFoundError(f"Class file not found: {class_file_path}")
    
    if not os.path.exists(cfr_jar_path):
        raise FileNotFoundError(f"CFR jar not found: {cfr_jar_path}")
    
    os.makedirs(output_dir, exist_ok=True)

    command = [
        "java", "-jar", cfr_jar_path,
        class_file_path,
        "--outputdir", output_dir
    ]
    
    try:
        result = subprocess.run(command, capture_output=True, text=True, check=True)
        print("Decompilation successful.")
        print(result.stdout)
    except subprocess.CalledProcessError as e:
        print("Error during decompilation:")
        print(e.stderr)


count_mutants={}
stored_class_files_paths={}
mutant_dirs=Path("/home/qinghua/projects/matg/data/HumanEvalJava/mutants/target/pit-reports/export/original")

# HumanEvalJava_mut_0/notest/src/main/java

for class_dir in mutant_dirs.iterdir():
    count_mutants[class_dir.name]=0
    stored_class_files_paths[class_dir.name]=[]
    if not class_dir.is_dir():
        continue
    print(f"Processing mutant directory: {class_dir}")
    mutant_dir=class_dir/"mutants"
    for mutant_i in mutant_dir.iterdir():
        class_file=list( mutant_i.glob("*.class"))[0]
        stored_class_files_paths[class_dir.name].append(class_file)
        count_mutants[class_dir.name]+=1
print("Mutant counts per directory:")
for dir_name, count in count_mutants.items():
    print(f"{dir_name}: {count} mutants")
print("max mutant count:", max(count_mutants.values()))
print("min mutant count:", min(count_mutants.values()))
print("average mutant count:", sum(count_mutants.values())/len(count_mutants))
for run in range(3):
    project_root=Path(f"/home/qinghua/projects/matg/data/experiments/HumanEvalJava_mut_{run}/notest")
    output_dir=project_root/"src/main/java"
    package_dir=output_dir/"original"
    # clear all java files in the output directory
    for java_file in package_dir.glob("*.java"):
        java_file.unlink()
    # decompile
    for k, v in stored_class_files_paths.items():
        print(f"{k}: {len(v)} class files")
        if run>len(v)-1:
            class_file_path= v[0]
        else:
            class_file_path= v[run]
        decompile_class_file(class_file_path, output_dir)
    
    
    # clean up
    package_dir=output_dir/"original"
    class_id_mapping_path= project_root/"class_id_mapping.pkl"
    class_id_mapping=pickle.load(open(class_id_mapping_path, "rb"))
    for java_file in package_dir.iterdir():
        print(f"Processing java file: {java_file}")
        if not java_file.name.endswith(".java"):
            continue
        class_name=java_file.stem
        if class_name not in class_id_mapping:
            java_file.unlink()
        else:
            new_name = f"{class_id_mapping[class_name]}.java"
            new_path = java_file.parent / new_name
            java_file.rename(new_path)
        
    