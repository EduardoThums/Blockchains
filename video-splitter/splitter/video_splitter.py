import os

from http_client.http_client import HttpClient


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
        HttpClient.make_request(self.get_miliseconds(self.starting_interval_seconds),
                                self.get_miliseconds(self.next_interval_seconds),
                                self.current_video_piece_filename)
        self.delete_file()


    def get_miliseconds(self, seconds):
        return int(round(seconds * 1000))


    def delete_file(self):
        os.remove(self.current_video_piece_filename)
