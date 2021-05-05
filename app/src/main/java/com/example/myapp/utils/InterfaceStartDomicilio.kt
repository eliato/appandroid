package com.example.myapp.utils

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InterfaceStartDomicilio {

    //@Headers("Content-Type: application/json")
    @POST("startDomicilio.php")
     fun startDomicilio(@Body requestBody: RequestBody): Call<com.example.myapp.Model.Resultado>



}

