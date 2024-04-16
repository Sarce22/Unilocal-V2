package com.eam.unilocalv2.fragmentos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eam.unilocalv2.R
import com.eam.unilocalv2.adapter.ComentariosAdapter
import com.eam.unilocalv2.bd.Comentarios
import com.eam.unilocalv2.bd.Lugares
import com.eam.unilocalv2.databinding.FragmentComentariosGestionarLugarBinding
import com.eam.unilocalv2.modelo.Comentario

class ComentariosGestionarLugarFragment : Fragment() {

    lateinit var binding: FragmentComentariosGestionarLugarBinding
    private var lista : ArrayList<Comentario> = ArrayList()
    var codigoLugar: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            codigoLugar = requireArguments().getInt("id_lugar")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComentariosGestionarLugarBinding.inflate(inflater, container, false)

        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("codigo_usuario", -1)

        val lugar = Lugares.obtener(codigoLugar)

        if(lugar != null){
            //Cargar info
            val cal: Int = lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))
            binding.calificacionPromedio.text = cal.toString()

            if(cal != 0){
                for (i in 0 until cal){
                    (binding.listaEstrellas[i] as TextView).setTextColor(ContextCompat.getColor(binding.listaEstrellas.context, R.color.yellow))
                }
            }

            lista = Comentarios.listar(codigoLugar)
            val adapter = ComentariosAdapter(lista, codigoUsuario)
            binding.listaComentarios.adapter = adapter
            binding.listaComentarios.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true)

            binding.cantComentarios.text = "(${lista.size})"
        }

        return binding.root
    }

    companion object{
        fun newInstance(codigoLugar:Int):ComentariosGestionarLugarFragment{
            val args = Bundle()
            args.putInt("id_lugar", codigoLugar)
            val fragmento = ComentariosGestionarLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

}