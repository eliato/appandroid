package com.example.myapp

import android.app.Application

class DataUser : Application() {

    companion object{
        lateinit var preffs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
       preffs = Prefs(applicationContext)

    }
}