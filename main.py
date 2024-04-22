
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
    cursor.execute(""" CREATE TABLE IF NOT EXISTS sys_human_list(id integer, login text, password text, email text) """)
    connect.commit()

#Видалити рядок
def delete_mysql():
    name=["Danyi", ]
    cursor.execute("DELETE FROM sys_human_list WHERE login = %s", name)
    connect.commit()

if __name__ == '__main__':
    #add_db_plus4()
    #add_baza_human()
    #add_my_sgl()
    print(IPAddr)
    #print(add_baza_human("Rick", "1111", "ex@gmail.com"))
    delete_mysql()


