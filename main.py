
import db_mysql
import db

import socket
hostname=socket.gethostname()
IPAddr=socket.gethostbyname(hostname)


import mysql.connector

connect = mysql.connector.connect(
    host=IPAddr,
    user="client",
    password="KillJoy98",
    database="qdms"
)

cursor = connect.cursor()

def add_db_plus4():
    db.db_plus4("sys_human_list", "id", "integer", "login", "text","password", "text","email", "text")
def add_baza_human(): #додати людину до списку людей
    count = db.count_id("sys_human_list")
    data_name = [count, "Danya", "1236", "example1@gmail.com"] #Тестові дані
    #db.db_plus3("human_list", "id","integer","login","text","password","text")
    #db.insert_data_db("sys_human_list", data_name, 3)

def check_human():
    name_test="Tanya"
    name_test1="Danya"
    pasww_test="1234"
    pasww_test1="1236"
    print(db.check_info_human(name_test1, pasww_test1))

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
            data_name = [0, name, pasww, email]
            #cursor.execute("INSERT INTO sys_human_list (id, login, password, email) VALUES(%s,%s,%s,%s);", data_name)
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

def size_table(id):
    cursor.execute("SELECT COUNT(id_name_table) FROM sys_human_table Where id_user=" + str(id))
    count_row = cursor.fetchone()

    if count_row is not None:
        count = int(count_row[0])
    else:
        count = 0

    connect.commit()
    return count

if __name__ == '__main__':
    #add_db_plus4()
    #add_baza_human()
    #add_my_sgl()
    #print(IPAddr)
    #print(add_baza_human("Rick", "1111", "ex@gmail.com"))
    #delete_mysql()
    #add_FK_colums()
    #del_PK_colums()
    #add_PK_colums()
    #add_FK_colums()
    print(size_table(0))