from constants.video_sender_constants import MINIMUM_NUMBER_OF_REPETITIONS_EXPECTED
from http_client.http_client import HttpClient


if __name__ == '__main__':
    camera_id = 1
    start_date = 1599286751512
    end_date = 1599286766368

    videos = HttpClient.query_videos_by_camera(
        camera_id=camera_id,
        start_date=start_date,
        end_date=end_date
    )

    print(videos)
