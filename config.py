from pathlib import  Path
import os

class Config:
    def __init__(self):
        self.project_root=Path("/home/qinghua/projects/matg")
        self.prompt_template_path=self.project_root/"templates"
        self.experiment_path=Path(os.getcwd())
    
    def _initialize_with_args(self, args):
        """set the config with the args"""
        for arg_name, arg_value in vars(args).items():
            if "path" in arg_name:
                arg_value=Path(arg_value)
            setattr(self, arg_name, arg_value)
            
