package com.example.myapp.Model

data class Usuario(
       // @SerializedName("usu_usuario")
    val usu_nombre:String,
    val usu_password:String,
    val usu_id_tp:Int,
    val usu_apellidos:String,
    val valido:Boolean,
    val usu_id:Int,
    val usu_id_mo: Int

)