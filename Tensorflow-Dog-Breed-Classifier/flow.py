import glob
import warnings
warnings.simplefilter(action='ignore', category=FutureWarning)
import classifier
import time
import threading
import socket
from PIL import Image
import imagehash
import argparse
import cv2
HOST = "192.168.1.179"
PORT = 8888
breed = ""

def capture(camid,start):
    cam = cv2.VideoCapture(0,camid)
    if not cam.isOpened():
        print('cant open the cam (%d)' % camid)
        return None
    ret, frame = cam.read()
    if frame is None:
        print('frame is not exist')
        return None
    if start:
        cv2.imwrite('1.jpg', frame, params=[cv2.IMWRITE_JPEG_QUALITY, 100])
    else:
        cv2.imwrite('2.jpg', frame, params=[cv2.IMWRITE_JPEG_QUALITY, 100])
    cam.release()
    cv2.destroyAllWindows()

def do():
    hash0 = imagehash.average_hash(Image.open('1.jpg'))
    hash1 = imagehash.average_hash(Image.open('2.jpg'))

    if Classifier.detect(): # if dog is found
        if Classifier.predict(): # same as given breed
            print("open : current breed")
        else: # else
            print("close : different breed")
    else: # if there's no dog
        if (hash0 - hash1) < 5 : # if nothing changed (no movings)
            print("open : no dogs in the scene")
        else: #if dog is eating so it cannot detect dog
            print("continue : dog may eating feed") # stay as opened or not

def register():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print('Socket created')
    s.bind((HOST, PORT))
    print('Socket bind complete')
    s.listen(1)
    print('Socket now listening')

    # 접속 승인
    conn, addr = s.accept()
    print("Connected by ", addr)

    with open('Client.jpg', 'wb') as f:
        while True:
            print('receiving data...')
            data = conn.recv(1024)

            if not data:
                break
            # write data to a file
            f.write(data)

    f.close()
    print('close')

    # 연결 닫기
    conn.close()

    s.close()

    # 연결 닫기
    register = classifier.Dogclassify("Client.jpg", None)
    array = register.register()

    s2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print('Socket2 created')
    s2.bind((HOST, PORT + 1))
    print('Socket bind complete')
    s2.listen(1)
    print('Socket now listening')

    # 접속 승인
    conn2, addr2 = s2.accept()
    print("Connected by ", addr2)

    b = array[0] + "/" + array[1] + "/" + array[2] + "/" + array[3] + "/" + array[4]
    print(b)
    conn2.sendall(b.encode("utf-8"))

    # 연결 닫기
    conn2.close()

    s2.close()


    s3 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print('Socket3 created')
    s3.bind((HOST, PORT+2))
    print('Socket bind complete')
    s3.listen(1)
    print('Socket now listening')

    # 접속 승인
    conn3, addr3 = s3.accept()
    print("Connected by ", addr3)

    answer = conn3.recv(1024)
    answer = answer.decode("utf8").strip()

    # 연결 닫기
    conn3.close()

    s3.close()
    f = open("breed.txt",'a')
    f.write(answer+'\n')
    f.close()
    print(answer +" is registered")

FLAGS = None
parser = argparse.ArgumentParser()
parser.add_argument(
    '--breed',
    type=str,
    default='beagle',
    help='number of crawling images'
)
FLAGS, unparsed = parser.parse_known_args()
if FLAGS.breed!=None  :
    breed = FLAGS.breed

while True:
    print("1 : server on\n2 : execute")
    command = input()
    if command == '1':
        while True:
            register()
    elif command == '2':
        with open("breed.txt","r") as file:
            content = list()
            while True:
                sentence = file.readline()
                if sentence:
                    content.append(sentence[:-1])
                else:
                    break
        breed = content[len(content)-1]
        print(breed)
        start = True
        while True:
            if start:
                capture(cv2.CAP_DSHOW, start)
                start = False
            capture(cv2.CAP_DSHOW, start)
            Classifier = classifier.Dogclassify("2.jpg", breed)
            do()
            time.sleep(3)
    else:
        print("wrong input");


