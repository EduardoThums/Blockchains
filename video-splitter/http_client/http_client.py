import requests
from constants.http_client_constants import OPEN_FILE_FORMAT, DEFAULT_API_ROUTE, DEFAULT_PARAM_NAME


class HttpClient:

    @staticmethod
    def make_request(file_path):
        file = open(file_path, OPEN_FILE_FORMAT)
        requests.post(DEFAULT_API_ROUTE, files={DEFAULT_PARAM_NAME: file})
