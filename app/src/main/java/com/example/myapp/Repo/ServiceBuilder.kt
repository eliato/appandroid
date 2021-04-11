package com.example.myapp.Repo

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder{
    private val cliente = OkHttpClient.Builder().build()
    // esta clase te va a crear la instancia para conectarte, ya veras.

    private val retrofit = Retrofit.Builder()

        //.baseUrl("http://192.168.169.231/android/")
        .baseUrl("http://170.80.23.121/domicilios_ver2/webservices/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(cliente)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    fun<T> buildService(service:Class<T>): T{
        return retrofit.create(service)
    }

}