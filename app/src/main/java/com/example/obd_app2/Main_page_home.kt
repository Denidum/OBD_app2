package com.example.obd_app2

import Adapters.TableListItemsAdapter
import Table_or_data_classes.Table
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.obd_app2.interfaces.Main_to_secondary_frags
import com.example.obd_app2.interfaces.Welcome_page_interface
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
class Main_page_home : Fragment() {
    var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main_page_home, container, false)
        val myInterface: Main_to_secondary_frags = activity as Main_to_secondary_frags
        val logAddTableButton: Button = v.findViewById(R.id.main_home_page_add_table_button)

        val data = arguments

        val userId = data?.getInt("id")

        val itemList: RecyclerView = v.findViewById(R.id.main_home_page_table_list)
        val items = arrayListOf<Table>()

        val py = Python.getInstance()
        val module = py.getModule("bd")

        val checkSizeTable = module["size_table"]
        val checkTime = module["info_table_time"]
        val checkRow = module["info_table_row"]
        val checkIdTable = module["info_table_id_table"]

        for(i in 0..Integer.parseInt(checkSizeTable?.call(userId).toString())-1){
            items.add(Table(i, checkIdTable?.call(userId, i).toString(), Integer.parseInt(checkRow?.call(userId, i).toString()), checkTime?.call(userId, i).toString()))
        }

        logAddTableButton.setOnClickListener{
            //val check = module["db_plus_table"]
            //val checkInfo = module["db_plus_table_info"]
            //val row = check?.call("Test", "testCol0", "text", "testCol1", "text", "testCol2", "text").toString()
            //checkInfo?.call(userId, checkSizeTable?.call(userId).toString(),Integer.parseInt(row),LocalDateTime.now().toString(), "Test")
        }

        itemList.layoutManager = LinearLayoutManager(activity)
        itemList.adapter = activity?.let { TableListItemsAdapter(items, it) }
        return v
    }

}