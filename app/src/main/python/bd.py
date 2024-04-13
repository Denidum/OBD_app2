import sqlite3
import os.path

package_dir = os.path.abspath(os.path.dirname(__file__))
db_dir = os.path.join(package_dir, 'list.db')

connect = sqlite3.connect(db_dir)
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
            return "Wrong"

def add_db_plus4(fon=" ", gd=" "):
    cursor.execute(""" CREATE TABLE IF NOT EXISTS sys_human_list(id integer, login text, password text, email text) """)
    data_name = [0, "Tanya", "1234", "example1@gmail.com"]  # Тестові дані
    cursor.execute("INSERT INTO sys_human_list VALUES(?,?,?,?);", data_name)
    connect.commit()
    return "Correct"
