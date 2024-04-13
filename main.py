import db ## бібліотека для бази даних


def add_db_plus4():
    db.db_plus4("sys_human_list", "id", "integer", "login", "text","password", "text","email", "text")
def add_baza_human(): #додати людину до списку людей
    count = db.count_id("sys_human_list")
    data_name = [count, "Danya", "1236", "example1@gmail.com"] #Тестові дані
    #db.db_plus3("human_list", "id","integer","login","text","password","text")
    db.insert_data_db("sys_human_list", data_name, 3)

def check_human():
    name_test="Tanya"
    name_test1="Danya"
    pasww_test="1234"
    pasww_test1="1236"
    print(db.check_info_human(name_test1, pasww_test1))

if __name__ == '__main__':
    #add_db_plus4()
    #add_baza_human()
    check_human()


