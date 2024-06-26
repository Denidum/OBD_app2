package com.example.obd_app2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.obd_app2.interfaces.Main_user_to_main_act
import com.example.obd_app2.interfaces.Welcome_page_interface

class Main_page_user : Fragment() {
    private var userId: Int? = null
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
        val changBtn = v.findViewById<Button>(R.id.main_user_page_change_pass_button)
        changBtn.setOnClickListener {
            Toast.makeText(v.context, "Will be added in new versions", Toast.LENGTH_SHORT).show()
        }

        val data = arguments
        //достається id користувача
        userId = data?.getInt("id")

        val userLogin: TextView = v.findViewById(R.id.main_user_page_login_textbox)
        val userEmail: TextView = v.findViewById(R.id.main_user_page_email_textbox)

        //Todo: знаходиш логін й пароль за userId і вставляєш у відповідну змінну
        val strLogin = "UserName"
        val strEmail = "UserEmail"



        val myInterface: Main_user_to_main_act = activity as Main_user_to_main_act
        logOutBtn.setOnClickListener{
            myInterface.intentDataFromMainToWelc(0)
        }

        return v
    }

}