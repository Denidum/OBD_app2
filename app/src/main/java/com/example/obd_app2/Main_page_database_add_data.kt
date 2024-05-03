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
import com.chaquo.python.Python
import com.example.obd_app2.interfaces.EditTextChangeListener
import com.example.obd_app2.interfaces.Main_to_secondary_frags

class Main_page_database_add_data : Fragment(), EditTextChangeListener {
    private var userId: Int? = null
    private var tableId: Int? = null
    private var tableName = ""
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

        val py = Python.getInstance()
        val module = py.getModule("bd")

        val checkIdTable = module["info_table_name_table"]

        userId = data?.getInt("id")
        tableId = data?.getInt("tableId")
        tableName = checkIdTable?.call(tableId, userId).toString()

        myInterface = activity as Main_to_secondary_frags

        val itemList: RecyclerView = v.findViewById(R.id.main_database_add_data_columns_list)
        itemList.setItemViewCacheSize(6)

        val checkSizeTable = module["count_column"]

        val columnCount: Int = Integer.parseInt(checkSizeTable?.call(tableName).toString())

        for(i in 0..<columnCount step 1){
            columnDataList.add("")
        }

        val columnNames = arrayListOf<String>()
        val columnDataType = arrayListOf<String>()

        val checkNameCol= module["info_columns_name"]
        val checkTypeCol= module["info_columns_type"]

        for(i in 0..<columnCount step 1){
            columnNames.add(checkNameCol?.call(tableName, i).toString())
            columnDataType.add(checkTypeCol?.call(tableName, i).toString())
        }

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
        val checkInsertData = module["db_plus_data"]
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

                for(i in 0..<columnCount step 1){
                    checkInsertData?.call(insertDataList[i], checkNameCol?.call(tableName, i).toString(), tableName).toString()
                }

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