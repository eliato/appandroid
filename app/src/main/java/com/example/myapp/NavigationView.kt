package com.example.myapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NavigationView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_view)
        var id = intent.getStringExtra("VALOR")

        val gmmIntentUri =
                Uri.parse("google.navigation:q=$id&mode=l")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent)


    }
}