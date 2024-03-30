package com.example.obd_app2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class Main_page : AppCompatActivity() {
    private var currSelectedItem: Int = 2;
    private val fragArray = arrayOf(Main_page_qr(), Main_page_database(), Main_page_home(), Main_page_scan(),Main_page_user())
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
        bnv.setOnItemSelectedListener{
            compareItemsSelected(it.itemId)
            true
        }
    }

    private fun replaceFragment(index: Int, way: Int){
        val fragmentManager = supportFragmentManager
        val fragmentTrans = fragmentManager.beginTransaction()
        if(way == 1){
            fragmentTrans.setCustomAnimations(R.anim.enter_from_right_to_left, R.anim.exit_from_right_to_left)
        }
        else if(way == -1){
            fragmentTrans.setCustomAnimations(R.anim.enter_from_left_to_right, R.anim.exit_from_left_to_right)
        }

        fragmentTrans.replace(R.id.main_page_frag_view, fragArray[index])
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
                replaceFragment(nextItemSelected, 1)

            }
            else{
                replaceFragment(nextItemSelected, -1)
            }
            currSelectedItem = nextItemSelected
        }
    }
}