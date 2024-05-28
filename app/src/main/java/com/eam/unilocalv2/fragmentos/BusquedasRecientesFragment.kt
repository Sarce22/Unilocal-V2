package com.eam.unilocalv2.fragmentos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eam.unilocalv2.adapter.BusquedasRecientesAdapter
import com.eam.unilocalv2.bd.UsuariosService
import com.eam.unilocalv2.databinding.FragmentBusquedasRecientesBinding
import com.google.firebase.auth.FirebaseAuth

class BusquedasRecientesFragment : Fragment() {
    lateinit var binding: FragmentBusquedasRecientesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusquedasRecientesBinding.inflate(inflater, container, false)

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            UsuariosService.buscar(user.uid){usuario ->
                if(usuario != null){
                    val lista: ArrayList<String> = usuario.busquedasRecientes
                    val adapter = BusquedasRecientesAdapter(lista)
                    binding.listaBusquedasRecientes.adapter = adapter
                    binding.listaBusquedasRecientes.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
                }
            }
        }

        /*val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("codigo_usuario", -1)
        if(codigoUsuario != -1){
            val usuario = Usuarios.buscar(codigoUsuario)
            if(usuario != null){
                lista = usuario.busquedasRecientes
                val adapter = BusquedasRecientesAdapter(lista)
                binding.listaBusquedasRecientes.adapter = adapter
                binding.listaBusquedasRecientes.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
            }
        }*/

        return binding.root
    }
}