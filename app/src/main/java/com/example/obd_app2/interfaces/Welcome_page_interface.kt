package com.example.obd_app2.interfaces

interface Welcome_page_interface {
    fun TransDataFromLogInToCheck(strLogin: String, strPass: String)
    fun TransDataFromLogInToAddPerson(strLogin: String, strPass: String, strEmail: String)
}