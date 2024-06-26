package com.eam.unilocalv2.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eam.unilocalv2.R
import com.eam.unilocalv2.actividades.BusquedaActivity
import com.eam.unilocalv2.actividades.MainActivity
import com.eam.unilocalv2.adapter.LugarAdapter
import com.eam.unilocalv2.bd.LugaresService
import com.eam.unilocalv2.databinding.FragmentMisLugaresBinding
import com.eam.unilocalv2.modelo.Lugar
import com.google.firebase.auth.FirebaseAuth

class MisLugaresFragment : Fragment() {

    lateinit var binding: FragmentMisLugaresBinding
    private var lista : ArrayList<Lugar> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisLugaresBinding.inflate(inflater, container, false)

        binding.btnVolver.setOnClickListener{ requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, InicioFragment() )
            .addToBackStack(MainActivity.MENU_INICIO).commit()
            MainActivity.binding.menuInferior.menu.getItem(0).isChecked = true}

        binding.btnSearch.setOnClickListener { startActivity(Intent(requireActivity(), BusquedaActivity::class.java)) }

        binding.btnNuevoLugar.setOnClickListener { requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, CrearLugarFragment() )
            .addToBackStack(MainActivity.MENU_CREAR_LUGAR).commit() }

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            val codigoUsuario = user.uid
            LugaresService.listarLugaresPorPropietario(codigoUsuario){lista ->
                val adapter = LugarAdapter(lista, codigoUsuario)
                binding.listaMisLugares.adapter = adapter
                binding.listaMisLugares.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }
        }

        return binding.root
    }

}