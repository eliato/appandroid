package com.example.myapp

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.DataUser.Companion.preffs
import com.example.myapp.Model.FinalizaDomicilio
import com.example.myapp.Model.GetData
import com.example.myapp.Model.Resultado
import com.example.myapp.Repo.ServiceBuilder
import com.example.myapp.utils.InterfaceFinishDomicilio
import com.example.myapp.utils.InterfaceGetData
import com.example.myapp.utils.InterfaceStartDomicilio
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_view_main.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.text.DecimalFormat

class ViewMain : AppCompatActivity(), MultiplePermissionsListener, LocationListener {

    lateinit var prefs:SharedPreferences
    lateinit var locationManager:LocationManager
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_view_main)
       prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE)
        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val refrescar = findViewById<Button>(R.id.refrescar)
        sharedPreferences = getSharedPreferences("valor_id", Context.MODE_PRIVATE)

        refrescar.setOnClickListener{
           // locationManager.removeUpdates(this)
            detenerLocationUpdates()
            locationupdates()
            showName()

        }

        Dexter.withContext(this@ViewMain)
            .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .withListener(this)
            .check();
        //startForegroundService(Intent(applicationContext, LocationTrackingService::class.java))
        //checkUserLog()
        showName()
        //lista[0].Id117977694
    }

    override fun onBackPressed() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cerrar_session -> {
                preffs.wipe()
                startActivity(Intent(this, MainActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun showName(){
        val bundle = intent.extras
        //var id = bundle?.get("INTENT_ID") as Int
        var id = preffs.getIdmo()

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
                        recicleview.adapter?.notifyDataSetChanged()
                        recicleview.adapter = null
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


    fun startDomicilio(id_dm: Int, latitude: String, longitude: String){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://170.80.23.121/domicilios_ver2/webservices/")
                //.addCallAdapterFactory(object : RxJavaCallAdapterFactory())
                //.addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(InterfaceStartDomicilio::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("id_dm", id_dm)
        jsonObject.put("latitude", latitude)
        jsonObject.put("longitude", longitude)

        // Convert JSONObject to String 
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        Log.e("BODY", "${jsonObjectString}")

        val request = ServiceBuilder.buildService(InterfaceStartDomicilio::class.java)
        val call = request.startDomicilio(requestBody)

        call.enqueue(object :Callback<Resultado>{
            override fun onFailure(call: Call<Resultado>, t: Throwable) {
                Log.e("ERROR", "Faile" )
            }

            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {
                Log.e("SUCCESS", "${response.isSuccessful}" )
                Log.e("ACTIVA","$response")

            }

        })
    }



    fun locationupdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
               // Manifest.permission.ACCESS_FINE_LOCATION  //para ver 9-
                  Manifest.permission.ACCESS_COARSE_LOCATION //para ver 10+
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
        }else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    55000,
                    0F,
                  this
            )

        }
    }

    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
        Toast.makeText(this@ViewMain, "PERMISOS HABILITADOS",Toast.LENGTH_LONG).show()
        //locationupdates()
        //locationManager.removeUpdates(LocationListener { })
    }

    override fun onPermissionRationaleShouldBeShown(
        p0: MutableList<PermissionRequest>?,
        p1: PermissionToken?
    ) {

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

    override fun onLocationChanged(location: Location) {
        val dec = DecimalFormat("#.######")
        var latitud = dec.format(location.latitude)
        var longitud = dec.format(location.longitude)

        Log.e("LOCATION", "${latitud},${longitud}")
        var id = preffs.getId_dm()
        Log.e("MENSAJE", "$id")
        if (id != 0){
            startDomicilio(id, latitud, longitud)
        }

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //super.onStatusChanged(provider, status, extras)
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
    fun onPause(provider: String){
        super.onProviderDisabled(provider)
        locationManager.removeUpdates(this)
    }

    fun detenerLocationUpdates(){
        locationManager.removeUpdates(this)
    }


    fun finalizaDomicilio(){
        var idd = preffs.getId_dm2()
        val request = ServiceBuilder.buildService(InterfaceFinishDomicilio::class.java)
        val call = request.finalizaDomicilio(idd)
        call.enqueue(object :Callback<FinalizaDomicilio>{
            override fun onFailure(call: Call<FinalizaDomicilio>, t: Throwable) {
                Log.e("CallbackFail",t.message.toString())
            }
            override fun onResponse(call: Call<FinalizaDomicilio>, response: Response<FinalizaDomicilio>) {
                Log.e("Login Response","$response")
                //val fdomicilio = response.body()
            }

        })

    }


}