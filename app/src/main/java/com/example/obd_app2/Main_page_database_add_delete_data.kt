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
import com.chaquo.python.Python
import com.example.obd_app2.interfaces.ChooseTableOrDataToDelete
import com.example.obd_app2.interfaces.Main_to_secondary_frags


class Main_page_database_add_delete_data : Fragment(), ChooseTableOrDataToDelete {
    private var myInterface: Main_to_secondary_frags? = null
    private var columnData = arrayListOf<Data_list_row>() // змінна, що буде зберігати об'єкти класу Data_list_row, які описують рядок даних з таблиці
    private var userId: Int? = null
    private var tableId: Int? = null
    private var tableName = ""
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

        val py = Python.getInstance()
        val module = py.getModule("bd")

        val checkIdTable = module["info_table_name_table"]

        userId = data?.getInt("id")
        tableId = data?.getInt("tableId")
        tableName = checkIdTable?.call(tableId, userId).toString()

        myInterface = activity as Main_to_secondary_frags

        val dataList: ListView = v.findViewById(R.id.main_database_add_delete_data_list)

        val checkSizeTable = module["count_column"]

        val columnCount: Int = Integer.parseInt(checkSizeTable?.call(tableName).toString())

        val columnNames = arrayListOf<String>()

        val checkNameCol= module["info_columns_name"]

        for(i in 0..<columnCount step 1){
            columnNames.add(checkNameCol?.call(tableName, i).toString())
        }

        val columnDataToDisplay = arrayListOf<Data_list_row>(Data_list_row(-1,columnCount, columnNames, true, false))

        val checkRow = module["size_table_row"]
        val checkSelectData = module["db_read_data"]
        val dataRowCount = Integer.parseInt(checkRow?.call(tableName, checkNameCol?.call(tableName, 0).toString()).toString())
        for(i in 0..<dataRowCount step 1){
            //Todo: замінити цей хардкод на бекенд, що буде витягувати порядково дані з таблиці та добавляти їх у columnData у форматі класу Data_list_row
                columnData.add(Data_list_row(i+1, columnCount, arrayListOf("data","data1","data2"), false, false))
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
        val myInterface: Main_to_secondary_frags = activity as Main_to_secondary_frags
        val addDataBtn: Button = v.findViewById(R.id.main_database_add_delete_add_button)
        addDataBtn.setOnClickListener {
            myInterface.passDataToMainToReplaceFrags(Main_page_database_add_data(),1)
        }
        val backBtn = v.findViewById<Button>(R.id.main_database_add_delete_back_button)

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