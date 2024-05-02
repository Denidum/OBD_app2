package com.example.obd_app2

import Adapters.DataListDeleteAdapter
import Table_or_data_classes.Data_list_row
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.obd_app2.interfaces.ChooseTableOrDataToDelete
import com.example.obd_app2.interfaces.Main_to_secondary_frags


class Main_page_database_add_delete_data : Fragment(), ChooseTableOrDataToDelete {
    private var myInterface: Main_to_secondary_frags? = null
    private var columnData = arrayListOf<Data_list_row>() // змінна, що буде зберігати об'єкти класу Data_list_row, які описують рядок даних з таблиці
    private var userId: Int? = null
    private var tableId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(
            R.layout.fragment_main_page_database_add_delete_data,
            container,
            false
        )
        val data = arguments
        //достається id користувача
        userId = data?.getInt("id")
        //достається id таблиці, з якою працюємо
        tableId = data?.getInt("tableId")

        myInterface = activity as Main_to_secondary_frags

        val dataList: ListView = v.findViewById(R.id.main_database_add_delete_data_list)
        //Todo:: за допомогою userId та tableId у БД знаходиш дані про таблицю, дані з якої будемо діставати (кількість колонок + їхні назви)
        //кількість колонок
        //значення 4 cтоїть для тесту відображення у інтерфейсі (маєш заміти бекендом)
        val columnCount: Int = 4
        //їхні назви (теж бекенд), можеш спробувати через for зробити добавлення
        val columnNames = arrayListOf<String>()
        /*
        //ось цикл for
        for(i in 0..<columnCount step 1){
            //кожну ітерацію записуємо лише одну назву, якщо користуємося циклом
            columnNames.add( *тут має бути функція, що повертає назву колонки таблиці* )
        }
         */
        //хардкод для тесту відображення у інтерфейсі (треба видалити)
        columnNames.add("1")
        columnNames.add("2")
        columnNames.add("3")
        columnNames.add("4")

        //cписок, що буде зберігати інформацію про структуру даних у таблиці
        val columnDataToDisplay = arrayListOf<Data_list_row>(Data_list_row(-1,columnCount, columnNames, true, false))

        val dataRowCount = 7 // Todo:: замість 7 маю бути функція, що повертає кількість рядків даних у таблиці, з якою користувач зараз працює
        for(i in 0..<dataRowCount step 1){
            //Todo: замінити цей хардкод на бекенд, що буде витягувати порядково дані з таблиці та добавляти їх у columnData у форматі класу Data_list_row
            columnData.add(Data_list_row(i+1, columnCount, arrayListOf("test_data1","test_data2","test_data3","test_data4"), false, false))
        }

        columnDataToDisplay.addAll(columnData)

        val adapter = DataListDeleteAdapter(v.context, columnDataToDisplay, this)
        dataList.adapter = adapter

        val deleteBtn = v.findViewById<Button>(R.id.main_database_add_delete_delete_button)
        deleteBtn.setOnClickListener {
            val dataListToDelete = arrayListOf<Data_list_row>()
            for(i in 0..<columnData.size step 1){
                //перевірка на елементи, що були вибрані
                if(columnData[i].isChecked){
                    //додавання до списку для видалення
                    dataListToDelete.add(columnData[i])
                }
            }
            if (dataListToDelete.isEmpty()){
                Toast.makeText(context, "Choose any data to delete", Toast.LENGTH_SHORT).show()
            }
            else{
                //діалог для
                confirmDeletionDialog(dataListToDelete)
            }
        }
        val backBtn = v.findViewById<Button>(R.id.main_database_add_delete_back_button)
        val myInterface: Main_to_secondary_frags = activity as Main_to_secondary_frags
        backBtn.setOnClickListener {
            myInterface.passDataToMainToReplaceFrags(Main_page_database(),-1)
        }
        return v
    }

    fun confirmDeletionDialog(rastrelList: ArrayList<Data_list_row>){
        val dialog = AlertDialog.Builder(activity)
        dialog.setTitle("Confirm deletion")
        dialog.setTitle("Do you want to delete chosen data?")
        dialog.setPositiveButton("No") { dialog, which ->

            dialog.dismiss()
        }
        dialog.setNegativeButton("Yes") { dialog, which ->
            deleteTableFromSQL(rastrelList)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun deleteTableFromSQL(rastrelList: ArrayList<Data_list_row>) {
        //Todo: тут реалізовуєш бекенд для видалення даних на основі інфи у елементів з rastrelList
        myInterface!!.passDataToMainToReplaceFrags(Main_page_database_add_delete_data(), 0)
    }

    override fun buttonChecked(index: Int) {
        columnData[index-1].isChecked = true
    }

    override fun buttonUnchecked(index: Int) {
        columnData[index-1].isChecked = false
    }

}