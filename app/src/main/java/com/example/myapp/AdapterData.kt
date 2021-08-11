package com.example.myapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.DataUser.Companion.preffs
import com.example.myapp.Model.GetData


class AdapterData(private val exampleList: List<GetData>,val context: Context) :
    RecyclerView.Adapter<AdapterData.ExampleViewHolder>() {



    var siYa = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_data,
            parent, false)

        return ExampleViewHolder(itemView)


    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]
        //holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.cliente
        holder.textView2.text = currentItem.telefono
        holder.textView3.text = currentItem.dir_direccion
        holder.textView4.text = currentItem.mt_motivo
        holder.textView5.text = "Orden: "+currentItem.rt_orden.toString()
        holder.textView6.text = "Factura: "+currentItem.factura

        holder.itemView.setOnClickListener {
            //context.startActivity(Intent(context, ActivityWeb::class.java))
            Toast.makeText(context, "Abriendo Ruta hacia el Cliente.", Toast.LENGTH_SHORT).show()

            /*val intent = Intent(context, ActivityWeb::class.java) //version es la version web
            intent.putExtra("VALOR", "${currentItem.dm_id}")
            */
            val intent = Intent(context, NavigationView::class.java) //version utilizando google maps
            intent.putExtra("VALOR", "${currentItem.latlong_cli}")

            context.startActivity(intent)
        }

       /* if (exampleList[position].des_id_estado == 3){
            holder.switchValue.isChecked = true

        }*/


       holder.btnIniciar.setOnClickListener {
           Toast.makeText(context,"Inicio el trackeo ${exampleList[position].cliente}",Toast.LENGTH_LONG).show()
           preffs.saveId_dm(exampleList[position].dm_id)
           preffs.saveCodigo(exampleList[position].dm_codigo)
           preffs.saveMo_nombre(exampleList[position].mo_nombre)
           preffs.saveMo_telefono(exampleList[position].mo_telefono)
           preffs.saveVh_placa(exampleList[position].vh_placa)
           preffs.saveFactura(exampleList[position].factura)
           preffs.saveFoto(exampleList[position].mo_url_img)
           (context as ViewMain).locationupdates()
       }

        holder.btnFinalizar.setOnClickListener {
            Toast.makeText(context,"Finalizo el trackeo $position  ${exampleList[position].cliente}",Toast.LENGTH_LONG).show()
            preffs.saveId_dm2(exampleList[position].dm_id)
            (context as ViewMain).detenerLocationUpdates()
            context.finalizaDomicilio()
            preffs.saveId_dm(0)
        }


        /*holder.switchValue.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked){
                Toast.makeText(context,"Inicio el trackeo ${exampleList[position].cliente}",Toast.LENGTH_LONG).show()
                preffs.saveId_dm(exampleList[position].dm_id)
                preffs.saveCodigo(exampleList[position].dm_codigo)
                preffs.saveMo_nombre(exampleList[position].mo_nombre)
                preffs.saveMo_telefono(exampleList[position].mo_telefono)
                preffs.saveVh_placa(exampleList[position].vh_placa)
                preffs.saveFactura(exampleList[position].factura)

                (context as ViewMain).locationupdates()

            }else{
                Toast.makeText(context,"Finalizo el trackeo $position  ${exampleList[position].cliente}",Toast.LENGTH_LONG).show()
                preffs.saveId_dm2(exampleList[position].dm_id)
                (context as ViewMain).detenerLocationUpdates()
                context.finalizaDomicilio()
                preffs.saveId_dm(0)
            }
        }*/
    }
    override fun getItemCount() = exampleList.size
    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val textView1: TextView = itemView.findViewById(R.id.tname)
        val textView2: TextView = itemView.findViewById(R.id.telefono)
        val textView3: TextView = itemView.findViewById(R.id.direccion)
        val textView4: TextView = itemView.findViewById(R.id.tipo)
        val textView6: TextView = itemView.findViewById(R.id.factura)
        val textView5: TextView = itemView.findViewById(R.id.orden)
        //var switchValue = itemView.findViewById<SwitchCompat>(R.id.startEnd)
        var btnIniciar = itemView.findViewById<Button>(R.id.biniciar)
        var btnFinalizar = itemView.findViewById<Button>(R.id.bfinalizar)

    }



   }



