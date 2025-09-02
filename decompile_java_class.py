import subprocess
import os

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

# Example usage:
decompile_class_file("/home/qinghua/projects/matg/data/HumanEvalJava/mutants/target/pit-reports/export/original/Solve1/mutants/0/original.Solve1.class", ".")


