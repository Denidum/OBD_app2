package com.example.obd_app2

import Adapters.DataListAdapter
import Adapters.DataListDeleteAdapter
import Table_or_data_classes.Data_from_row
import Table_or_data_classes.Data_list_row
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.obd_app2.interfaces.Main_to_secondary_frags

class Main_page_scan_data : Fragment() {
    private var myInterface: Main_to_secondary_frags? = null
    private var userId: Int? = null
    private var tableId: Int? = null
    private var rowId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main_page_scan_data, container, false)
        val data = arguments
        userId = data?.getInt("id")
        tableId = data?.getInt("tableId")
        rowId = data?.getInt("rowId")

        myInterface = activity as Main_to_secondary_frags
        val dataList: ListView = v.findViewById(R.id.main_page_scan_data_list)

        val tableLabel: TextView = v.findViewById(R.id.main_page_scan_data_table_label)
        //Todo: замість "Table Name" має бути ім'я таблиці, на яку посилається tableId
        tableLabel.text = "Table Name"
        //Todo: знайти кількість колонок у таблиці з tableId, їхні назви та дані з кожної колонки у рядку з rowId
        //кількість колонок
        val columnCount: Int = 3
        //значення в кожній з колонок
        val columnData = arrayListOf<String>("data1", "data2", "data3")
        //назви колонок
        val columnNames = arrayListOf<String>("col1", "col2", "col3")
        var dataToDisplay = ArrayList<Data_from_row>()

        for (i in 0..<columnCount step 1){
            dataToDisplay.add(Data_from_row(/*назва колонки*/columnNames[i], /*дані у цій колонці*/columnData[i]))
        }
        //кінець тудушки

        val adapter = DataListAdapter(v.context, dataToDisplay)
        dataList.adapter = adapter

        val backButton = v.findViewById<Button>(R.id.main_page_scan_data_back_button)
        backButton.setOnClickListener {
            myInterface!!.passDataToMainToReplaceFrags(Main_page_scan(), -1, 0,-1)
        }

        return v
    }


}