import time

from constants.video_sender_constants import VIDEO_FILE_PATH, VIDEO_FILE_SECONDS
from csv_writer.csv_writer import CsvWriter
from splitter.video_sender.video_sender import VideoSender

if __name__ == '__main__':
    VideoSender.send_file_multiple_times(VIDEO_FILE_PATH, VIDEO_FILE_SECONDS)

    time.sleep(30)

    print("Creating csv file")
    CsvWriter.write_log_csv_file()
