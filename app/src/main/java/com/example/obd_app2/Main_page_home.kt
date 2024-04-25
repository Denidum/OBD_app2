package com.example.obd_app2

import Adapters.TableListItemsAdapter
import Table_or_data_classes.Table
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obd_app2.interfaces.UserId_to_Main_page_frags

class Main_page_home : Fragment() {
    var UserId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main_page_home, container, false)
        val itemList: RecyclerView = v.findViewById(R.id.main_home_page_table_list)
        val myInterface: UserId_to_Main_page_frags = activity as UserId_to_Main_page_frags

        UserId = myInterface.getUserId()

        val items = arrayListOf<Table>()
        //ToDo: замість хардкода реалізувати функцію, що буде вводити список таблиць, що належать користувачу
        items.add(Table(1, "Test Table1", arrayListOf("Id", "Name", "Phone number", "Address"), 4, UserId.toString()))
        items.add(Table(2, "Test Table2", arrayListOf("Id", "Name", "Phone number", "Address"), 4, "00:00 1th January 2024"))
        items.add(Table(3, "Test Table3", arrayListOf("Id", "Name", "Phone number", "Address"), 4, "00:00 1th January 2024"))
        items.add(Table(4, "Test Table4", arrayListOf("Id", "Name", "Phone number", "Address"), 4, "00:00 1th January 2024"))
        items.add(Table(5, "Test Table5", arrayListOf("Id", "Name", "Phone number", "Address"), 4, "00:00 1th January 2024"))

        itemList.layoutManager = LinearLayoutManager(activity)
        itemList.adapter = activity?.let { TableListItemsAdapter(items, it) }
        return v
    }

}