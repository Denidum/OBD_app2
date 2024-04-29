package com.example.obd_app2

import Table_or_data_classes.Table_to_create
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.obd_app2.interfaces.Main_to_secondary_frags
import com.example.obd_app2.interfaces.Main_user_to_main_act
import com.google.android.material.bottomnavigation.BottomNavigationView


class Main_page : AppCompatActivity(), Main_user_to_main_act, Main_to_secondary_frags {
    private var userId: Int = 1
    private var tableIdtoWork: Int = 0
    private var currSelectedItem: Int = 2
    private val fragArray = arrayOf(Main_page_qr(), Main_page_database(), Main_page_home(), Main_page_scan(),Main_page_user())
    private val titlesArray = arrayOf(R.string.main_page_top_tool_bar_str_qr_gen, R.string.main_page_top_tool_bar_str_database, R.string.main_page_top_tool_bar_str_main, R.string.main_page_top_tool_bar_str_qr_scan, R.string.main_page_top_tool_bar_str_profile)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_page_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bnv: BottomNavigationView = findViewById(R.id.main_page_nav_menu)
        bnv.setOnApplyWindowInsetsListener(null)
        bnv.itemActiveIndicatorColor = getColorStateList(R.color.gray_on_gray)
        bnv.setPadding(0,0,0,0)
        bnv.selectedItemId = R.id.ic_home
        userId = intent.getIntExtra("id", 0)
        replaceFragment(fragArray[2], 0)
        val scanData = intent.getStringExtra("dataScan")
        if(scanData == "0"){
            bnv.selectedItemId = R.id.ic_scan
            currSelectedItem = 3
            replaceFragment(fragArray[currSelectedItem], 0)
            Toast.makeText(this, "Scanning was cancelled", Toast.LENGTH_SHORT).show()
        }
        else if(scanData!=null){
            //Todo: тут має бути функція дешифрування
            Toast.makeText(this, "Scan data: $scanData", Toast.LENGTH_SHORT).show()
        }

        bnv.setOnItemSelectedListener{
            compareItemsSelected(it.itemId)
            findViewById<TextView>(R.id.main_tool_bar_top_title).text = getString(titlesArray[currSelectedItem])
            true
        }
    }

    private fun replaceFragment(frag: Fragment, way: Int){
        val fragmentManager = supportFragmentManager
        val fragmentTrans = fragmentManager.beginTransaction()
        if(way == 1){
            fragmentTrans.setCustomAnimations(R.anim.enter_from_right_to_left, R.anim.exit_from_right_to_left)
        }
        else if(way == -1){
            fragmentTrans.setCustomAnimations(R.anim.enter_from_left_to_right, R.anim.exit_from_left_to_right)
        }
        val fragEx = frag
        val b = Bundle()
        b.putInt("id", userId)
        b.putInt("tableId", tableIdtoWork)
        fragEx.arguments = b
        fragmentTrans.replace(R.id.main_page_frag_view, fragEx)
        fragmentTrans.commit()
    }

    private fun compareItemsSelected(a: Int){
        var nextItemSelected: Int = currSelectedItem
        when(a){
            R.id.ic_qr -> nextItemSelected = 0
            R.id.ic_database -> nextItemSelected = 1
            R.id.ic_home -> nextItemSelected = 2
            R.id.ic_scan -> nextItemSelected = 3
            R.id.ic_user -> nextItemSelected = 4
        }
        if(nextItemSelected != currSelectedItem){
            if(nextItemSelected > currSelectedItem){
                replaceFragment(fragArray[nextItemSelected], 1)

            }
            else{
                replaceFragment(fragArray[nextItemSelected], -1)
            }
            currSelectedItem = nextItemSelected
        }
    }

    override fun intentDataFromMainToWelc(n: Int) {
        val intentVal = Intent(this, Welcome_page::class.java).apply {
            putExtra("id_reset", 1)
        }
        startActivity(intentVal)
        finish()
    }

    override fun passDataToMainToReplaceFrags(frag: Fragment, way: Int) {
        replaceFragment(frag, way)
    }

    override fun passDataToMainToReplaceFrags(frag: Fragment, way: Int, tableId: Int) {
        tableIdtoWork = tableId
        replaceFragment(frag, way)
    }

}