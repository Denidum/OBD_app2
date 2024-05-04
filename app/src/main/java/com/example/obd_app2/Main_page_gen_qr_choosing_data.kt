package com.example.obd_app2

import Adapters.DataListDeleteAdapter
import Table_or_data_classes.Data_list_row
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


class Main_page_gen_qr_choosing_data : Fragment(), ChooseTableOrDataToDelete{//мені було впадлу робити новий інтерфейс
    private var myInterface: Main_to_secondary_frags? = null
    private var columnData = arrayListOf<Data_list_row>()
    private var userId: Int? = null
    private var tableId: Int? = null
    private var selectedRowId: Int? = -1
    private var adapter: DataListDeleteAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_main_page_gen_qr_choosing_data, container, false)

        val data = arguments
        userId = data?.getInt("id")
        tableId = data?.getInt("tableId")

        myInterface = activity as Main_to_secondary_frags

        val dataList: ListView = v.findViewById(R.id.gen_qr_page_choosing_data_list)

        //Todo:: все так само як в Main_page_database_add_delete_data, тобто маєш достати рядки даних з таблиці з БД, яку користувач вибрав до цього, і вивести ці дані у таблицю
        //Тут я просто копіпастю, бо мені на той час було так впадлу все переписувати на щось інше
        //По факту можеш копіпастнути код з того файлу теж(у механізмі відображення я нічого не змінював)
        //ПОЧАТОК тудушки
        val columnCount: Int = 4
        val columnNames = arrayListOf<String>()

        //хардкод для тесту відображення у інтерфейсі (треба видалити)
        columnNames.add("1")
        columnNames.add("2")
        columnNames.add("3")
        columnNames.add("4")

        //cписок, що буде зберігати інформацію про структуру даних у таблиці
        val columnDataToDisplay = arrayListOf<Data_list_row>(Data_list_row(-1,columnCount, columnNames, true, false))

        val dataRowCount = 15
        for(i in 0..<dataRowCount step 1){
            columnData.add(Data_list_row(i+1, columnCount, arrayListOf("test_data1","test_data2","test_data3","test_data4"), false, false))
        }

        //КІНЕЦЬ тудушки

        columnDataToDisplay.addAll(columnData)

        adapter = DataListDeleteAdapter(v.context, columnDataToDisplay, this)
        dataList.adapter = adapter

        val genQRBtn = v.findViewById<Button>(R.id.main_gen_qr_page_choosing_data_gen_qr_button)
        genQRBtn.setOnClickListener {
            if (selectedRowId == -1){
                Toast.makeText(context, "Choose one data row to generate QR code", Toast.LENGTH_SHORT).show()
            }
            else{
                genQr()
            }
        }

        val backBtn = v.findViewById<Button>(R.id.main_gen_qr_page_choosing_data_back_button)
        backBtn.setOnClickListener {
            myInterface!!.passDataToMainToReplaceFrags(Main_page_qr(), -1)
        }

        return v
    }

    private fun genQr() {
        //Todo: тут реалізовуєш бекенд для створення QR коду на основі userId, tableId та selectedRowId(зберігає id рядка у таблиці з БД)
    }

    override fun buttonChecked(index: Int) {
        //тут я реалізовую обмеження лише вибору у розмірі одного рядка
        for(i in 0..<index-1 step 1){
            columnData[i].isChecked = false
        }
        for(i in index..<columnData.size step 1){
            columnData[i].isChecked = false
        }
        columnData[index-1].isChecked = true
        selectedRowId = columnData[index-1].rowId
        Log.d("myLog","${selectedRowId}")
        //костиль, що перезавантажує список, щоб візуально показати, що можна вибрати лише один елемент
        adapter!!.notifyDataSetChanged()
    }

    override fun buttonUnchecked(index: Int) {
        columnData[index-1].isChecked = false
        selectedRowId = -1
        Log.d("myLog","${selectedRowId}")
    }
}