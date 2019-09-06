import cv2
import time


BASE_FILENAME = 'video'
FILE_EXTENSION = 'avi'
TIME_INTERVAL_IN_SECONDS = 10
FPS = 30

def get_full_filename(starting_datime):
    final_datetime = starting_datime + TIME_INTERVAL_IN_SECONDS;

    starting_datime_text = str(time.strftime('%d %m %Y - %H:%M:%S', time.localtime(starting_datime)))
    final_datime_text = str(time.strftime('%d %m %Y - %H:%M:%S', time.localtime(final_datetime)))

    formated_filename = str.format("{} from {} to {}.{}", BASE_FILENAME, starting_datime_text, final_datime_text, FILE_EXTENSION)

    return formated_filename

def get_output(out=None):
    if out:
        out.release()
     #Specify the path and name of the video file as well as the encoding, fps and resolution
    return cv2.VideoWriter(get_full_filename(time.time()), get_encoding(FILE_EXTENSION), FPS, get_dimensions("480p"))

# Standard Video Dimensions Sizes
STANDARD_DIMENSIONS =  {
    "480p": (640, 480),
    "720p": (1280, 720),
    "1080p": (1920, 1080),
    "4k": (3840, 2160),
}

# Video Encoding, might require additional installs
# Types of Codes: http://www.fourcc.org/codecs.php
VIDEO_TYPE = {
    'avi': cv2.VideoWriter_fourcc(*'XVID'),
    #'mp4': cv2.VideoWriter_fourcc(*'H264'),
    'mp4': cv2.VideoWriter_fourcc(*'XVID'),
}


# grab resolution dimensions and set video capture to it.
def get_dimensions(resolution='1080p'):
    width, height = STANDARD_DIMENSIONS["480p"]

    if resolution in STANDARD_DIMENSIONS:
        width,height = STANDARD_DIMENSIONS[resolution]

    ## change the current caputre device
    ## to the resulting resolution
    change_resolution(width, height)
    return width, height

# Set resolution for the video capture
def change_resolution(width, height):
    video_capture.set(3, width)
    video_capture.set(4, height)

def get_encoding(ext):
    return VIDEO_TYPE[ext]

video_capture = cv2.VideoCapture(0)
next_time = time.time() +  TIME_INTERVAL_IN_SECONDS
output = get_output()
cont = 0

while True:

    if time.time() > next_time:
        if cont > 1:
            break

        cont += 1
        next_time += TIME_INTERVAL_IN_SECONDS
        output = get_output(output)

    # Capture frame-by-frame
    ret, frame = video_capture.read()

    if ret:
        output.write(frame)

video_capture.release()
output.release()
cv2.destroyAllWindows()
