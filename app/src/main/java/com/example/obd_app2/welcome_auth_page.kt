package com.example.obd_app2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.obd_app2.interfaces.Welcome_page_interface
import kotlinx.coroutines.newFixedThreadPoolContext

class welcome_auth_page : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_welcome_auth_page, container, false)

        val loginEditText: EditText = v.findViewById(R.id.welc_auth_login_textbox)
        val passEditText: EditText = v.findViewById(R.id.welc_auth_pass_textbox)
        val logInButton: Button = v.findViewById(R.id.welc_auth_log_in_button)

        val myInterface: Welcome_page_interface = activity as Welcome_page_interface



        logInButton.setOnClickListener{
            var strLogin = loginEditText.text.toString()
            var strPass = passEditText.text.toString()

            myInterface.TransDataFromLogInToCheck(strLogin, strPass)
        }

        return v
    }

}