@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.obd_app2

import Adapters.TableColumnsItemsAdapter
import Table_or_data_classes.Table_to_create
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaquo.python.Python
import com.example.obd_app2.interfaces.EditTextChangeListener
import com.example.obd_app2.interfaces.Main_to_secondary_frags
import java.time.LocalDateTime

class Main_page_home_add_table : Fragment(), EditTextChangeListener {
    var UserId: Int? = null
    private val columnNameList = arrayListOf<String>("","","","","")
    private val columnDataType = arrayListOf<String>("","","","","")
    private var nameErrorTable = ""
    private var nameErrorColums = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
        @SuppressLint("SuspiciousIndentation")
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            // Inflate the layout for this fragment
            val v = inflater.inflate(R.layout.fragment_main_page_home_add_table, container, false)

            val data = arguments
            UserId = data?.getInt("id")

            val itemList: RecyclerView = v.findViewById(R.id.main_home_add_table_page_columns_list)
            val items = arrayListOf<Int>()
            itemList.setItemViewCacheSize(6)
            items.add(0)

            itemList.layoutManager = LinearLayoutManager(activity)
            val adapter = activity?.let { TableColumnsItemsAdapter(items, it, this) }
            itemList.adapter = adapter

            val addButton: ImageView = v.findViewById(R.id.add_column)
            val removeButton: ImageView = v.findViewById(R.id.remove_column)
            val tableNameEditText: EditText = v.findViewById(R.id.main_home_add_table_page_name_edittext)
            val addTableBtn: Button = v.findViewById(R.id.main_home_add_table_page_add_table_button)

            val backBtn: Button = v.findViewById(R.id.main_home_add_table_page_back_button)
            val myInterface: Main_to_secondary_frags = activity as Main_to_secondary_frags
            backBtn.setOnClickListener { myInterface.passDataToMainToReplaceFrags(Main_page_home(), -1) }
            addButton.setOnClickListener {
                if (items.size < 5) {
                    items.add(items.size)
                    adapter?.notifyItemInserted(items.size - 1)
                } else {
                    Toast.makeText(v.context, "There is limit of columns 5 for table", Toast.LENGTH_SHORT).show()
                }
            }
            removeButton.setOnClickListener {
                if (items.size > 1) {
                    items.removeAt(items.size - 1)
                    adapter?.notifyItemRemoved(items.size)
                } else {
                    Toast.makeText(
                        v.context,
                        "Table must contain at least one column",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            addTableBtn.setOnClickListener {
                var isCreateTableSucc = 0
                val tableName = tableNameEditText.text.toString().trim()
                if (checkIfEmpty(tableName)) {
                    isCreateTableSucc = 1
                    Toast.makeText(context, "Table name must be filled", Toast.LENGTH_SHORT)
                        .show()
                }
                if (checkIfTableNameUniq(tableName)) {
                    isCreateTableSucc = 1
                    if (nameErrorTable == "Error") {
                        Toast.makeText(requireActivity(), "Table name is not correct", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Table name must be unique", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                val listToCreateColName = ArrayList<String>()
                val listToCreateColType = ArrayList<String>()
                if (isCreateTableSucc == 0) {
                    for (i in 0..<items.size step 1) {
                        val strName = columnNameList[i].trim()
                        if (checkIfEmpty(strName)) {
                            Toast.makeText(context, "All field must be filled", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("myLog", "field ${i} is empty")
                            isCreateTableSucc = 1
                            break
                        }
                        listToCreateColName.add(strName)
                        listToCreateColType.add(columnDataType[i])
                        if (checkIfUniq(listToCreateColName, strName, i)) {
                            if(nameErrorColums=="Error"){
                                Toast.makeText(context, "Column name is not correct", Toast.LENGTH_SHORT)
                                    .show()
                                isCreateTableSucc = 1
                                break
                            }
                            else {
                                Log.d("myLog", "field ${i} is not unique")
                                Toast.makeText(
                                    context,
                                    "All Column names must be unique",
                                    Toast.LENGTH_SHORT
                                ).show()
                                isCreateTableSucc = 1
                                break
                            }
                        }
                    }
                }
                if (isCreateTableSucc == 0) {
                    var tableClassToCreate: Table_to_create = Table_to_create(
                        tableName,
                        items.size,
                        listToCreateColName,
                        listToCreateColType
                    )

                    val py = Python.getInstance()
                    val module = py.getModule("bd")

                    if (items.size == 1) {
                        val check = module["db_plus_table1"]
                        val checkInfo = module["db_plus_table_info"]
                        val checkSizeTable = module["size_table"]
                        val tb = check?.call(tableName, listToCreateColName[0], listToCreateColType[0]).toString()
                        checkInfo?.call(UserId, checkSizeTable?.call(UserId).toString(), items.size, LocalDateTime.now().toString(), tableName)
                    }
                    else if(items.size==2){
                        val check = module["db_plus_table2"]
                        val checkInfo = module["db_plus_table_info"]
                        val checkSizeTable = module["size_table"]
                        val tb = check?.call(tableName, listToCreateColName[0], listToCreateColType[0],
                            listToCreateColName[1], listToCreateColType[1]).toString()
                        checkInfo?.call(UserId, checkSizeTable?.call(UserId).toString(),items.size, LocalDateTime.now().toString(), tableName)
                    }
                    else if(items.size==3){
                        val check = module["db_plus_table3"]
                        val checkInfo = module["db_plus_table_info"]
                        val checkSizeTable = module["size_table"]
                        val tb = check?.call(tableName, listToCreateColName[0], listToCreateColType[0],
                            listToCreateColName[1], listToCreateColType[1],
                            listToCreateColName[2], listToCreateColType[2]).toString()
                        checkInfo?.call(UserId, checkSizeTable?.call(UserId).toString(),items.size, LocalDateTime.now().toString(), tableName)
                    }
                    else if(items.size==4){
                        val check = module["db_plus_table4"]
                        val checkInfo = module["db_plus_table_info"]
                        val checkSizeTable = module["size_table"]
                        val tb = check?.call(tableName, listToCreateColName[0], listToCreateColType[0],
                            listToCreateColName[1], listToCreateColType[1],
                            listToCreateColName[2], listToCreateColType[2],
                            listToCreateColName[3], listToCreateColType[3]).toString()
                        checkInfo?.call(UserId, checkSizeTable?.call(UserId).toString(),items.size, LocalDateTime.now().toString(), tableName)
                    }
                    else if(items.size==5){
                        val check = module["db_plus_table5"]
                        val checkInfo = module["db_plus_table_info"]
                        val checkSizeTable = module["size_table"]
                        val tb = check?.call(tableName, listToCreateColName[0], listToCreateColType[0],
                            listToCreateColName[1], listToCreateColType[1],
                            listToCreateColName[2], listToCreateColType[2],
                            listToCreateColName[3], listToCreateColType[3],
                            listToCreateColName[4], listToCreateColType[4]).toString()
                        checkInfo?.call(UserId, checkSizeTable?.call(UserId).toString(),items.size, LocalDateTime.now().toString(), tableName)
                    }
                    else {
                        Toast.makeText( requireActivity(),"Error", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText( requireActivity(),tableName+" is created", Toast.LENGTH_SHORT).show()
                    myInterface.passDataToMainToReplaceFrags(Main_page_home(), -1)
                }
            }

            return v
        }

        private fun checkIfTableNameUniq(tableName: String): Boolean {
            val py = Python.getInstance()
            val module = py.getModule("bd")
            val check = module["check_name_table"]
            val check_name = check?.call(tableName).toString()
            if (check_name =="Error"){
                nameErrorTable = "Error"
                return true
            }
            return check_name.toBooleanStrict()
        }

        private fun checkIfEmpty(str: String): Boolean {
            return str == ""
        }
        private fun containsOnlyAlphanumeric(str: String): Boolean {
            return str.all { it.isLetterOrDigit() }
        }

        private fun checkIfUniq(list: ArrayList<String>, str: String, index: Int): Boolean {
            val modList = ArrayList<String>()
            modList.addAll(list.filterNotNull())
            modList.removeAt(index)

            if (!containsOnlyAlphanumeric(str)) {
                nameErrorTable = "Error"
                return true
            }

            return modList.contains(str)
        }

        override fun onEditTextChanged(text: String, position: Int) {
            columnNameList.removeAt(position)
            columnNameList.add(position, text)
        }

        override fun onItemSelected(text: String, position: Int) {
            columnDataType.removeAt(position)
            columnDataType.add(position, text)
        }

    }