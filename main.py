import db ## бібліотека для бази даних

def add_baza_human(): #додати людину до списку людей
    data_name = [1, "Tanya", "1234"] #Тестові дані
    #db.db_plus3("human_list", "id","integer","login","text","password","text")
    db.insert_data_db("human_list", data_name, 2)

def check_human():
    name_test="Tanya"
    name_test1="Danya"
    pasww_test="1234"
    pasww_test1="0123"
    print(db.check_info_human(name_test, pasww_test1))

if __name__ == '__main__':
    check_human()


