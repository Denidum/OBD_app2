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


def dencryption_table(data, client_id):
    a = data.split(".")
    dig = int(hex(int(a[0], 0)), 16)
    return dig - 5 * client_id

def dencryption_row(data, client_id):
    a = data.split(".")
    dig = int(hex(int(a[1], 0)), 16)
    return dig - 10 * client_id

#id_table = 4
#id_client = 1
#id_row = 7

#create_qr_code(encryption(id_table, id_client, id_row), "QR4.png")

#dencryption(read_qr_code("QR4.png"))

def db_plus_qr_info(id_table, id_client, id_row, time):
    id_qr=bd.count_id("table_user")
    name_file="QR"+str(id_qr)
    data = [id_qr, time, name_file, id_table]
    if bd.i==1:
        bd.cursor.execute("INSERT INTO table_user (id_qr, time, name_file_qr, id_name_table) VALUES(%s,%s,%s,%s);", data)
    else:
        bd.cursor.execute("INSERT INTO sys_human_table VALUES(?,?,?,?);",data)
    bd.connect.commit()
    return name_file

def db_read_qr_info(table, row, id):
    table=int(table)
    row=int(row)
    name_table=bd.info_table_name_table(table, id)
    name_col=bd.info_columns_name(bd.info_table_name_table(table, id), 0)
    data_name_col=bd.db_read_data_from_first_col(name_col, name_table, row)
    if bd.i == 1:
        bd.cursor.execute("SELECT * FROM "+name_table+" WHERE "+name_col+" = %s", (data_name_col,))
    else:
        bd.cursor.execute("SELECT * FROM "+name_table+" WHERE "+name_col+" = ?", (data_name_col,))

    next_row = bd.cursor.fetchone()

    return list(next_row)