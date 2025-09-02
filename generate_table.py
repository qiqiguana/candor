## fill jinja template with the source code metadata
from pathlib import Path
from jinja2 import Environment, FileSystemLoader
template_path="/home/qinghua/projects/matg/latex_tables"
rq1_save_path=Path(template_path)/"rq1"
jinja_env=Environment(loader=FileSystemLoader( template_path))
result={
    "empirical":{
        "human":{
            "line":"0.885",
            "branch":"0.930",
            "mutation":"0.910 (2223/2443)",
        },
        "leetcode":{
            "easy":{
                "line":"0.84",
                "branch":"0.84",
                "mutation":"0.84",
            },
            "medium":{
                "line":"0.877",
                "branch":"0.902",
                "mutation":"0.804 (491/611)",
            },
            "hard":{
                "line":"0.853",
                "branch":"0.927",
                "mutation":"0.901 (774/859)",
            },
        }
    },
    "evosuite":{
        "human":{
            "line":"0.961",
            "branch":"0.942",
            "mutation":"0.858 (2096/2443)",
        },
        "leetcode":{
            "easy":{
                "line":"0.978",
                "branch":"0.983",
                "mutation":"0.84",
            },
            "medium":{
                "line":"0.959",
                "branch":"\\textbf{0.959}",
                "mutation":"0.845 (516/611)",
            },
            "hard":{
                "line":"0.984",
                "branch":"0.976",
                "mutation":"0.888 (763/859)",
            },
        }
    },
    "candor":{
        "human":{
            "line":"\\textbf{0.991}",
            "branch":"\\textbf{0.970}",
            "mutation":"\\textbf{0.980} (2384/2443)",
        },
        "leetcode":{
            "easy":{
                "line":"0.84",
                "branch":"0.84",
                "mutation":"0.84",
            },
            "medium":{
                "line":"\\textbf{0.990}",
                "branch":"0.949",
                "mutation":"\\textbf{0.939} (574/611)",
            },
            "hard":{
                "line":"\\textbf{0.989}",
                "branch":"\\textbf{0.980}",
                "mutation":"\\textbf{0.937} (805/859)",
            },
        }
    },
}
rq1_template=jinja_env.get_template("rq1.jinja")
rq1_file=rq1_template.render({"result": result})

print(rq1_file)