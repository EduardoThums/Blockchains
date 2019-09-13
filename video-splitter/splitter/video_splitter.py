import cv2
import math
import time

from constants.video_splitter_constants import \
    NUMBER_OF_MILISECONDS_IN_A_SECOND, \
    DEFAULT_FILE_NAME, \
    DEFAULT_FILE_EXTENSION, \
    DEFAULT_AVI_ENCODING


class VideoSplitter:

    def __init__(self, video_path, fragments_interval):
        self.video_path = video_path
        self.fragments_interval = fragments_interval
        self.video_capture = cv2.VideoCapture(self.video_path)
        self.fps = self.video_capture.get(cv2.CAP_PROP_FPS)
        self.current_next_interval_second = 0
        self.video_dimensions = (int(self.video_capture.get(cv2.CAP_PROP_FRAME_WIDTH)),
                                 int(self.video_capture.get(cv2.CAP_PROP_FRAME_HEIGHT)))

    def get_output(self, out=None):
        if out:
            out.release()
        # Specify the path and name of the video file as well as the encoding, fps and resolution
        filename = str.format(
            '{} {}.{}',
            DEFAULT_FILE_NAME,
            time.strftime('%d-%m-%Y - %H.%M.%S'),
            DEFAULT_FILE_EXTENSION
        )

        return cv2.VideoWriter(
            filename,
            cv2.VideoWriter_fourcc(*DEFAULT_AVI_ENCODING),
            self.fps,
            self.video_dimensions
        )

    def write_frame(self, output, frame):
        current_second = math.floor(self.video_capture.get(cv2.CAP_PROP_POS_MSEC) / NUMBER_OF_MILISECONDS_IN_A_SECOND)

        if current_second == self.current_next_interval_second:
            self.increase_next_interval()
            output = self.get_output(output)

        output.write(frame)
        return output

    def increase_next_interval(self):
        self.current_next_interval_second += self.fragments_interval

    def split(self):
        output = self.get_output()
        self.increase_next_interval()

        while self.video_capture.isOpened():
            frame_exists, current_frame = self.video_capture.read()

            if frame_exists:
                output = self.write_frame(output, current_frame)
            else:
                break

        output.release()
        self.video_capture.release()
        cv2.destroyAllWindows()
