import requests

from constants.http_client_constants import OPEN_FILE_FORMAT, DEFAULT_API_ROUTE, DEFAULT_PARAM_NAME, DEFAULT_CAMERA_ID
from utils.timestamp_utils import TimestampUtils


class HttpClient:

    @staticmethod
    def send_file(starting_interval_timestamp, next_interval_timestamp, file_path, camera_id=DEFAULT_CAMERA_ID):
        route = DEFAULT_API_ROUTE + 'upload?cameraId={}&startDate={}&endDate={}&logStartDate={}'

        file = open(file_path, OPEN_FILE_FORMAT)
        log_start_date = TimestampUtils.get_current_milliseconds()

        print("===> NEW FILE <===")
        print(starting_interval_timestamp)
        print(next_interval_timestamp)

        route_with_params = route.format(camera_id,
                                         starting_interval_timestamp,
                                         next_interval_timestamp,
                                         log_start_date)
        requests.post(route_with_params, files={DEFAULT_PARAM_NAME: file})

    @staticmethod
    def search_video_interval(starting_timestamp, ending_timestamp, camera_id=DEFAULT_CAMERA_ID):
        route = DEFAULT_API_ROUTE + 'blockchain/cameraId/{}?startDate={}&endDate={}&logStartDate={}'

        log_start_date = TimestampUtils.get_current_milliseconds()

        route_with_params = route.format(camera_id,
                                         starting_timestamp,
                                         ending_timestamp,
                                         log_start_date)
        requests.get(route_with_params)
