package com.example.myapp

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.Model.GetData


class AdapterData(private val exampleList: List<GetData>,context: Context) :
    RecyclerView.Adapter<AdapterData.ExampleViewHolder>() {

    var con = context
    lateinit var prefs: SharedPreferences
    var siYa = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_data,
            parent, false)
       prefs = con.getSharedPreferences("Preferencias", Context.MODE_PRIVATE)
        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]
        //holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.cliente
        holder.textView2.text = currentItem.mt_motivo

        holder.itemView.setOnClickListener {
           if (!siYa && position<= (exampleList.size-1)){
               //aqui en teoria iniciarias verdad
               siYa=true
               var e = prefs.edit()
               e.putBoolean("Tracing",siYa)

           }
            Toast.makeText(con, "seleccionaste un domicilio", Toast.LENGTH_SHORT).show()


        }
        holder.switchValue.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked){
                Toast.makeText(con,"Inicio el trackeo $position ${exampleList[position].cliente}",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(con,"Finalizo el trackeo $position  ${exampleList[position].cliente}",Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun getItemCount() = exampleList.size
    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val textView1: TextView = itemView.findViewById(R.id.tname)
        val textView2: TextView = itemView.findViewById(R.id.direccion)

        var switchValue = itemView.findViewById<SwitchCompat>(R.id.startEnd)



    }
}