package com.example.myapp

import android.content.Context

class Prefs(val context:Context) {

    val SHARED_NAME = "Mydtb"
    val SHARED_USER_NAME = "username"
    val SHARED_ID_MO = "idmo"
    val SHARED_ID_DM = "id_dm"
    val SHARED_ID_DM2 = "id_dm2"
    val SHARED_CODIGO_DM = "dm_codigo"

    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveName(name:String){
        storage.edit().putString(SHARED_USER_NAME, name).apply()
    }

    fun getName():String = storage.getString(SHARED_USER_NAME,"")!!

    fun saveIdmo(id_mo:Int){
        storage.edit().putInt(SHARED_ID_MO, id_mo).apply()
    }

    fun getIdmo():Int = storage.getInt(SHARED_ID_MO, 0)!!

    fun saveId_dm(id_dm:Int){
        storage.edit().putInt(SHARED_ID_DM, id_dm).apply()
    }
    fun getId_dm():Int = storage.getInt(SHARED_ID_DM,0)!!

    fun saveId_dm2(id_dm2:Int){
        storage.edit().putInt(SHARED_ID_DM2, id_dm2).apply()
    }
    fun getId_dm2():Int = storage.getInt(SHARED_ID_DM2,0)!!
    
    fun saveCodigo(dm_codigo:String){
        storage.edit().putString(SHARED_CODIGO_DM, dm_codigo).apply()
    }
    fun getCodigo():String= storage.getString(SHARED_CODIGO_DM,"")!!
    

    fun wipe(){
        storage.edit().clear().apply()
    }

}