import sqlite3
import os.path
import mysql.connector

try:
    connect = mysql.connector.connect(
        host="192.168.1.12",
        user="client",
        password="KillJoy98",
        database="qdms"
    )
    i=1
except:
    package_dir = os.path.abspath(os.path.dirname(__file__))
    db_dir = os.path.join(package_dir, 'list.db')

    connect = sqlite3.connect(db_dir)
    i=0

cursor = connect.cursor()

#Type
#integer-number
#text-string

#Спеціальна функція перевірки для таблиці авторизації(human_list)

def check_info_human(name_human='', passw_human=''):
    if i==1:
        cursor.execute("SELECT * FROM sys_human_list where login = %s and password = %s", (name_human, passw_human))
    else:
        cursor.execute("SELECT * FROM sys_human_list where login = ? and password = ?", (name_human, passw_human))
    next_row = cursor.fetchone()
    connect.commit()
    if next_row:
        (id, login, passw, em) = next_row
        if name_human == login and passw_human == passw:
            return "Correct"
        if name_human == '' and passw_human == '':
            return "Enter something"
        if name_human == '':
            return "Enter login"
        if passw_human == '':
            return "Enter password"
        else:
            return "Wrong"
def count_id(name_table):
    cursor.execute("SELECT * FROM "+name_table)
    result = cursor.fetchall()
    connect.commit()
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
                connect.commit()
                return "Login"
            if passw== pasww:
                connect.commit()
                return "Pasww"
            if em == email:
                connect.commit()
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

def check_id_user(name_user):
    if i == 1:
        cursor.execute("SELECT * FROM sys_human_list WHERE login = %s", (name_user,))
    else:
        cursor.execute("SELECT * FROM sys_human_list WHERE login = ?", (name_user,))
    next_row = cursor.fetchone()
    connect.commit()
    if next_row is not None:
        (id, login, passw, em) = next_row 
        return id
    else:
        return None


def db_plus_table(name_table, name1, type1, name2, type2, name3, type3):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""("""
                   + name1 +""" """+ type1 + """, """
                   + name2 +""" """+ type2 + """, """
                   + name3 +""" """+ type3 + """) """)
    connect.commit()
    return "3"

def db_plus_table_info(id_user, id_table, row, time,name):
    data = [id_user, id_table, row, time, name]
    if i==1:
        cursor.execute("INSERT INTO sys_human_table (id_user, id_name_table, number_row, time_created, name_table) VALUES(%s,%s,%s,%s,%s);", data)
    else:
        cursor.execute("INSERT INTO sys_human_table VALUES(?,?,?,?,?);",data)
    connect.commit()
    return "correct"


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

def info_table_row(id_table, id):
    if i==1:
        cursor.execute("SELECT * FROM sys_human_table where id_name_table = %s and id_user = %s",(id_table,id))
    else:
        cursor.execute("SELECT * FROM sys_human_table WHERE id_name_table = ? AND id_user = ?",
                       (id_table, id))
    next_row = cursor.fetchone()
    if next_row is not None:
        (id, id_table, row, time, name) = next_row
        connect.commit()
        return row
    else:
        return None

def info_table_time(id_table, id):
    if i==1:
        cursor.execute("SELECT * FROM sys_human_table where id_name_table = %s and id_user = %s",(id_table,id))
    else:
        cursor.execute("SELECT * FROM sys_human_table WHERE id_name_table = ? AND id_user = ?",
                       (id_table, id))
    next_row = cursor.fetchone()
    if next_row is not None:
        (id, id_table, row, time, name) = next_row
        connect.commit()
        return time
    else:
        return None

def size_table(id):
    if i==1:
        cursor.execute("SELECT COUNT(id_name_table) FROM sys_human_table Where id_user= %s",(id,))
    else:
        cursor.execute("SELECT COUNT(id_name_table) FROM sys_human_table Where id_user='{}'".format(id, ))
    count_row = cursor.fetchone()
    if count_row is not None:
        count = int(count_row[0])
    else:
        count = 0
    connect.commit()
    return count