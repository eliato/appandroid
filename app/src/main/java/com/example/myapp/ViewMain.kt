package com.example.myapp

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.Model.GetData
import com.example.myapp.Repo.ServiceBuilder
import com.example.myapp.utils.InterfaceGetData
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_view_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewMain : AppCompatActivity(), MultiplePermissionsListener {

    lateinit var prefs:SharedPreferences
    lateinit var locationManager:LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_main)
       prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE)
        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager

        Dexter.withContext(this@ViewMain)
            .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .withListener(this)
            .check();

        showName()
        //lista[0].Id
    }

    fun showName(){
        val bundle = intent.extras
        val id = bundle?.get("INTENT_ID") as Int
        //Log.e("id", id.toString())
        getRetrofit(id)

    }

    private fun getRetrofit(id: Int) {
        //val textView : TextView = findViewById(R.id.recicleview)

        //val intent = Intent(this, ViewMain::class.java)

        val request = ServiceBuilder.buildService(InterfaceGetData::class.java)
        val call = request.Get_Data(id)

        call.enqueue(object : Callback<List<GetData>> {
            override fun onFailure(call: Call<List<GetData>>, t: Throwable) {
                Log.e("CallbackFail",t.message.toString())
            }

            override fun onResponse(call: Call<List<GetData>>, response: Response<List<GetData>>) {
                Log.e("Login Response","$response")
                val usuario = response.body()
                if (usuario != null) {
                    if (usuario[0].dm_id == 0){
                       //Toast.makeText(this@ViewMain,"Sin Domicilios",Toast.LENGTH_LONG).show()
                        alert()
                    }else{
                        //alert("bienvenido", "${usuario!!.usu_nombres}")
                        //intent.putExtra("INTENT_USUARIO", usuario.cli_id)
                        //intent.putExtra("INTENT_ID", usuario.cli_dir)
                       //textView.text= usuario.cli_dir
                       // startActivity(intent)

                    recicleview.layoutManager = LinearLayoutManager(this@ViewMain)
                        recicleview.adapter = AdapterData(usuario,this@ViewMain)
                    }
                }


            }

        })
    }

    private fun alert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No Tienes Domicilios Asignados")
        builder.setIcon(R.drawable.ic_baseline_block_24)
        //builder.setMessage(msg2)
        /* builder.setPositiveButton("yes") { _: DialogInterface, _: Int ->
             finish()
         }*/
        builder.setNegativeButton("Aceptar") { _: DialogInterface, _: Int -> }
        builder.show()

    }

    fun locationupdates(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this@ViewMain,"Error",Toast.LENGTH_LONG).show()
            return
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0F, object :
                LocationListener {
                override fun onLocationChanged(p0: Location) {
                    //Toast.makeText(this@ViewMain,"${p0.latitude}",Toast.LENGTH_LONG).show()
                    Log.e("LOCATION", "${p0.latitude}")
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    super.onStatusChanged(provider, status, extras)
                    Log.e("STATUS", "${status}")
                }

                override fun onProviderEnabled(provider: String) {
                    super.onProviderEnabled(provider)
                    Log.e("ENABLED", "${provider}")
                }

                override fun onProviderDisabled(provider: String) {
                    super.onProviderDisabled(provider)
                    Log.e("DISABLED", "${provider}")

                }

            })
        }

    }
    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
        Toast.makeText(this@ViewMain, "PERMISOS HABILITADOS",Toast.LENGTH_LONG).show()
        locationupdates()


    }



    override fun onPermissionRationaleShouldBeShown(
        p0: MutableList<PermissionRequest>?,
        p1: PermissionToken?
    ) {

    }


}