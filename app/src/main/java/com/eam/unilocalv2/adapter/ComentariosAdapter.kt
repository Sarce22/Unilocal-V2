package com.eam.unilocalv2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.eam.unilocalv2.R
import com.eam.unilocalv2.actividades.DetalleLugarActivity
import com.eam.unilocalv2.bd.LugaresService
import com.eam.unilocalv2.bd.UsuariosService
import com.eam.unilocalv2.fragmentos.ComentariosLugarFragment
import com.eam.unilocalv2.modelo.Comentario


class ComentariosAdapter(var lista:ArrayList<Comentario>, var codigoUsuario: String,var keyLugar: String): RecyclerView.Adapter<ComentariosAdapter.ViewHolder>() {


   override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount() = lista.size

    inner class ViewHolder(var itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val imgUsuario: ImageView = itemView.findViewById(R.id.img_usuario)
        val nombreUsuario: TextView = itemView.findViewById(R.id.nombre_usuario_comentario)
        val listaEstrellas: LinearLayout = itemView.findViewById(R.id.lista_estrellas_comentario)
        val fecha: TextView = itemView.findViewById(R.id.fecha_comentario)
        val coment: TextView = itemView.findViewById(R.id.comentario)
        val btnEliminar: TextView =  itemView.findViewById(R.id.btn_eliminar_comentario)

        fun bind(comentario: Comentario){
            UsuariosService.buscar(comentario.keyUsuario){usuario ->

                if(comentario.keyUsuario == codigoUsuario){
                    btnEliminar.setOnClickListener{
                        LugaresService.eliminarComentario(comentario.key, keyLugar){ res ->
                            if(res){
                                DetalleLugarActivity.binding.viewPager.adapter =  ViewPagerAdapterLugar(ComentariosLugarFragment.act, keyLugar, 1)
                                DetalleLugarActivity.binding.viewPager.setCurrentItem(1)
                            }
                        }
                    }
                }else{
                    btnEliminar.isVisible = false
                }

                if(usuario != null){
                    nombreUsuario.text = usuario.nickname
                }

                val cal: Int = comentario.calificacion

                if(cal != 0){
                    for (i in 0 until cal){
                        (listaEstrellas[i] as TextView).setTextColor(ContextCompat.getColor(listaEstrellas.context, R.color.yellow))
                    }
                }

                fecha.text = comentario.fecha.toString().substring(0, 10)

                coment.text = comentario.texto
            }
        }

        override fun onClick(p0: View?) {}

    }
}