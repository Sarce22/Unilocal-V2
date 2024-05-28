package com.eam.unilocalv2.actividades

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eam.unilocalv2.R
import com.eam.unilocalv2.adapter.ViewPagerAdapterLugar
import com.eam.unilocalv2.bd.LugaresService
import com.eam.unilocalv2.bd.UsuariosService
import com.eam.unilocalv2.databinding.ActivityDetalleLugarBinding
import com.eam.unilocalv2.modelo.Usuario
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class DetalleLugarActivity : AppCompatActivity() {

    var codigoLugar: String? = ""
    var fav: Boolean = false

    companion object{
        lateinit var binding: ActivityDetalleLugarBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { this.finish() }
        binding.btnSearch.setOnClickListener { startActivity(Intent(this, BusquedaActivity::class.java)) }

        codigoLugar = intent.extras!!.getString("codigo")

        if(codigoLugar != null){
            var nombreLugar = ""
            LugaresService.obtener(codigoLugar!!){lugar ->
                if(lugar != null){
                    nombreLugar = lugar.nombre
                    binding.nombreLugar.text = nombreLugar
                } else {
                    nombreLugar = "ERROR"
                }
            }
            //Adapter
            binding.viewPager.adapter = ViewPagerAdapterLugar(this, codigoLugar!!, 1)
            TabLayoutMediator(binding.tabs, binding.viewPager){ tab, pos ->
                when(pos){
                    0 -> tab.text = getString(R.string.txt_detalles)
                    1 -> tab.text = getString(R.string.resenas)
                    2 -> tab.text = getString(R.string.fotos)
                }
            }.attach()

            //Boton favorito
            val user = FirebaseAuth.getInstance().currentUser
            if(user != null){
                UsuariosService.buscar(user.uid){u ->
                    if(u != null){
                        fav = u.buscarFavorito(codigoLugar!!)
                        if(fav){
                            binding.imgFav.setImageResource(R.drawable.ic_favorite_40)
                        } else {
                            binding.imgFav.setImageResource(R.drawable.ic_favorite_border_40)
                        }
                        binding.imgFav.setOnClickListener { clickFav(u) }
                    }
                }
            }
        }
    }

    fun clickFav(usuario : Usuario){
        if(fav){
            usuario.eliminarFavorito(codigoLugar!!)
            UsuariosService.actualizarUsuario(usuario){res ->
                if(res){
                    binding.imgFav.setImageResource(R.drawable.ic_favorite_border_40)
                    fav = false
                }
            }
        } else {
            usuario.agregarFavorito(codigoLugar!!)
            UsuariosService.actualizarUsuario(usuario){res ->
                if(res){
                    binding.imgFav.setImageResource(R.drawable.ic_favorite_40)
                    fav = true
                }
            }
        }
    }
}