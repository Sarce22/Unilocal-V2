package com.eam.unilocalv2.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.eam.unilocalv2.R
import com.eam.unilocalv2.actividades.BusquedaActivity
import com.eam.unilocalv2.fragmentos.ResultadoBusquedaFragment

class BusquedasRecientesAdapter(var lista:ArrayList<String>): RecyclerView.Adapter<BusquedasRecientesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_reciente, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount() = lista.size

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val reciente: TextView = itemView.findViewById(R.id.reciente)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(busquedaReciente: String){
            reciente.text = busquedaReciente
        }

        override fun onClick(p0: View?) {
            BusquedaActivity.binding.buscador.setText(reciente.text.toString())
            BusquedaActivity.binding.buscador.setSelection(reciente.text.toString().length)
            //Cambiar fragmento
            val ac: BusquedaActivity = BusquedaActivity()
            BusquedaActivity.fragmentMngr.beginTransaction()
                .replace(BusquedaActivity.binding.contenido.id, ResultadoBusquedaFragment.newInstance(reciente.text.toString()))
                .addToBackStack("busqueda")
                .commit()
        }

    }
}