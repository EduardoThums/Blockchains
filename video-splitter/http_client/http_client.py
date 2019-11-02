import time

import requests

from constants.http_client_constants import OPEN_FILE_FORMAT, DEFAULT_API_ROUTE, DEFAULT_PARAM_NAME, DEFAULT_CAMERA_ID


class HttpClient:

    @staticmethod
    def make_request(starting_interval_timestamp, next_interval_timestamp, file_path):
        route = DEFAULT_API_ROUTE + 'upload?cameraId={}&startDate={}&endDate={}&logStartDate={}'

        file = open(file_path, OPEN_FILE_FORMAT)
        log_start_date = int(round(time.time() * 1000))

        print("===> NEW FILE <===")
        print(starting_interval_timestamp)
        print(next_interval_timestamp)

        route_with_params = route.format(DEFAULT_CAMERA_ID,
                                         starting_interval_timestamp,
                                         next_interval_timestamp,
                                         log_start_date)
        requests.post(route_with_params, files={DEFAULT_PARAM_NAME: file})
