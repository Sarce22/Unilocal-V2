package com.eam.unilocalv2.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eam.unilocalv2.R
import com.eam.unilocalv2.adapter.ComentariosAdapter
import com.eam.unilocalv2.bd.LugaresService
import com.eam.unilocalv2.databinding.FragmentComentariosGestionarLugarBinding
import com.google.firebase.auth.FirebaseAuth

class ComentariosGestionarLugarFragment : Fragment() {

    lateinit var binding: FragmentComentariosGestionarLugarBinding
    var codigoLugar: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            codigoLugar = requireArguments().getString("id_lugar", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComentariosGestionarLugarBinding.inflate(inflater, container, false)

        LugaresService.obtener(codigoLugar){lug ->
            val lugar = lug
            if(lugar != null){
                //Cargar info
                LugaresService.listarComentarios(codigoLugar){lista ->
                    val cal: Int = lugar.obtenerCalificacionPromedio(lista)
                    binding.calificacionPromedio.text = cal.toString()

                    if(cal != 0){
                        for (i in 0 until cal){
                            (binding.listaEstrellas[i] as TextView).setTextColor(ContextCompat.getColor(binding.listaEstrellas.context, R.color.yellow))
                        }
                    }
                    val user = FirebaseAuth.getInstance().currentUser
                    if(user != null){
                        val adapter = ComentariosAdapter(lista, user.uid, codigoLugar)
                        binding.listaComentarios.adapter = adapter
                        binding.listaComentarios.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)
                    }
                    binding.cantComentarios.text = "(${lista.size})"
                }
            }
        }

        return binding.root
    }

    companion object{
        fun newInstance(codigoLugar:String):ComentariosGestionarLugarFragment{
            val args = Bundle()
            args.putString("id_lugar", codigoLugar)
            val fragmento = ComentariosGestionarLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

}