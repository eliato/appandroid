package com.example.myapp.utils

import com.example.myapp.Model.FinalizaDomicilio
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface InterfaceFinishDomicilio {
    @Headers("Content-Type: application/json")
    @GET("update_estado_domicilio.php")
    fun finalizaDomicilio(@Query("id_dm") id_dm:Int): Call<FinalizaDomicilio>
}