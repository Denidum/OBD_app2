package com.example.obd_app2

import Adapters.DataColumnsItemsAdapter
import Adapters.TableColumnsItemsAdapter
import Table_or_data_classes.Column_to_add_data
import Table_or_data_classes.Table_to_create
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obd_app2.interfaces.EditTextChangeListener
import com.example.obd_app2.interfaces.Main_to_secondary_frags

class Main_page_database_add_data : Fragment(), EditTextChangeListener {
    private var userId: Int? = null
    private var tableId: Int? = null
    private val columnDataList = arrayListOf<String>()
    private var myInterface: Main_to_secondary_frags? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main_page_database_add_data, container, false)
        val data = arguments

        userId = data?.getInt("id")
        tableId = data?.getInt("tableId")

        myInterface = activity as Main_to_secondary_frags

        val itemList: RecyclerView = v.findViewById(R.id.main_database_add_data_columns_list)
        itemList.setItemViewCacheSize(6)

        //Todo:: за допомогою userId та tableId у БД знаходиш дані про колонки у таблиці, дані з якої будемо діставати (кількість колонок + їхні назви + типи даних)
        //кількість колонок
        //значення 4 cтоїть для тесту відображення у інтерфейсі (маєш замінити бекендом)
        val columnCount: Int = 4

        for(i in 0..<columnCount step 1){
            columnDataList.add("")
        }
        //їхні назви (теж бекенд), можеш спробувати через for зробити добавлення
        val columnNames = arrayListOf<String>()
        //їхні типи
        val columnDataType = arrayListOf<String>()

        //хардкод для тесту відображення у інтерфейсі (треба видалити)
        columnNames.add("Col1")
        columnNames.add("Col2")
        columnNames.add("Col3")
        columnNames.add("Col4")

        columnDataType.add("Int")
        columnDataType.add("TEXT")
        columnDataType.add("Int")
        columnDataType.add("TEXT")
        //

        val columnDataToDisplay = arrayListOf<Column_to_add_data>()
        for(i in 0..<columnCount step 1){
            columnDataToDisplay.add(Column_to_add_data(i+1, columnNames[i], columnDataType[i]))
        }

        itemList.layoutManager = LinearLayoutManager(v.context)
        val adapter = DataColumnsItemsAdapter(columnDataToDisplay, v.context, this)
        itemList.adapter = adapter

        val backBtn: Button = v.findViewById(R.id.main_database_add_data_back_button)
        backBtn.setOnClickListener {
            myInterface!!.passDataToMainToReplaceFrags(Main_page_database_add_delete_data(),-1)
        }

        val addBtn: Button = v.findViewById(R.id.main_database_add_data_add_button)
        addBtn.setOnClickListener {
            var isInsertDataSucc = 1
            val insertDataList = arrayListOf<String>() //сюди будуть зберігатися дані, що треба буде вписати в таблицю
            for(i in 0..<columnCount step 1){
                if(checkIfEmpty(columnDataList[i])){
                    isInsertDataSucc = 0
                    Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show()
                    break
                }
                else{
                    insertDataList.add(columnDataList[i])
                }
            }

            if(isInsertDataSucc == 1){
                //Todo: тут має бути бекенд, що добавляє нові дані у таблицю, на основі insertDataList
                Toast.makeText(context, "Data insertion is successful", Toast.LENGTH_SHORT).show()
            }
        }
        return v
    }

    private fun checkIfEmpty(str: String):Boolean{
        return str == ""
    }
    override fun onEditTextChanged(text: String, position: Int) {
        columnDataList.removeAt(position)
        columnDataList.add(position,text)
    }

    override fun onItemSelected(text: String, position: Int) {
        //*_*
    }


}