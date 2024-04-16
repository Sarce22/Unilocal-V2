package com.eam.unilocalv2.fragmentos

import android.content.Context
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
import com.eam.unilocalv2.adapter.BusquedasRecientesAdapter
import com.eam.unilocalv2.bd.Usuarios
import com.eam.unilocalv2.databinding.FragmentBusquedasRecientesBinding

class BusquedasRecientesFragment : Fragment() {
    lateinit var binding: FragmentBusquedasRecientesBinding
    private var lista : ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusquedasRecientesBinding.inflate(inflater, container, false)

        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("codigo_usuario", -1)
        if(codigoUsuario != -1){
            lista = Usuarios.buscar(codigoUsuario)!!.busquedasRecientes
            val adapter = BusquedasRecientesAdapter(lista)
            binding.listaBusquedasRecientes.adapter = adapter
            binding.listaBusquedasRecientes.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
        }

        return binding.root
    }
}