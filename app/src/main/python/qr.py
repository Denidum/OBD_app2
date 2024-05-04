import qrcode
import png
import cv2

import bd


#створює Qr. data - інформація яку шуфруєм, name - назва Qr
def create_qr_code(data, filename):
    qr = qrcode.QRCode(version=1, box_size=10, border=5)
    qr.add_data(data)
    qr.make(fit=True)

    img = qr.make_image(fill_color="black", back_color="white")
    img.save(filename)

    return filename

def read_qr_code(image_path):
    image = cv2.imread(image_path)
    detector = cv2.QRCodeDetector()
    data, vertices_array, binary_qrcode = detector.detectAndDecode(image)
    return data

def encryption(id_table, id_client, id_row):
    a = str(hex(id_table + 5 * id_client))
    b = str(hex(id_row + 10 * id_client))
    return a + "." + b

def dencryption(data):
    a = data.split(".")
    return a

#id_table = 4
#id_client = 1
#id_row = 7

#create_qr_code(encryption(id_table, id_client, id_row), "QR4.png")

#dencryption(read_qr_code("QR4.png"))

def db_plus_qr_info(id_table, id_client, id_row, time):
    id_qr=bd.count_id("table_user")
    name_file="QR"+str(id_qr)+".png"
    create_qr_code(encryption(id_table, id_client, id_row), name_file)
    data = [id_qr, time, name_file, id_table]
    if bd.i==1:
        bd.cursor.execute("INSERT INTO table_user (id_qr, time, name_file_qr, id_name_table) VALUES(%s,%s,%s,%s);", data)
    else:
        bd.cursor.execute("INSERT INTO sys_human_table VALUES(?,?,?,?);",data)
    bd.connect.commit()
    return name_file