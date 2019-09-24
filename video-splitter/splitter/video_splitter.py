import os
from http_client.http_client import HttpClient


class VideoSplitter:

    def __init__(self, fragments_time_interval_in_seconds, video_capture, video_fps):
        self.fragments_time_interval_in_seconds = fragments_time_interval_in_seconds
        self.video_capture = video_capture
        self.video_fps = video_fps
        self.current_video_piece_filename = ''

    def release_output(self, output):
        output.release()
        HttpClient.make_request(self.current_video_piece_filename)
        self.delete_file()

    def delete_file(self):
        os.remove(self.current_video_piece_filename)
