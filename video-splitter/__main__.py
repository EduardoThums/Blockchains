from splitter.file_splitter.file_splitter import FileSplitter
from splitter.recorder.video_recorder import VideoRecorder

if __name__ == '__main__':
    FileSplitter('sample.mp4', 10).split()
