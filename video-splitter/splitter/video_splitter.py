import os

from http_client.http_client import HttpClient
from utils.timestamp_utils import TimestampUtils


class VideoSplitter:

    def __init__(self, fragments_time_interval_in_seconds, video_capture, video_fps):
        self.fragments_time_interval_in_seconds = fragments_time_interval_in_seconds
        self.video_capture = video_capture
        self.video_fps = video_fps
        self.current_video_piece_filename = ''
        self.starting_interval_seconds = None
        self.next_interval_seconds = None

    def release_output(self, output):
        output.release()

        HttpClient.send_file(TimestampUtils.get_milliseconds(self.starting_interval_seconds),
                             TimestampUtils.get_milliseconds(self.next_interval_seconds),
                             self.current_video_piece_filename)

        self.delete_file()

    def delete_file(self):
        os.remove(self.current_video_piece_filename)
