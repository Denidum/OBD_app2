package com.example.obd_app2

import Adapters.TableListItemsAdapter
import Table_or_data_classes.Table
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obd_app2.interfaces.Main_to_secondary_frags

class Main_page_home : Fragment() {
    private var userId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main_page_home, container, false)

        val addTableBtn: Button = v.findViewById(R.id.main_home_page_add_table_button)

        val data = arguments
        userId = data?.getInt("id")

        val itemList: RecyclerView = v.findViewById(R.id.main_home_page_table_list)
        val items = arrayListOf<Table>()
        //Todo: вивести список завдяки функції, що шукає таблиці за id користувача
        items.add(Table(1, "Test Table1", 4, "00:00 1th January 2024"))
        items.add(Table(2, "Test Table2", 4, "00:00 1th January 2024"))


        itemList.layoutManager = LinearLayoutManager(activity)
        itemList.adapter = activity?.let { TableListItemsAdapter(items, it) }

        val myInterface: Main_to_secondary_frags = activity as Main_to_secondary_frags
        addTableBtn.setOnClickListener {
            myInterface.passDataToMainToReplaceFrags(Main_page_home_add_table(), 1)
        }
        return v
    }

}