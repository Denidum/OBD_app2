package com.example.obd_app2.interfaces

import Table_or_data_classes.Table_to_create
import androidx.fragment.app.Fragment

interface Main_to_secondary_frags {
    fun passDataToMainToReplaceFrags(frag: Fragment, way: Int)
    fun passDataToMainToReplaceFrags(frag: Fragment, way: Int, tableId: Int)
    fun passDataToMainToReplaceFrags(frag: Fragment, way: Int, tableId: Int, rowId: Int)
}