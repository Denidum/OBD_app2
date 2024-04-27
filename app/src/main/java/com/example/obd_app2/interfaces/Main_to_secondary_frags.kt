package com.example.obd_app2.interfaces

import androidx.fragment.app.Fragment

interface Main_to_secondary_frags {
    fun passDataToMainToReplaceFrags(frag: Fragment, way: Int)
}