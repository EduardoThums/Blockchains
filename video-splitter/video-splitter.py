import cv2
import time

def get_output(out=None):
    #Specify the path and name of the video file as well as the encoding, fps and resolution
    if out:
        out.release()
    return cv2.VideoWriter('videozin top ' + str(time.strftime('%d %m %Y - %H %M %S')) + '.avi', cv2.VideoWriter_fourcc('M','J','P','G'), 30, (640,480))


cap = cv2.VideoCapture(0)
size = (int(cap.get(cv2.CAP_PROP_FRAME_WIDTH)),
        int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT)))

next_time = time.time() + 10
out = get_output()
cont = 0

while True:
    if cont > 1:
        break

    if time.time() > next_time:
        cont += 1
        next_time += 10
        out = get_output(out)

    # Capture frame-by-frame
    ret, frame = cap.read()

    if ret:
        out.write(frame)

cap.release()
cv2.destroyAllWindows()