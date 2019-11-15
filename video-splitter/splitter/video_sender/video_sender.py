from constants.video_sender_constants import VIDEO_FILE_PATH, MINIMUM_NUMBER_OF_REPETITIONS_EXPECTED
from http_client.http_client import HttpClient
from utils.timestamp_utils import TimestampUtils


class VideoSender:

    @staticmethod
    def send_file_multiple_times(seconds_to_add=10):
        for index in range(MINIMUM_NUMBER_OF_REPETITIONS_EXPECTED):
            starting_timestamp = TimestampUtils.get_current_milliseconds()
            final_timestamp = starting_timestamp + TimestampUtils.get_milliseconds(seconds_to_add)

            print("-> New Video <-")
            print("Start date: {}".format(starting_timestamp))
            print("End date: {}".format(final_timestamp))

            HttpClient.send_file(
                starting_timestamp,
                final_timestamp,
                VIDEO_FILE_PATH)
