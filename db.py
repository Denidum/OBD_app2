import sqlite3
import os.path

package_dir = os.path.abspath(os.path.dirname(__file__))
db_dir = os.path.join(package_dir, 'list.db')

connect = sqlite3.connect(db_dir)
cursor = connect.cursor()

#Type
#integer-number
#text-string

#Створити aдаптовану таблицю з 1 колонкою
def db_plus1(name_table ,name1, type1):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""(""" + name1 + type1 + """) """)
    connect.commit()

#Створити aдаптовану таблицю з 2 колонкою
def db_plus2(name_table, name1, type1, name2, type2):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""("""
                   + name1 +""" """+ type1 + """, """
                   + name2 +""" """+ type2 + """) """)
    connect.commit()

#Створити адаптовану таблицю для 3 колонок
def db_plus3(name_table, name1, type1, name2, type2, name3, type3):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""("""
                   + name1 +""" """+ type1 + """, """
                   + name2 +""" """+ type2 + """, """
                   + name3 +""" """+ type3 + """) """)
    connect.commit()

#Створити адаптовану таблицю для 4 колонок
def db_plus4(name_table, name1, type1, name2, type2, name3, type3, name4, type4):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""("""
                   + name1 +""" """+ type1 + """, """
                   + name2 +""" """+ type2 + """, """
                   + name3 +""" """+ type3 + """, """
                   + name4 +""" """+ type4 + """) """)
    connect.commit()

#Створити адаптовану таблицю для 5 колонок
def db_plus5(name_table, name1, type1, name2, type2, name3, type3, name4, type4, name5, type5):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""("""
                   + name1 +""" """+ type1 + """, """
                   + name2 +""" """+ type2 + """, """
                   + name3 +""" """+ type3 + """, """
                   + name4 +""" """+ type4 + """, """
                   + name5 +""" """+ type5 + """) """)
    connect.commit()

#Створити адаптовану таблицю для 6 колонок
def db_plus6(name_table, name1, type1, name2, type2, name3, type3, name4, type4, name5, type5, name6, type6):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""("""
                   + name1 +""" """+ type1 + """, """
                   + name2 +""" """+ type2 + """, """
                   + name3 +""" """+ type3 + """, """
                   + name4 +""" """+ type4 + """, """
                   + name5 +""" """+ type5 + """, """
                   + name6 +""" """+ type6 + """) """)
    connect.commit()

#Створити адаптовану таблицю для 7 колонок
def db_plus6(name_table, name1, type1, name2, type2, name3, type3, name4, type4, name5, type5, name6, type6, name7, type7):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS """+name_table+"""("""
                   + name1 + type1 + """, """
                   + name2 + type2 + """, """
                   + name3 + type3 + """, """
                   + name4 + type4 + """, """
                   + name5 + type5 + """, """
                   + name6 + type6 + """, """
                   + name7 + type7 + """) """)
    connect.commit()

#Видалити таблицю
def db_delete(name_table):
    cursor.execute("DROP TABLE "+name_table)
    connect.commit()

#Занести дані у таблицю
def insert_data_db(name_table, data_column, size_data_column, id_row=0):
    if size_data_column==0:
        cursor.execute("INSERT INTO "+name_table+" VALUES(?);", data_column)
    elif size_data_column==1:
        cursor.execute("INSERT INTO "+name_table+" VALUES(?,?);", data_column)
    elif size_data_column==2:
        cursor.execute("INSERT INTO "+name_table+" VALUES(?,?,?);", data_column)
    elif size_data_column==3:
        cursor.execute("INSERT INTO " + name_table + " VALUES(?,?,?,?);", data_column)
    elif size_data_column==4:
        cursor.execute("INSERT INTO "+name_table+" VALUES(?,?,?,?,?);", data_column)
    elif size_data_column==5:
        cursor.execute("INSERT INTO "+name_table+" VALUES(?,?,?,?,?,?);", data_column)
    elif size_data_column==6:
        cursor.execute("INSERT INTO "+name_table+" VALUES(?,?,?,?,?,?,?);", data_column)
    connect.commit()

def count_id(name_table):
    result = cursor.execute("SELECT * FROM "+name_table).fetchall()
    for row in result:
        count = row[0]
    return count + 1

#Винести всі дані з таблиці
def select_all_data_db(name_table):
    cursor.execute("SELECT * FROM "+name_table)
    connect.commit()

#Винести рядок з таблиці
def select_data_db(name_table, id_row, number_column):
    cursor.execute("SELECT * FROM "+name_table)
    while True:
        next_row = cursor.fetchone()
        if next_row:
            dict_data = {}
            for i in range(number_column):
                if id_row == i:
                    dict_data[id_row] = next_row
                    break

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
            return "Wrong"

'''
    dict_data = {}
    for i in range(id_row):
        if i==id_row:
            dict_data[i] = []
'''
'''
    next_row = cursor.fetchone()
    if next_row:
        (a, b, c) = next_row
        return a, b, c
    else:
        return 0;
'''

#Редагувати таблицю
def alter_data_db(id_column, id_row, number_column):
    cursor.execute("SELECT * FROM expenses")
    while True:
        next_row = cursor.fetchone()
        if next_row:
            dict_data = {}
            for i in range(number_column):
                if id_row == i:
                    dict_data[id_row] = next_row
                    break
    insert_data_db()

    return 0