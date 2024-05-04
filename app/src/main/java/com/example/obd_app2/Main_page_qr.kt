package com.example.obd_app2

import Table_or_data_classes.Table
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.obd_app2.interfaces.Main_to_secondary_frags

class Main_page_qr : Fragment() {
    private var userId: Int? = null
    private var selectedTable: Table? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main_page_generate_qr, container, false)
        val data = arguments
        //достається id користувача
        userId = data?.getInt("id")

        val items = arrayListOf<Table>()
        //Todo: Робиш бекенд такий самий, як у Main_page_database, тобто добавляєш таблиці користувача у список items
        items.add(Table(1, "Test Table1", 4, "00:00 1th January 2024"))
        items.add(Table(2, "Test Table2", 4, "00:00 1th January 2024"))
        val tableName = arrayListOf<String>()
        //перевірка чи користувач має таблиці
        if(items.size > 0) {
            for (i in 0..<items.size step 1) {
                tableName.add(items[i].name)
            }
        }
        else{
            tableName.add("There is no table")
            items.add(Table(0, "null", 0, "null"))
        }
        selectedTable = items[0]

        val tableSpr = v.findViewById<Spinner>(R.id.main_gen_qr_spinner)

        val adap = ArrayAdapter(v.context, R.layout.style_spinner_data_type_selected_item, tableName)
        adap.setDropDownViewResource(R.layout.style_spinner_data_type_item)
        tableSpr.apply {
            adapter = adap
            setSelection(0, false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedTable = items[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        }

        val myInterface: Main_to_secondary_frags = activity as Main_to_secondary_frags

        val chooseBtn: Button = v.findViewById(R.id.main_gen_qr_page_choose_data_button)
        chooseBtn.setOnClickListener {
            if(selectedTable!!.id != 0){
                myInterface.passDataToMainToReplaceFrags(Main_page_gen_qr_choosing_data(), 0, selectedTable!!.id)
            }
            else{
                Toast.makeText(context, "There is no table to work with", Toast.LENGTH_SHORT).show()
            }
        }

        val listBtn: Button = v.findViewById(R.id.main_gen_qr_page_list_created_qr_button)
        listBtn.setOnClickListener {
            myInterface.passDataToMainToReplaceFrags(Main_page_list_of_created_qrs(), 1)
        }
        return v
    }

}