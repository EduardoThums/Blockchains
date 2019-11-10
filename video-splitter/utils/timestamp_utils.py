from constants.video_splitter_constants import NUMBER_OF_MILISECONDS_IN_A_SECOND


class TimestampUtils:

    @staticmethod
    def get_miliseconds(seconds):
        return int(round(seconds * NUMBER_OF_MILISECONDS_IN_A_SECOND))
