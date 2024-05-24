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

class ModRegistroLugaresFragment : Fragment() {

    lateinit var binding: FragmentResultadoBusquedaBinding
    private var busqueda: String? = null
    private var lista : ArrayList<Lugar> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            busqueda = requireArguments().getString("busqueda")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultadoBusquedaBinding.inflate(inflater, container, false)

        val user = FirebaseAuth.getInstance().currentUser
        lista = ArrayList()
        if(busqueda != null && busqueda!!.isNotEmpty()){
            if(busqueda!!.contains("categoria/")){
                val keyCategoria: String = busqueda!!.substringAfter("/")
                LugaresService.listarPorCategoria(keyCategoria){lugares ->
                    lista = lugares
                    val adapter: LugarAdapter
                    adapter = if(user != null){
                        val codigoUsuario = user.uid
                        LugarAdapter(lista, codigoUsuario)
                    } else {
                        LugarAdapter(lista)
                    }
                    binding.listaLugares.adapter = adapter
                    binding.listaLugares.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                }
            } else {
                LugaresService.buscarPorNombre(busqueda!!){lugares ->
                    lista = lugares
                    val adapter: LugarAdapter
                    adapter = if(user != null){
                        val codigoUsuario = user.uid
                        LugarAdapter(lista, codigoUsuario)
                    } else {
                        LugarAdapter(lista)
                    }
                    binding.listaLugares.adapter = adapter
                    binding.listaLugares.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }

        return binding.root
    }

    companion object{
        fun newInstance(busqueda:String):ResultadoBusquedaFragment{
            val args = Bundle()
            args.putString("busqueda", busqueda)
            val fragmento = ResultadoBusquedaFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

}