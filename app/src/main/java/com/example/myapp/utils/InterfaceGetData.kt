package com.example.myapp.utils

import com.example.myapp.Model.GetData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface InterfaceGetData {

    @Headers("Content-Type: application/json")
    @GET("get_data.php")
    fun Get_Data(@Query("id") id: Int ): Call<List<GetData>>
}