import pyqrcode
import png
import cv2


#створює Qr. data - інформація яку шуфруєм, name - назва Qr
def create_qr(data, name):
    url = pyqrcode.create(data)
    url.png(name , scale = 100)


#Читає Qr. data - інформація яку шуфруєм, name - назва Qr
def read_qr(data, name):
    url = pyqrcode.create(data)
    url.png(name, scale = 100)
    img = cv2.imread(name)
    det = cv2.QRCodeDetector()
    val, pts, st_code = det.detectAndDecode(img)
    print(val)