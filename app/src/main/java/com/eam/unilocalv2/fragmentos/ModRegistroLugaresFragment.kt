package com.eam.unilocalv2.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eam.unilocalv2.adapter.LugarAdapter
import com.eam.unilocalv2.adapter.RegistroLugaresModAdapter
import com.eam.unilocalv2.bd.LugaresService
import com.eam.unilocalv2.databinding.FragmentModRegistroLugaresBinding
import com.eam.unilocalv2.databinding.FragmentResultadoBusquedaBinding
import com.eam.unilocalv2.modelo.Lugar
import com.eam.unilocalv2.modelo.RegistroEstadoLugar
import com.google.firebase.auth.FirebaseAuth

class ModRegistroLugaresFragment : Fragment() {

    lateinit var binding: FragmentModRegistroLugaresBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModRegistroLugaresBinding.inflate(inflater, container, false)

        LugaresService.obtenerRegistros { lista ->
            val adapter = RegistroLugaresModAdapter(lista)
            binding.registroLugares.adapter = adapter
            binding.registroLugares.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
        }

        return binding.root
    }

}