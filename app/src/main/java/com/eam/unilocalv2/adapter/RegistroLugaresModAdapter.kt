package com.eam.unilocalv2.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.eam.unilocalv2.R
import com.eam.unilocalv2.modelo.EstadoLugar
import com.eam.unilocalv2.modelo.RegistroEstadoLugar

class RegistroLugaresModAdapter(var lista: ArrayList<RegistroEstadoLugar>): RecyclerView.Adapter<RegistroLugaresModAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_registro_lugar_mod, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( lista[position] )
    }

    override fun getItemCount() = lista.size

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nombre: TextView = itemView.findViewById(R.id.nombre_lugar)
        val ic: TextView = itemView.findViewById(R.id.estado_ic)
        val estado: TextView =  itemView.findViewById(R.id.estado)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(registro: RegistroEstadoLugar){
            nombre.text = registro.lugar.nombre
            if(registro.nuevoEstado == EstadoLugar.ACEPTADO){
                estado.text = "APROBADO"
                estado.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                ic.text = "\uf058"
                ic.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
            }else{
                estado.text = "RECHAZADO"
                estado.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                ic.text = "\uf057"
                ic.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
            }
        }

        override fun onClick(p0: View?) {
            //Mostrar lugar
        }

    }

}