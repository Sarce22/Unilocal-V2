package com.eam.unilocalv2.actividades

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eam.unilocalv2.R
import com.eam.unilocalv2.adapter.ViewPagerAdapterLugarMod
import com.eam.unilocalv2.adapter.ViewPagerAdapterMod
import com.eam.unilocalv2.bd.LugaresService
import com.eam.unilocalv2.databinding.ActivityModDetalleLugarBinding
import com.eam.unilocalv2.modelo.EstadoLugar
import com.google.android.material.tabs.TabLayoutMediator



class ModDetalleLugarActivity : AppCompatActivity() {

    lateinit var binding: ActivityModDetalleLugarBinding
    var codigoLugar: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        codigoLugar = intent.extras!!.getString("codigo", "")

        if(codigoLugar != ""){
            LugaresService.obtener(codigoLugar){lug ->
                val lugar = lug
                if(lugar != null){
                    binding.nombreLugar.text = lugar.nombre

                    binding.btnVolver.setOnClickListener { this.finish() }
                    binding.btnAprobar.setOnClickListener {
                        LugaresService.editarEstadoLugar(lugar, EstadoLugar.ACEPTADO){res ->
                            if(res){
                                Toast.makeText(this, getString(R.string.lugar_aceptado), Toast.LENGTH_LONG).show()
                                ModMainActivity.binding.viewPager.adapter = ViewPagerAdapterMod(ModMainActivity.act)
                                this.finish()
                            }else{
                                Toast.makeText(this, getString(R.string.error_lugar_estado), Toast.LENGTH_LONG).show()
                                ModMainActivity.binding.viewPager.adapter = ViewPagerAdapterMod(ModMainActivity.act)
                                this.finish()
                            }
                        }
                    }
                    binding.btnRechazar.setOnClickListener {
                        LugaresService.editarEstadoLugar(lugar, EstadoLugar.RECHAZADO){res ->
                            if(res){
                                Toast.makeText(this, getString(R.string.lugar_rechazado), Toast.LENGTH_LONG).show()
                                ModMainActivity.binding.viewPager.adapter = ViewPagerAdapterMod(ModMainActivity.act)
                                this.finish()
                            }else{
                                Toast.makeText(this, getString(R.string.error_lugar_estado), Toast.LENGTH_LONG).show()
                                ModMainActivity.binding.viewPager.adapter = ViewPagerAdapterMod(ModMainActivity.act)
                                this.finish()
                            }
                        }
                    }

                    binding.viewPager.adapter = ViewPagerAdapterLugarMod(this, codigoLugar)
                    TabLayoutMediator(binding.tabs, binding.viewPager){ tab, pos ->
                        when(pos){
                            0 -> tab.text = getString(R.string.descripcion)
                            1 -> tab.text = getString(R.string.fotos)
                        }
                    }.attach()
                }
            }
        }

    }



}