from constants.video_sender_constants import MINIMUM_NUMBER_OF_REPETITIONS_EXPECTED
from http_client.http_client import HttpClient


if __name__ == '__main__':
    camera_id = 1
    start_date = 1599602991935
    end_date = 1599603019103

    videos = HttpClient.query_videos_by_camera(
        camera_id=camera_id,
        start_date=start_date,
        end_date=end_date
    )

    print(videos)

    print(len(videos))

    #1599326469584
    #1599326479584
