import os
from datetime import datetime

# Header README
header = f"""# 📱 Mobile Programming Development (S1 - 2025)
**Author:** Hafizh Hilman Asyhari  
**Last Update:** {datetime.now().strftime('%d %B %Y, %H:%M')}  
---

## 📘 Struktur Repository
"""

# Dapatkan struktur folder
tree = ""
for root, dirs, files in os.walk(".", topdown=True):
    if root.startswith("./.") or any(x in root for x in ["__pycache__", "venv"]):
        continue
    level = root.replace("./", "").count(os.sep)
    indent = "│   " * level
    folder_name = os.path.basename(root)
    if folder_name == "":
        folder_name = "root"
    tree += f"{indent}├── {folder_name}/\n"
    for f in files:
        if f.endswith(".md") or f.endswith(".kt") or f.endswith(".xml"):
            tree += f"{indent}│   ├── {f}\n"

# Auto progress
progress = """
---

## 📊 Weekly Progress
| Minggu | Materi | Status |
|:-------:|:------------------------|:----------:|
| 01 | UI Hierarchy & ViewGroup | ✅ |
| 02 | Layout & Widget | ✅ |
| 03 | Event Handling | 🔄 |
| 04–16 | Materi lanjut | ⏳ |

---

## 🧰 Auto Update
README ini diperbarui otomatis oleh GitHub Actions setiap kali kamu push atau setiap Senin.
"""

# Gabungkan semua bagian
content = header + "```\n" + tree + "```\n" + progress

# Tulis ke file README.md
with open("README.md", "w", encoding="utf-8") as f:
    f.write(content)

print("✅ README.md generated successfully!")
