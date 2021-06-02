package com.example.myapp

import android.content.Context

class Prefs(val context:Context) {

    val SHARED_NAME = "Mydtb"
    val SHARED_USER_NAME = "username"
    val SHARED_ID_MO = "idmo"

    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveName(name:String){
        storage.edit().putString(SHARED_USER_NAME, name).apply()
    }

    fun getName():String = storage.getString(SHARED_USER_NAME,"")!!

    fun saveIdmo(id_mo:Int){
        storage.edit().putInt(SHARED_ID_MO, id_mo).apply()
    }

    fun getIdmo():Int = storage.getInt(SHARED_ID_MO, 0)!!

}