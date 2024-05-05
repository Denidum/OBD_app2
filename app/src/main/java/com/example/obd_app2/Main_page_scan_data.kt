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
import com.chaquo.python.Python
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

        val py = Python.getInstance()
        val module = py.getModule("bd")

        val checkIdTable = module["info_table_name_table"]

        tableLabel.text = checkIdTable?.call(tableId, userId).toString()
        //кількість колонок
        val checkSizeTable = module["count_column"]

        val columnCount: Int = Integer.parseInt(checkSizeTable?.call(tableLabel.text).toString())

        //назви колонок
        val columnNames = arrayListOf<String>()

        val checkNameCol= module["info_columns_name"]

        for(i in 0..<columnCount step 1){
            columnNames.add(checkNameCol?.call(tableLabel.text, i).toString())
        }

        //значення в кожній з колонок

        val checkSelectData = module["db_read_data"]
        val checkSelectFirstData = module["db_read_data_from_first_col"]

        val DataInColumns = mutableListOf<String>()
            for (k in 0..<columnCount step 1) {
                DataInColumns.add(checkSelectData?.call(
                    checkNameCol?.call(tableLabel.text, k).toString(),
                    tableLabel.text,
                    checkNameCol?.call(tableLabel.text, 0).toString(),
                    checkSelectFirstData?.call(
                        checkNameCol?.call(tableLabel.text, 0).toString(),
                        tableLabel.text,
                        rowId
                    ).toString()
                ).toString())
            }

        var dataToDisplay = ArrayList<Data_from_row>()

        for (i in 0..<columnCount step 1){
            dataToDisplay.add(Data_from_row(columnNames[i], DataInColumns[i]))
        }

        val adapter = DataListAdapter(v.context, dataToDisplay)
        dataList.adapter = adapter

        val backButton = v.findViewById<Button>(R.id.main_page_scan_data_back_button)
        backButton.setOnClickListener {
            myInterface!!.passDataToMainToReplaceFrags(Main_page_scan(), -1, 0,-1)
        }

        return v
    }


}