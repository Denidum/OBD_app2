
import db_mysql
import db
import sqlite3
import os.path
import socket
import qr
hostname=socket.gethostname()
IPAddr=socket.gethostbyname(hostname)


import mysql.connector

try:
    connect = mysql.connector.connect(
        host=IPAddr,
        #host="192.168.1.105",
        user="client",
        password="KillJoy98",
        database="qdms"
    )
    print("online")
    i=1
except:
    package_dir = os.path.abspath(os.path.dirname(__file__))
    db_dir = os.path.join(package_dir, 'list.db')

    connect = sqlite3.connect(db_dir)
    print("local")
    i=0

cursor = connect.cursor()

def add_db_plus4():
    db.db_plus4("sys_human_list", "id", "integer", "login", "text","password", "text","email", "text")
def add_baza_human_local(): #додати людину до списку людей
    #count = db.count_id("sys_human_list")
    #data_name = [count, "Nick", "1836", "example4@gmail.com"] #Тестові дані
    db.db_plus5("sys_human_table", "id_user","integer","id_name_table","integer","number_row","integer", "time_created", "text", "name_table", "text")
    #db.insert_data_db("sys_human_list", data_name, 3)

def check_human():
    name_test="Tanya"
    name_test1="Danya"
    pasww_test="1234"
    pasww_test1="1236"
    print(db.check_info_human(name_test1, pasww_test1))

def count_id(name_table):
    count=0
    cursor.execute("SELECT * FROM " + name_table)
    result = cursor.fetchall()
    for row in result:
        count = row[0]
    return count

def add_baza_human(name, pasww, email): #додати людину до списку людей
    cursor.execute("SELECT * FROM sys_human_list")
    while True:
        next_row = cursor.fetchone()
        if next_row:
            (id, login, passw, em) = next_row
            if name == login:
                return "Login"
            if passw== pasww:
                return "Pasww"
            if em == email:
                return "Email"
            else:
                pass
        else:
            data_name = [count_id("sys_human_list"), name, pasww, email]
            if i==1:
                cursor.execute("INSERT INTO sys_human_list (id, login, password, email) VALUES(%s,%s,%s,%s);", data_name)
            else:
                cursor.execute("INSERT INTO sys_human_list VALUES(?,?,?,?);", data_name)
            connect.commit()
            return "Correct"

def add_my_sgl():
    cursor.execute(""" CREATE TABLE IF NOT EXISTS sys_human_table(id_user integer, id_name_table integer, number_row integer, time_created time) """)
    connect.commit()

#Видалити рядок
def delete_mysql():
    name=["Danyi", ]
    cursor.execute("DELETE FROM sys_human_list WHERE login = %s", name)
    connect.commit()

def info_table(id):
    cursor.execute("SELECT * FROM sys_human_list where id = "+ str(id))
    next_row = cursor.fetchone()
    connect.commit()
    return next_row

def add_colums():
    cursor.execute("ALTER TABLE sys_human_table ADD name_table text")
    connect.commit()

def alter_colums():
    cursor.execute("ALTER TABLE sys_human_table MODIFY time_created text")
    connect.commit()

#Рахує рядки
def size_table():
    cursor.execute("SELECT COUNT(login) FROM sys_human_list")
    count= cursor.fetchone()
    connect.commit()
    return int(count[0])

def size_table_sys(id):
    cursor.execute("SELECT COUNT(id_name_table) FROM sys_human_table Where id_user=" + str(id))
    count_row = cursor.fetchone()

    if count_row is not None:
        count = int(count_row[0])
    else:
        count = 0

    connect.commit()
    return count

def db_plus_table(name_table, name1, type1, name2, type2, name3, type3):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""("""
                   + name1 +""" """+ type1 + """, """
                   + name2 +""" """+ type2 + """, """
                   + name3 +""" """+ type3 + """) """)
    connect.commit()
    return "3"
def db_plus_table_info():
    data = [0, 0, 3, "21:34", "Test"]
    if i==1:
        cursor.execute("INSERT INTO sys_human_table (id_user, id_name_table, number_row, time_created, name_table) VALUES(%s,%s,%s,%s,%s);", data)
    else:
        cursor.execute("INSERT INTO sys_human_table VALUES(?,?,?,?,?);",data)
    connect.commit()
    return "correct"

def check_id_user(name_user):
    if i == 1:
        cursor.execute("SELECT * FROM sys_human_list WHERE login = %s", (name_user,))
    else:
        cursor.execute("SELECT * FROM sys_human_list WHERE login = ?", (name_user,))

    next_row = cursor.fetchone()

    if next_row is not None:
        (id, login, passw, em) = next_row
        connect.commit()
        return id
    else:
        return None

#змінити дані
def change_data():
    cursor.execute("UPDATE sys_human_list SET password = '0000' WHERE login = 'admin'")
    connect.commit()

def db_plus4(name_table, name1, type1, name2, type2, name3, type3, name4, type4):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""("""
                   + name1 +""" """+ type1 + """, """
                   + name2 +""" """+ type2 + """, """
                   + name3 +""" """+ type3 + """, """
                   + name4 +""" """+ type4 + """) """)
    connect.commit()

def add_PK_colums():
    cursor.execute("ALTER TABLE sys_human_table ADD CONSTRAINT id_tablePK PRIMARY KEY (id_name_table)")
    connect.commit()

def del_PK_colums():
    cursor.execute("ALTER TABLE sys_human_table DROP PRIMARY KEY;")
    connect.commit()
def add_FK_colums():
    cursor.execute("ALTER TABLE table_user ADD CONSTRAINT id_nameFK FOREIGN KEY (id_name_table) REFERENCES sys_human_table(id_name_table);")
    connect.commit()

def info_table_name_table(id_table, id):
    if i==1:
        cursor.execute("SELECT * FROM sys_human_table where id_name_table = %s and id_user = %s",(id_table,id))
    else:
        cursor.execute("SELECT * FROM sys_human_table WHERE id_name_table = ? AND id_user = ?",
                       (id_table, id))
    next_row = cursor.fetchone()
    if next_row is not None:
        (id, id_table, row, time, name) = next_row
        connect.commit()
        return name
    else:
        return None

def info_columns_name(name_table, number):
    t=0
    nc=0
    if i == 1:
        cursor.execute("SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'qdms' AND TABLE_NAME = %s;",(name_table,))
        while True:
            next_row = cursor.fetchone()
            if next_row:
                (name_col, type_col) = next_row
                if t==number:
                    nc = name_col
                    t=t+1
                else:
                    t=t+1
            else:
                break
        connect.commit()
        return nc
    else:
        cursor.execute("PRAGMA table_info('{}');".format(name_table))
        while True:
            next_row = cursor.fetchone()
            if next_row:
                name_col = next_row[0]
                t += 1
                if t == number:
                    nc = name_col
                    break
            else:
                break
        connect.commit()
        return nc

def db_read_data_from_first_col(name_col, name_table, k):
    t=0
    ic=0
    if i==1:
        cursor.execute("SELECT "+name_col+" FROM "+name_table)
    else:
        cursor.execute("SELECT "+name_col+" FROM "+name_table)
    while True:
        next_row = cursor.fetchone()
        if next_row:
            if t==k:
                ic = next_row
                t=t+1
            else:
                t=t+1
        else:
            break
    connect.commit()
    return ic

def db_plus_qr_info(id_table, id_client, id_row, time):
    id_qr=count_id("table_user")
    name_file="QR"+str(id_qr)+".png"
    qr.create_qr_code(qr.encryption(id_table, id_client, id_row), name_file)
    data = [id_qr, time, name_file, id_table]
    if i==1:
        cursor.execute("INSERT INTO table_user (id_qr, time, name_file_qr, id_name_table) VALUES(%s,%s,%s,%s);", data)
    else:
        cursor.execute("INSERT INTO sys_human_table VALUES(?,?,?,?);",data)
    connect.commit()
    return "correct"

def db_read_qr_info(file_name, id):
    info_from_qr = qr.dencryption(qr.read_qr_code(file_name))
    table = info_from_qr[0]
    row = info_from_qr[1]
    info_table_name_table(table, id)
    name_table=info_table_name_table(table, id)
    name_col=info_columns_name(info_table_name_table(table, id), 0)
    data_name_col=db_read_data_from_first_col(name_col, name_table, row)
    if i == 1:
        cursor.execute("SELECT * FROM "+name_table+" WHERE "+name_col+" = %s", (data_name_col,))
    else:
        cursor.execute("SELECT * FROM "+name_table+" WHERE "+name_col+" = ?", (data_name_col,))

    next_row = cursor.fetchone()

    return next_row


if __name__ == '__main__':
    #print(qr.create_qr_code(qr.encryption(0, 0, 1), "QR4.png"))
    #print(qr.dencryption(qr.read_qr_code("QR4.png")))
    #db_plus4("table_user", "id_qr", "text", "time", "text", "name_file_qr", "text", "id_name_table", "int")
    #print(qr.create_qr_code(qr.encryption(0, 0, 1)))
    #print(qr.dencryption(qr.read_qr_code("QR4.png")))

    #db_plus_qr_info(0, 0, 1, "20:12")
    print(db_read_qr_info("QR0.png", 0))

