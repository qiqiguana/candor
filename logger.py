import logging
from pathlib import Path


# define log format
log_format="%(asctime)s   %(levelname)s : %(message)s"
formatter=logging.Formatter(log_format)

# create a logger
logger=logging.getLogger("basic_logger")
logger.setLevel(logging.INFO)

# create a console handler -- send output to the console
console_handler=logging.StreamHandler()
console_handler.setFormatter(formatter)

# create a file handler for error logs
log_folder=Path("logs")
log_folder.mkdir(parents=True, exist_ok=True)
file_handler=logging.FileHandler("logs/error.log")
file_handler.setLevel(logging.ERROR)
file_handler.setFormatter(formatter)

# add handler to logger
logger.addHandler(console_handler)
logger.addHandler(file_handler)

if __name__=="__main__":
    # test the logger
    logger.debug("This is a debug message")
    logger.info("This is an info message")
    logger.warning("This is a warning message")
    logger.error("This is an error message")
    logger.critical("This is a critical message")

