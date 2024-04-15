package com.eam.unilocalv2.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eam.unilocalv2.adapter.RegistroLugaresModAdapter
import com.eam.unilocalv2.bd.Lugares
import com.eam.unilocalv2.databinding.FragmentModRegistroLugaresBinding
import com.eam.unilocalv2.modelo.RegistroEstadoLugar

class ModRegistroLugaresFragment : Fragment() {

    lateinit var binding: FragmentModRegistroLugaresBinding
    private var lista : ArrayList<RegistroEstadoLugar> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModRegistroLugaresBinding.inflate(inflater, container, false)

        lista = Lugares.obtenerRegistros()
        val adapter = RegistroLugaresModAdapter(lista)
        binding.registroLugares.adapter = adapter
        binding.registroLugares.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)

        return binding.root
    }

}