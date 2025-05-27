import os
from pathlib import Path

if __name__ == "__main__":
    full_code = ""
    extensions = ["java", "vert", "frag", "config"]

    home = os.environ["HOME"]
    for ext in extensions:
        files = Path("src/").glob(f"**/*.{ext}")
        for path in files:
            filename = path.name
            txt_content = "".join(path.read_text().split())

            full_code += txt_content + "\n\n"

    with open("full_code.txt", "w+") as f:
        f.write(full_code)