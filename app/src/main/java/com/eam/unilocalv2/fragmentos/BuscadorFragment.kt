package com.eam.unilocalv2.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eam.unilocalv2.R
import com.eam.unilocalv2.actividades.BusquedaActivity
import com.eam.unilocalv2.actividades.LoginActivity
import com.eam.unilocalv2.databinding.FragmentBuscadorBinding

class BuscadorFragment : Fragment() {
    lateinit var binding: FragmentBuscadorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscadorBinding.inflate(inflater, container, false)
        binding.buscador.setOnClickListener { startActivity(Intent(requireActivity(), BusquedaActivity::class.java)) }
        binding.imgUsuario.setOnClickListener { cerrarSesion() }
        return binding.root
    }

    fun cerrarSesion(){
        val sh = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE).edit()
        sh.clear()
        sh.commit()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
    }
}