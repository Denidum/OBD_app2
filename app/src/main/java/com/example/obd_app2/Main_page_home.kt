package com.example.obd_app2

import Adapters.TableListItemsAdapter
import Table_or_data_classes.Table
import android.app.Activity
import android.os.Build
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.obd_app2.interfaces.Welcome_page_interface
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obd_app2.interfaces.ChooseTableOrDataToDelete
import com.example.obd_app2.interfaces.Main_to_secondary_frags

class Main_page_home : Fragment(), ChooseTableOrDataToDelete {
    private var myInterface: Main_to_secondary_frags? = null
    private val items = arrayListOf<Table>()
    private var listTableToDelete = arrayListOf<Int>()
    var rastrelList = arrayListOf<String>()
    private var userId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main_page_home, container, false)

        val addTableBtn: Button = v.findViewById(R.id.main_home_page_add_table_button)
        val delTableBtn: Button = v.findViewById(R.id.main_home_page_delete_table_button)

        val data = arguments

        val userId = data?.getInt("id")

        val itemList: RecyclerView = v.findViewById(R.id.main_home_page_table_list)

        itemList.setItemViewCacheSize(6)
        val items = arrayListOf<Table>()

        val py = Python.getInstance()
        val module = py.getModule("bd")

        val checkSizeTable = module["size_table"]
        val checkTime = module["info_table_time"]
        val checkRow = module["info_table_row"]
        val checkIdTable = module["info_table_name_table"]

        for (i in 0..Integer.parseInt(checkSizeTable?.call(userId).toString()) - 1) {
            items.add(
                Table(i, checkIdTable?.call(i, userId).toString(), Integer.parseInt(checkRow?.call(i, userId).toString()), checkTime?.call(i, userId).toString())
            )
        }

            //На основі кількості таблиць, які є у користувача, у списку listTableToDelete створюється така сама кількість елементів, які відповідають за стан кнопок вибору у списку, що відображається на екрані
            for (i in 0..<items.size step 1) {
                listTableToDelete.add(0)
            }

            itemList.layoutManager = LinearLayoutManager(activity)
            itemList.adapter = activity?.let { TableListItemsAdapter(items, it, this) }

            val myInterface: Main_to_secondary_frags = activity as Main_to_secondary_frags
            addTableBtn.setOnClickListener {
                if (items.size < 5) {
                    myInterface.passDataToMainToReplaceFrags(Main_page_home_add_table(), 1)
                } else {
                    Toast.makeText(v.context, "There is limit of 5 tables", Toast.LENGTH_SHORT)
                        .show()
                }
            }


            delTableBtn.setOnClickListener {
                if (!listTableToDelete.contains(1)) {
                    Toast.makeText(context, "Choose any table to delete", Toast.LENGTH_SHORT).show()
                } else {
                    for (i in 0..<listTableToDelete.size step 1) {
                        if (listTableToDelete[i] == 1) {
                            rastrelList.add(items[i].name)
                        }
                    }
                    confirmDeletionDialog()
                }
            }
            return v
        }

        fun confirmDeletionDialog() {
            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Confirm deletion")
            dialog.setTitle("Do you want to delete chosen tables?")
            dialog.setPositiveButton("No") { dialog, which ->

                dialog.dismiss()
            }
            dialog.setNegativeButton("Yes") { dialog, which ->
                deleteTableFromSQL()
                dialog.dismiss()
            }
            dialog.show()
        }

        fun deleteTableFromSQL() {
            val py = Python.getInstance()
            val module = py.getModule("bd")
            val checkDeleteTable = module["table_delete"]
            val checkDeleteInfoTable = module["delete_row_name_table"]

            for(i in 0..<rastrelList.size step 1) {
                val del = checkDeleteTable?.call(rastrelList[i]).toString()
                val infoDel = checkDeleteInfoTable?.call(rastrelList[i]).toString()
                Toast.makeText(context, del + "is deleted", Toast.LENGTH_SHORT).show()
            }
            myInterface?.passDataToMainToReplaceFrags(Main_page_home(), 0)
            (activity as? Activity)?.recreate()
        }

        override fun buttonChecked(index: Int) {
            listTableToDelete.removeAt(index)
            listTableToDelete.add(index, 1)
        }

        override fun buttonUnchecked(index: Int) {
            listTableToDelete.removeAt(index)
            listTableToDelete.add(index, 0)
        }
}