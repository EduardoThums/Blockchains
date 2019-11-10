from constants.video_sender_constants import MINIMUM_NUMBER_OF_REPETITIONS_EXPECTED
from http_client.http_client import HttpClient
from utils.timestamp_utils import TimestampUtils


class VideoSender:

    @staticmethod
    def send_file_multiple_times(filename='sample.mp4', seconds_to_add=10):
        final_timestamp = TimestampUtils.get_current_milliseconds()

        for index in range(MINIMUM_NUMBER_OF_REPETITIONS_EXPECTED):
            starting_timestamp = final_timestamp
            final_timestamp = starting_timestamp + float(seconds_to_add)

            HttpClient.search_video_interval(
                TimestampUtils.get_milliseconds(starting_timestamp),
                TimestampUtils.get_milliseconds(final_timestamp))
