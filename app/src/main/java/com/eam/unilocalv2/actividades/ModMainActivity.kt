package com.eam.unilocalv2.actividades

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import com.eam.unilocalv2.R
import com.eam.unilocalv2.adapter.ViewPagerAdapterMod
import com.eam.unilocalv2.databinding.ActivityModMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class ModMainActivity : AppCompatActivity() {

    companion object {
        lateinit var binding: ActivityModMainBinding
        lateinit var act: FragmentActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        act = this

        binding.btnCerrarSesion.setOnClickListener{ cerrarSesion() }

        binding.viewPager.adapter = ViewPagerAdapterMod(this)
        TabLayoutMediator(binding.tabs, binding.viewPager){tab, pos ->
            when (pos){
                0 -> tab.text = getString(R.string.lugares)
                1 -> tab.text = getString(R.string.registro)
            }
        }.attach()

    }

    fun cerrarSesion(){
        val sh = getSharedPreferences("sesion", Context.MODE_PRIVATE).edit()
        sh.clear()
        sh.commit()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}