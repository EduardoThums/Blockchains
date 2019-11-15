import csv

import requests

from constants.http_client_constants import LOGGER_API_ROUTE
from constants.video_sender_constants import CSV_FIELD_NAMES, TEST_FILE_PATH


class CsvWriter:

    @staticmethod
    def write_log_csv_file():
        log_list = requests.get(LOGGER_API_ROUTE).json()

        with open(TEST_FILE_PATH, 'w') as write_file:
            writer = csv.DictWriter(write_file, fieldnames=CSV_FIELD_NAMES)
            writer.writeheader()

            for dict_object in log_list:
                writer.writerow(dict_object)
