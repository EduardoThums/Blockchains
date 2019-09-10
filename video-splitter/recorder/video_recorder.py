import cv2
import time
from constants.video_recorder_constants import STANDARD_DIMENSIONS, VIDEO_TYPES


class VideoRecorder:

    def __init__(
            self,
            base_filename='video',
            file_extension='avi',
            time_interval_in_seconds=10,
            video_fps=30,
            number_of_records=1,
            resolution='480p'):
        self.base_filename = base_filename
        self.file_extension = file_extension
        self.time_interval_in_seconds = time_interval_in_seconds
        self.video_fps = video_fps
        self.number_of_records = number_of_records
        self.video_capture = cv2.VideoCapture(0)
        self.resolution = resolution

    def get_output(self, out=None):
        if out:
            out.release()
        # Specify the path and name of the video file as well as the encoding, fps and resolution
        return cv2.VideoWriter(
            self.get_full_filename(time.time()),
            self.get_encoding(self.file_extension),
            self.video_fps,
            self.get_dimensions(self.resolution))

    def get_full_filename(self, starting_datime):
        final_datetime = starting_datime + self.time_interval_in_seconds;

        starting_datime_text = str(time.strftime('%d-%m-%Y - %H.%M.%S', time.localtime(starting_datime)))
        final_datime_text = str(time.strftime('%d-%m-%Y - %H.%M.%S', time.localtime(final_datetime)))

        formated_filename = str.format(
            "{} from {} to {}.{}",
            self.base_filename,
            starting_datime_text,
            final_datime_text,
            self.file_extension)

        return formated_filename

    def get_encoding(self, extension):
        return cv2.VideoWriter_fourcc(*VIDEO_TYPES[extension])

    # grab resolution dimensions and set video capture to it.
    def get_dimensions(self, resolution='480p'):
        width, height = STANDARD_DIMENSIONS["480p"]

        if resolution in STANDARD_DIMENSIONS:
            width, height = STANDARD_DIMENSIONS[resolution]

        # Change the current caputre device to the resulting resolution
        self.change_resolution(width, height)
        return width, height

    # Set resolution for the video capture
    def change_resolution(self, width, height):
        self.video_capture.set(3, width)
        self.video_capture.set(4, height)

    def record_video(self):
        output = self.get_output()
        next_time = time.time() + self.time_interval_in_seconds
        counter = 1

        while True:
            if time.time() > next_time:
                if counter >= self.number_of_records:
                    break

                counter += 1
                next_time += self.time_interval_in_seconds
                output = self.get_output(output)

            # Capture frame-by-frame
            ret, frame = self.video_capture.read()

            if ret:
                output.write(frame)

        output.release()
        self.video_capture.release()
        cv2.destroyAllWindows()
