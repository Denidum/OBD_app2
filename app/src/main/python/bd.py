
import mysql.connector

connect = mysql.connector.connect(
    host="192.168.1.12",
    #ost="192.168.0.105",
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

def db_plus_table(name_table, name1, type1, name2, type2, name3, type3):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""("""
                   + name1 +""" """+ type1 + """, """
                   + name2 +""" """+ type2 + """, """
                   + name3 +""" """+ type3 + """) """)
    connect.commit()
    return "3"

def db_plus_table_info(id_user, id_table, row, time,name):
    data = [id_user, id_table, row, time, name]
    cursor.execute("INSERT INTO sys_human_table (id_user, id_name_table, number_row, time_created, name_table) VALUES(%s,%s,%s,%s,%s);", data)
    connect.commit()
    return "correct"


def info_table_name_table(id_table, id):
    cursor.execute("SELECT * FROM sys_human_table where id_name_table = "+ str(id_table) + "and id_user = " + str(id))
    next_row = cursor.fetchone()
    if next_row is not None:
        (id, id_table, row, time, name) = next_row
        connect.commit()
        return name
    else:
        return None

def info_table_row(id_table, id):
    cursor.execute("SELECT * FROM sys_human_table where id_name_table = "+ str(id_table) + "and id_user = " + str(id))
    next_row = cursor.fetchone()
    if next_row is not None:
        (id, id_table, row, time, name) = next_row
        connect.commit()
        return row
    else:
        return None

def info_table_time(id_table, id):
    cursor.execute("SELECT * FROM sys_human_table where id_name_table = "+ str(id_table) + "and id_user = " + str(id))
    next_row = cursor.fetchone()
    if next_row is not None:
        (id, id_table, row, time, name) = next_row
        connect.commit()
        return time
    else:
        return None

def size_table(id):
    cursor.execute("SELECT COUNT(id_name_table) FROM sys_human_table Where id_user=" + str(id))
    count_row = cursor.fetchone()

    if count_row is not None:
        count = int(count_row[0])
    else:
        count = 0

    connect.commit()
    return count