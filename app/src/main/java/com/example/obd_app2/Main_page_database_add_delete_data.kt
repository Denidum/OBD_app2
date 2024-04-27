package com.example.obd_app2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.obd_app2.interfaces.Main_to_secondary_frags
import com.example.obd_app2.interfaces.Main_user_to_main_act


class Main_page_database_add_delete_data : Fragment() {

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

        val backBtn = v.findViewById<Button>(R.id.main_database_add_delete_back_button)
        val myInterface: Main_to_secondary_frags = activity as Main_to_secondary_frags
        backBtn.setOnClickListener {
            myInterface.passDataToMainToReplaceFrags(Main_page_database(),-1)
        }
        return v
    }

}