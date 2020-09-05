import time

import requests

from constants.http_client_constants import OPEN_FILE_FORMAT, DEFAULT_PARAM_NAME, DEFAULT_CAMERA_ID


class HttpClient:

    @staticmethod
    def make_request(starting_interval_timestamp, next_interval_timestamp, file_path):
        route = 'http://localhost:8080/api/upload?cameraId={}&startDate={}&endDate={}&logStartDate={}'

        with open(file_path, 'rb') as file:
            log_start_date = int(round(time.time() * 1000))

            print("===> NEW FILE <===")
            print(starting_interval_timestamp)
            print(next_interval_timestamp)

            route_with_params = route.format(DEFAULT_CAMERA_ID,
                                             starting_interval_timestamp,
                                             next_interval_timestamp,
                                             log_start_date)
            requests.post(route_with_params, files={DEFAULT_PARAM_NAME: file})

    @staticmethod
    def query_videos_by_camera(camera_id: int, start_date: int, end_date: int):
        url = f'http://localhost:8081/api/query/cameraId/{camera_id}' \
              f'?startDate={start_date}' \
              f'&endDate={end_date}' \
              f'&logStartDate=1'

        response = requests.get(url)

        return response.json()
