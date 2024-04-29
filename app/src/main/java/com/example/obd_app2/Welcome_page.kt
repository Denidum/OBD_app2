package com.example.obd_app2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.obd_app2.interfaces.Welcome_page_interface

class Welcome_page : AppCompatActivity(), Welcome_page_interface {
    private var fragmentNumber: Int = 0
    var pref: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.welc_page_main_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pref = getSharedPreferences("Table", MODE_PRIVATE)
        var savedUserId = pref?.getInt("CurrIdUser", -1)!!
        val resetUserId = intent.getIntExtra("id_reset", 0)
        if(resetUserId == 1){
             saveUserId(0)
             savedUserId = pref?.getInt("CurrIdUser", 0)!!
        }
        if(savedUserId !=-1){
            intentToMain(savedUserId)
        }

        val imagelogo: ImageView = findViewById(R.id.welcome_page_appBarTop_logo)
        imagelogo.visibility = View.INVISIBLE
        replaceFragment(welcome_main_page(), 0)
        findViewById<LinearLayout>(R.id.welcome_page_nav_back_button).visibility = View.INVISIBLE
        findViewById<LinearLayout>(R.id.welcome_page_nav_next_button).setOnClickListener{
            if(fragmentNumber == 0){
                imagelogo.visibility = View.VISIBLE
                val custAnim = AnimationUtils.loadAnimation(this, R.anim.top_logo_fade_in)
                imagelogo.startAnimation(custAnim)
                fragmentNumber = 1
                changeButtonVisibility(findViewById(R.id.welcome_page_nav_back_button), View.VISIBLE)
                replaceFragment(welcome_auth_page(), 1)
                findViewById<TextView>(R.id.welcome_page_nav_next_button_label).text = getString(R.string.welcome_page_nav_str_sign_in)
            }
            else if(fragmentNumber == 1){
                fragmentNumber = 2
                replaceFragment(welcome_reg_page(), 1)
                changeButtonVisibility(findViewById(R.id.welcome_page_nav_next_button), View.INVISIBLE)
            }
        }
        findViewById<LinearLayout>(R.id.welcome_page_nav_back_button).setOnClickListener{
            if(fragmentNumber == 1){
                fragmentNumber = 0
                val custAnim = AnimationUtils.loadAnimation(this, R.anim.top_logo_fade_out)
                imagelogo.startAnimation(custAnim)
                changeButtonVisibility(findViewById(R.id.welcome_page_nav_back_button), View.INVISIBLE)
                changeButtonVisibility(findViewById(R.id.appBarTop_linearlayout), View.VISIBLE)
                replaceFragment(welcome_main_page(), -1)
                findViewById<TextView>(R.id.welcome_page_nav_next_button_label).text = getString(R.string.welcome_page_nav_str_next)
            }
            else if(fragmentNumber == 2){
                fragmentNumber = 1
                replaceFragment(welcome_auth_page(), -1)
                changeButtonVisibility(findViewById(R.id.welcome_page_nav_next_button), View.VISIBLE)
            }
        }
    }

    private fun changeButtonVisibility(b: LinearLayout, vis: Int){
        b.visibility = vis
    }
    private fun replaceFragment(frag: Fragment, a: Int){
        val fragmentManager = supportFragmentManager
        val fragmentTrans = fragmentManager.beginTransaction()
        if(a == 1){
            fragmentTrans.setCustomAnimations(R.anim.enter_from_right_to_left, R.anim.exit_from_right_to_left)
        }
        else if(a == -1){
            fragmentTrans.setCustomAnimations(R.anim.enter_from_left_to_right, R.anim.exit_from_left_to_right)
        }
        fragmentTrans.replace(R.id.welc_page_frag_view, frag)
        fragmentTrans.commit()
    }

    override fun TransDataFromLogInToCheck(strLogin: String, strPass: String) {

        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }

        val py = Python.getInstance()
        val module = py.getModule("bd")

        var strLoginTrim = strLogin.trim()
        var strPassTrim = strPass.trim()

        val check = module["check_info_human"]
        val a = check?.call(strLoginTrim, strPassTrim).toString()

        if(strLoginTrim == "" || strPassTrim == ""){
            Toast.makeText(this, "Some of fields aren't filled", Toast.LENGTH_SHORT).show()
        }
        else if(a=="Correct"){
            Toast.makeText(this, "You successfully entered your account", Toast.LENGTH_SHORT).show()
            val checkId = module["check_id_user"]
            val userId = Integer.parseInt(checkId?.call(strLoginTrim).toString())

            saveUserId(userId)
            intentToMain(userId)
        }
        else{
            Toast.makeText(this, "Wrong login or password", Toast.LENGTH_SHORT).show()
        }

    }

    override fun TransDataFromLogInToAddPerson(strLogin: String, strPass: String, strEmail: String) {
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }

        val py = Python.getInstance()
        val module = py.getModule("bd")

        var strLoginTrim = strLogin.trim()
        var strPassTrim = strPass.trim()
        var strEmailTrim = strEmail.trim()

        val check = module["add_baza_human"]
        val a = check?.call(strLoginTrim, strPassTrim, strEmailTrim).toString()

        if(strLoginTrim == "" || strPassTrim == "" || strEmailTrim == ""){
            Toast.makeText(this, "Some of fields aren't filled", Toast.LENGTH_SHORT).show()
        }
        else if(a=="Login"){
            Toast.makeText(this, "Login is already in use", Toast.LENGTH_SHORT).show()
        }
        else if(a=="Pasww"){
            Toast.makeText(this, "Password is already in use", Toast.LENGTH_SHORT).show()
        }
        else if(a=="Email"){
            Toast.makeText(this, "Email is already in use", Toast.LENGTH_SHORT).show()
        }
        else if(a=="Correct"){
            Toast.makeText(this, "You successfully add your account", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }
    private fun saveUserId(id: Int){
        val editor = pref?.edit()
        editor?.putInt("CurrIdUser", id)
        editor?.apply()

    }
    private fun intentToMain(userId: Int){
        val intentVal = Intent(this, Main_page::class.java).apply(){
            putExtra("id", userId)
        }
        startActivity(intentVal)
        finish()
    }

}



