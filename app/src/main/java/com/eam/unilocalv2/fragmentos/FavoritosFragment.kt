package com.eam.unilocalv2.fragmentos

import android.content.Context
import android.content.Intent
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
import com.eam.unilocalv2.actividades.BusquedaActivity
import com.eam.unilocalv2.actividades.MainActivity
import com.eam.unilocalv2.adapter.LugarAdapter
import com.eam.unilocalv2.bd.Lugares
import com.eam.unilocalv2.databinding.FragmentFavoritosBinding
import com.eam.unilocalv2.modelo.Lugar

class FavoritosFragment : Fragment() {

    lateinit var binding: FragmentFavoritosBinding
    private var lista : ArrayList<Lugar> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)

        binding.btnVolver.setOnClickListener{ requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, InicioFragment() )
            .addToBackStack(MainActivity.MENU_INICIO).commit()
            MainActivity.binding.menuInferior.menu.getItem(0).isChecked = true}

        binding.btnSearch.setOnClickListener { startActivity(Intent(requireActivity(), BusquedaActivity::class.java)) }

        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sp.getInt("codigo_usuario", -1)
        if(codigoUsuario != -1){
            lista = Lugares.obtenerFavoritos(codigoUsuario)

            val adapter = LugarAdapter(lista, codigoUsuario)
            binding.listaMisFavoritos.adapter = adapter
            binding.listaMisFavoritos.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

        return binding.root
    }
}