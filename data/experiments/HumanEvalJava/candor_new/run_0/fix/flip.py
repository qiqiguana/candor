import sys
import os
import re

# Path to the test directory
#test_dir = "src/test/java/original/"
test_dir = sys.argv[1]

# Pattern to match and replace
pattern = r"(separateClassLoader\s*=\s*)true"

# Iterate over all Java files in the test directory
for dirpath,_,filenames in os.walk(test_dir):
    if dirpath.startswith("."):
        continue
    for filename in filenames:
        if filename.endswith("ESTest.java"):
            file_path = os.path.join(dirpath, filename)

            # Read file content
            with open(file_path, "r", encoding="utf-8") as file:
                content = file.read()

            # Replace 'false' with 'true' for separateClassLoader
            new_content = re.sub(pattern, r"\1false", content)

            # Write back only if changes were made
            if new_content != content:
                with open(file_path, "w", encoding="utf-8") as file:
                    file.write(new_content)
                print(f"Updated: {filename}")
