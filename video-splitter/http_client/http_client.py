import requests

from constants.http_client_constants import OPEN_FILE_FORMAT, DEFAULT_PARAM_NAME, DEFAULT_CAMERA_ID, \
    UPLOAD_API_ROUTE, QUERY_API_ROUTE
from utils.timestamp_utils import TimestampUtils


class HttpClient:

    @staticmethod
    def send_file(starting_interval_timestamp, next_interval_timestamp, file_path, camera_id=DEFAULT_CAMERA_ID):
        file = open(file_path, OPEN_FILE_FORMAT)
        log_start_date = TimestampUtils.get_current_milliseconds()

        requests.post(UPLOAD_API_ROUTE.format(camera_id,
                                              starting_interval_timestamp,
                                              next_interval_timestamp,
                                              log_start_date),
                      files={DEFAULT_PARAM_NAME: file})

    @staticmethod
    def search_video_interval(starting_timestamp, ending_timestamp, camera_id=DEFAULT_CAMERA_ID):
        log_start_date = TimestampUtils.get_current_milliseconds()

        requests.get(QUERY_API_ROUTE.format(camera_id,
                                            starting_timestamp,
                                            ending_timestamp,
                                            log_start_date))
