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
import com.eam.unilocalv2.bd.LugaresService
import com.eam.unilocalv2.databinding.FragmentImagenesLugarBinding


class ImagenesLugarFragment : Fragment() {

    lateinit var binding: FragmentImagenesLugarBinding
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
        binding = FragmentImagenesLugarBinding.inflate(inflater, container, false)

        LugaresService.obtener(codigoLugar){lugar ->
            if(lugar != null){
                if(lugar.imagenes.isNotEmpty()){
                    val adapter = ImagenesAdapter(lugar.imagenes)
                    binding.listaImagenes.adapter = adapter
                    binding.listaImagenes.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }

        return binding.root
    }

    companion object{

        fun newInstance(codigoLugar:String):ImagenesLugarFragment{

            val args = Bundle()
            args.putString("id_lugar", codigoLugar)
            val fragmento = ImagenesLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}