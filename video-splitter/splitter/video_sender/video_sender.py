import time

from constants.video_sender_constants import MINIMUM_NUMBER_OF_REPETITIONS_EXPECTED
from http_client.http_client import HttpClient
from utils.timestamp_utils import TimestampUtils


class VideoSender:

    @staticmethod
    def send_file_multiple_times(filename='sample.mp4', seconds_to_add=10):
        for index in range(MINIMUM_NUMBER_OF_REPETITIONS_EXPECTED):
            starting_timestamp = time.time()
            final_timestamp = starting_timestamp + seconds_to_add

            HttpClient.make_request(TimestampUtils.get_miliseconds(starting_timestamp),
                                    TimestampUtils.get_miliseconds(final_timestamp),
                                    filename)
