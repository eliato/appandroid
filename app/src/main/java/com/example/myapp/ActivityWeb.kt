package com.example.myapp

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web.*

class ActivityWeb : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        var id = intent.getStringExtra("VALOR")

        val url = "https://domicilioslcr.com/domicilios_ver2/webservices/rt_ruta_moto_webservice.php?id_dm=$id"

        val BASE_URL = url

     wv1.webChromeClient = object : WebChromeClient(){

     }

     wv1.webViewClient = object : WebViewClient(){

     }

     val setting:WebSettings = wv1.settings
        setting.javaScriptEnabled = true

        wv1.loadUrl(BASE_URL.toString())
    }
}