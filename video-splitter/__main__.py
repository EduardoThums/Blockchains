import time

from csv_writer.csv_writer import CsvWriter
from http_client.http_client import HttpClient
from splitter.video_sender.video_sender import VideoSender

if __name__ == '__main__':
    # print("Sending videos to API...")
    # VideoSender.send_file_multiple_times()

    print("Querying videos from API...")
    HttpClient.search_video_interval(1573799711635, 1573799728078)

    # time.sleep(20)

    print("Creating CSV file...")
    CsvWriter.write_log_csv_file()
