from splitter.file_splitter.file_splitter import FileSplitter
from splitter.recorder.video_recorder import VideoRecorder
from splitter.video_sender.video_sender import VideoSender

if __name__ == '__main__':
    VideoSender.send_file_multiple_times('sample.mp4', 10)
