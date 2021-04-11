package com.example.myapp.utils

import com.example.myapp.Model.Usuario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface InterfaceUsuario {
    @Headers("Content-Type: application/json")
    @GET("login.php")
    fun login(@Query("usuario") usuario: String,@Query("password") password:String ): Call<Usuario>
}