package com.eam.unilocalv2.actividades

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import com.eam.unilocalv2.R
import com.eam.unilocalv2.databinding.ActivityMainBinding
import com.eam.unilocalv2.fragmentos.FavoritosFragment
import com.eam.unilocalv2.fragmentos.InicioFragment
import com.eam.unilocalv2.fragmentos.MisLugaresFragment

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var binding: ActivityMainBinding
        var MENU_INICIO = "inicio"
        var MENU_MIS_LUGARES = "mis_lugares"
        var MENU_FAVORITOS = "favoritos"
        var MENU_CREAR_LUGAR = "favoritos"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuInferior.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_inicio -> reemplazarFragmento(1, MENU_INICIO)
                R.id.menu_mis_lugares -> reemplazarFragmento(2, MENU_MIS_LUGARES)
                R.id.menu_favoritos -> reemplazarFragmento(3, MENU_FAVORITOS)
            }
            true
        }

        reemplazarFragmento(1, MENU_INICIO)

    }

    fun reemplazarFragmento(valor: Int, nombre: String){
        var fragmento: Fragment

        fragmento = if(valor == 1){
            InicioFragment()
        } else if(valor == 2){
            MisLugaresFragment()
        } else {
            FavoritosFragment()
        }

        supportFragmentManager.beginTransaction().replace( binding.contenidoPrincipal.id, fragmento )
            .addToBackStack(nombre)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val count = supportFragmentManager.backStackEntryCount

        if(count>0){
            val nombre = supportFragmentManager.getBackStackEntryAt(count - 1).name
            when(nombre) {
                MENU_INICIO -> binding.menuInferior.menu.getItem(0).isChecked = true
                MENU_MIS_LUGARES -> binding.menuInferior.menu.getItem(1).isChecked = true
                else -> binding.menuInferior.menu.getItem(2).isChecked = true
            }
        }
    }

    fun irALogin(v: View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}