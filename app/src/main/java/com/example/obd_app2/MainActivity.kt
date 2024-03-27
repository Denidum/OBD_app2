package com.example.obd_app2

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private var fragmentNumber: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        replaceFragment(welcome_main_page())
        findViewById<LinearLayout>(R.id.welcome_page_nav_back_button).visibility = View.INVISIBLE
        findViewById<LinearLayout>(R.id.welcome_page_nav_next_button).setOnClickListener{
            if(fragmentNumber == 0){
                fragmentNumber = 1
                changeButtonVisibility(findViewById(R.id.welcome_page_nav_back_button), View.VISIBLE)
                replaceFragment(welcome_auth_page())
                findViewById<TextView>(R.id.welcome_page_nav_next_button_label).text = getString(R.string.welcome_page_nav_str_sign_in)
            }
            else if(fragmentNumber == 1){
                fragmentNumber = 2
                replaceFragment(welcome_reg_page())
                changeButtonVisibility(findViewById<LinearLayout>(R.id.welcome_page_nav_next_button), View.INVISIBLE)
            }
        }
        findViewById<LinearLayout>(R.id.welcome_page_nav_back_button).setOnClickListener{
            if(fragmentNumber == 1){
                fragmentNumber = 0
                changeButtonVisibility(findViewById(R.id.welcome_page_nav_back_button), View.INVISIBLE)
                replaceFragment(welcome_main_page())
                findViewById<TextView>(R.id.welcome_page_nav_next_button_label).text = getString(R.string.welcome_page_nav_str_next)
            }
            else if(fragmentNumber == 2){
                fragmentNumber = 1
                replaceFragment(welcome_auth_page())
                changeButtonVisibility(findViewById(R.id.welcome_page_nav_next_button), View.VISIBLE)
            }
        }
    }

    private fun changeButtonVisibility(b: LinearLayout, vis: Int){
        b.visibility = vis
    }
    private fun replaceFragment(frag: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTrans = fragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.welc_page_frag_view, frag)
        fragmentTrans.commit()
    }
}