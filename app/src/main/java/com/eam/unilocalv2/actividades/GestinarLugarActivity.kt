package com.eam.unilocalv2.actividades

import android.content.Context
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.eam.unilocalv2.R
import com.eam.unilocalv2.adapter.ViewPagerAdapterGestionarLugar
import com.eam.unilocalv2.bd.Lugares
import com.eam.unilocalv2.bd.Usuarios
import com.eam.unilocalv2.databinding.ActivityGestinarLugarBinding
import com.eam.unilocalv2.modelo.EstadoLugar
import com.eam.unilocalv2.modelo.Usuario
import com.google.android.material.tabs.TabLayoutMediator

class GestionarLugarActivity : AppCompatActivity() {

    var codigoLugar: Int = -1
    var fav: Boolean = false

    companion object{
        lateinit var binding: ActivityGestinarLugarBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestinarLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { this.finish() }

        codigoLugar = intent.extras!!.getInt("codigo")
        if(codigoLugar != -1){
            val lugar = Lugares.obtener(codigoLugar)
            val nombreLugar = lugar!!.nombre
            binding.nombreLugar.text = nombreLugar

            //Botón favorito
            val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
            val codigoUsuario = sp.getInt("codigo_usuario", -1)
            if(codigoUsuario != -1){
                val usuario = Usuarios.buscar(codigoUsuario)
                if(usuario != null){
                    fav = usuario.buscarFavorito(codigoLugar)
                    if(fav){
                        binding.imgFav.setImageResource(R.drawable.ic_favorite_40)
                    } else {
                        binding.imgFav.setImageResource(R.drawable.ic_favorite_border_40)
                    }

                    binding.imgFav.setOnClickListener { clickFav(usuario) }
                }
            }

            //Botón eliminar
            binding.btnEliminarLugar.setOnClickListener{
                Lugares.eliminar(lugar)
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }

            //Icono estado
            if(lugar.estado == EstadoLugar.ACEPTADO){
                binding.estado.text = "\uf058"
                binding.estado.setTextColor(ContextCompat.getColor(baseContext, R.color.green))
            } else if(lugar.estado == EstadoLugar.RECHAZADO){
                binding.estado.text = "\uf057"
                binding.estado.setTextColor(ContextCompat.getColor(baseContext, R.color.red))
            }

            //Adapter
            binding.viewPager.adapter = ViewPagerAdapterGestionarLugar(this, codigoLugar)
            TabLayoutMediator(binding.tabs, binding.viewPager){ tab, pos ->
                when(pos){
                    0 -> tab.text = getString(R.string.descripcion)
                    1 -> tab.text = getString(R.string.resenas)
                    2 -> tab.text = getString(R.string.fotos)
                }
            }.attach()

        }

    }

    fun clickFav(usuario : Usuario){
        if(fav){
            usuario.lugaresFavoritos.remove(codigoLugar)
            binding.imgFav.setImageResource(R.drawable.ic_favorite_border_40)
            fav = false
        } else {
            usuario.lugaresFavoritos.add(codigoLugar)
            binding.imgFav.setImageResource(R.drawable.ic_heart)
            fav = true
        }
    }
}