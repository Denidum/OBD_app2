package com.example.obd_app2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.obd_app2.interfaces.Main_to_secondary_frags

class Main_page_database_data_redacting : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(
            R.layout.fragment_main_page_database_data_redacting,
            container,
            false
        )
        return v
    }

}