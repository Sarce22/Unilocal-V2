package com.eam.unilocalv2.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eam.unilocalv2.R
import com.eam.unilocalv2.adapter.LugaresModAdapter
import com.eam.unilocalv2.bd.Lugares
import com.eam.unilocalv2.databinding.FragmentModLugaresBinding
import com.eam.unilocalv2.modelo.EstadoLugar
import com.eam.unilocalv2.modelo.Lugar


class ModLugaresFragment : Fragment() {

    lateinit var binding: FragmentModLugaresBinding
    private var lista : ArrayList<Lugar> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModLugaresBinding.inflate(inflater, container, false)

        lista = Lugares.listarPorEstado(EstadoLugar.SIN_REVISAR)
        val adapter = LugaresModAdapter(lista)
        binding.listaLugaresSinRevision.adapter = adapter
        binding.listaLugaresSinRevision.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)

        return binding.root
    }

}