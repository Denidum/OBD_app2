package com.example.obd_app2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.obd_app2.interfaces.Welcome_page_interface

class welcome_reg_page : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_welcome_reg_page, container, false)

        val loginEditText: EditText = v.findViewById(R.id.welc_reg_login_textbox)
        val passEditText: EditText = v.findViewById(R.id.welc_reg_pass_textbox)
        val emailEditText: EditText  = v.findViewById(R.id.welc_reg_email_textbox)
        val logAddButton: Button = v.findViewById(R.id.welc_reg_log_in_button)

        val myInterface: Welcome_page_interface = activity as Welcome_page_interface

        logAddButton.setOnClickListener{
            var strLogin = loginEditText.text.toString()
            var strPass = passEditText.text.toString()
            var strEmail = emailEditText.text.toString()

            myInterface.TransDataFromLogInToAddPerson(strLogin, strPass, strEmail)
        }
        return v
    }

}