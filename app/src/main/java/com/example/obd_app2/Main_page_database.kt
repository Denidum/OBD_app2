package com.example.obd_app2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.obd_app2.interfaces.Main_to_secondary_frags

class Main_page_database : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main_page_database, container, false)
        val addDelBtn = v.findViewById<Button>(R.id.main_database_page_add_del_data_button)
        val redBtn = v.findViewById<Button>(R.id.main_database_page_redact_table_button)
        val viewBtn = v.findViewById<Button>(R.id.main_database_page_view_table_button)
        val myInterface: Main_to_secondary_frags = activity as Main_to_secondary_frags
        addDelBtn.setOnClickListener {
            myInterface.passDataToMainToReplaceFrags(Main_page_database_add_delete_data(), 1)
        }
        redBtn.setOnClickListener {
            myInterface.passDataToMainToReplaceFrags(Main_page_database_redact_data_choosing(), 1)
        }
        viewBtn.setOnClickListener {
            myInterface.passDataToMainToReplaceFrags(Main_page_database_data_view(), 1)
        }
        return v
    }

}