package com.example.obd_app2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.obd_app2.interfaces.Main_user_to_main_act
import com.example.obd_app2.interfaces.Welcome_page_interface

class Main_page_user : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main_page_user, container, false)
        val logOutBtn = v.findViewById<Button>(R.id.main_user_page_log_out_button)

        val myInterface: Main_user_to_main_act = activity as Main_user_to_main_act
        logOutBtn.setOnClickListener{
            myInterface.intentDataFromMainToWelc(0)
        }
        return v
    }

}