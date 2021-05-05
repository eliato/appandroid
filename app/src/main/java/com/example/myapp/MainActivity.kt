package com.example.myapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.Model.Usuario
import com.example.myapp.Repo.ServiceBuilder
import com.example.myapp.utils.InterfaceUsuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIniciar = findViewById<Button>(R.id.bt_iniciar)
        btnIniciar.setOnClickListener{validar()}


    }
/*  aca stan las validaciones de los campos que no esten vacios chivo*/
    private fun validar(){
        val usuario = findViewById<EditText>(R.id.username)
        val usuario2 = usuario.text.toString()
        val pass = findViewById<EditText>(R.id.password)
        val password = pass.text.toString()

        if (usuario2.isNotEmpty())
        {
            if (password.isNotEmpty()){
                getRetrofit(usuario2,password)
            }else{
                alert("Contraseña Vacía", "Debes Escribir una Contraseña")
                pass.requestFocus()
            }
        }else {
                alert("Usuario Vacío", "Debes Escribir un Nombre de Usuario")
            usuario.requestFocus()
        }

    }


/* esta funcion es para el mensaje de alerta como me dijiste con dialog */
        private fun alert(msg: String, msg2: String){
            val builder = AlertDialog.Builder(this)
            builder.setTitle(msg)
            builder.setMessage(msg2)
            /* builder.setPositiveButton("yes") { _: DialogInterface, _: Int ->
                 finish()
             }*/
            builder.setNegativeButton("Aceptar") { _: DialogInterface, _: Int -> }
            builder.show()

        }

    private fun getRetrofit(usuario2: String, password: String) {
    val intent = Intent(this, ViewMain::class.java)

    val request = ServiceBuilder.buildService(InterfaceUsuario::class.java)
    val call = request.login(usuario2,password)

    call.enqueue(object :Callback<Usuario>{
        override fun onFailure(call: Call<Usuario>, t: Throwable) {
           Log.e("CallbackFail",t.message.toString())
        }

        override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
           Log.e("Login Response","$response")
                val usuario = response.body()
                if (usuario != null) {
                    if (usuario.usu_nombre == null){
                        alert("Usuario o Contraseña Erroneos", "Por favor Verificar")
                    }else{
                        //alert("bienvenido", "${usuario!!.usu_nombres}")
                        intent.putExtra("INTENT_USUARIO", usuario.usu_nombre)
                        intent.putExtra("INTENT_ID", usuario.usu_id_mo)
                        startActivity(intent)
                    }
                }


        }

    })
    }



}