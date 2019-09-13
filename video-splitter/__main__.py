from recorder.video_recorder import VideoRecorder
from splitter.video_splitter import VideoSplitter

if __name__ == '__main__':
    VideoSplitter('sample_test.avi', 10).split()
