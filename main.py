
import db_mysql
import db
import sqlite3
import os.path
import socket
hostname=socket.gethostname()
IPAddr=socket.gethostbyname(hostname)


import mysql.connector

try:
    connect = mysql.connector.connect(
        #host=IPAddr,
        host="192.168.1.105",
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
    result = cursor.execute("SELECT * FROM " + name_table).fetchall()
    for row in result:
        count = row[0]
    return count + 1

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

def add_PK_colums():
    cursor.execute("ALTER TABLE sys_human_list ADD CONSTRAINT idPK PRIMARY KEY (id)")
    connect.commit()

def del_PK_colums():
    cursor.execute("ALTER TABLE sys_human_table DROP PRIMARY KEY;")
    connect.commit()
def add_FK_colums():
    cursor.execute("ALTER TABLE sys_human_table ADD CONSTRAINT id_userFK FOREIGN KEY (id_user) REFERENCES sys_human_list(id);")
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


if __name__ == '__main__':
    change_data()
    print("Hello world")

