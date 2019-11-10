import time

from constants.video_splitter_constants import NUMBER_OF_MILISECONDS_IN_A_SECOND


class TimestampUtils:

    @staticmethod
    def get_milliseconds(seconds):
        return int(round(seconds * NUMBER_OF_MILISECONDS_IN_A_SECOND))

    @staticmethod
    def get_current_milliseconds():
        return TimestampUtils.get_milliseconds(time.time())
