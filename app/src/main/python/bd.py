
import mysql.connector

connect = mysql.connector.connect(
    host="192.168.1.12",
    user="boss",
    password="PomeloGranat5",
    database="qdms",
    port=3306
)

cursor = connect.cursor()

#Type
#integer-number
#text-string

#Спеціальна функція перевірки для таблиці авторизації(human_list)

def check_info_human(name_human='', passw_human=''):
    cursor.execute("SELECT * FROM sys_human_list")
    while True:
        next_row = cursor.fetchone()
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
                pass
        else:
            connect.commit()
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
                return "Login"
            if passw== pasww:
                return "Pasww"
            if em == email:
                return "Email"
            else:
                pass
        else:
            connect.commit()
            count = count_id("sys_human_list")
            data_name = [count, name, pasww, email]
            cursor.execute("INSERT INTO sys_human_list (id, login, password, email) VALUES(%s,%s,%s,%s);", data_name)
            connect.commit()
            return "Correct"

