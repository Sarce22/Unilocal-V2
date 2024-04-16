package com.eam.unilocalv2.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.eam.unilocalv2.R
import com.eam.unilocalv2.actividades.BusquedaActivity
import com.eam.unilocalv2.actividades.MainActivity
import com.eam.unilocalv2.bd.Categorias
import com.eam.unilocalv2.bd.Ciudades
import com.eam.unilocalv2.bd.Lugares
import com.eam.unilocalv2.databinding.ActivityCrearLugarFragmentBinding
import com.eam.unilocalv2.databinding.FragmentCrearLugarBinding
import com.eam.unilocalv2.modelo.Categoria
import com.eam.unilocalv2.modelo.Ciudad
import com.eam.unilocalv2.modelo.DiaSemana
import com.eam.unilocalv2.modelo.EstadoLugar
import com.eam.unilocalv2.modelo.Horario
import com.eam.unilocalv2.modelo.Lugar
import com.eam.unilocalv2.modelo.Posicion

class CrearLugarFragment : Fragment() {

    lateinit var binding: FragmentCrearLugarBinding
    lateinit var ciudades:ArrayList<Ciudad>
    lateinit var categorias:ArrayList<Categoria>




    private var posicion: Posicion? = null

    var posCiudad:Int = -1
    var posCategoria:Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrearLugarBinding.inflate(inflater, container, false)

        ciudades = Ciudades.listar()
        categorias = Categorias.listar()

        cargarHorarios()
        cargarCiudades()
        cargarCategorias()

        binding.btnCrearLugar.setOnClickListener { crearLugar() }

        binding.btnVolver.setOnClickListener { requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, InicioFragment() )
            .addToBackStack(MainActivity.MENU_INICIO).commit()
            MainActivity.binding.menuInferior.menu.getItem(0).isChecked = true}

        binding.btnBuscar.setOnClickListener { startActivity(Intent(requireActivity(), BusquedaActivity::class.java)) }


        return binding.root
    }

    fun cargarHorarios(){
        val lista: ArrayList<String> = ArrayList()
        lista.add(getString(R.string.txt_seleccione))
        for (i in 1..24){
            lista.add(i.toString())
        }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.inicioLunes.adapter = adapter
        binding.finalLunes.adapter = adapter
        binding.inicioMartes.adapter = adapter
        binding.finalMartes.adapter = adapter
        binding.inicioMiercoles.adapter = adapter
        binding.finalMiercoles.adapter = adapter
        binding.inicioJueves.adapter = adapter
        binding.finalJueves.adapter = adapter
        binding.inicioViernes.adapter = adapter
        binding.finalViernes.adapter = adapter
        binding.inicioSabado.adapter = adapter
        binding.finalSabado.adapter = adapter
        binding.inicioDomingo.adapter = adapter
        binding.finalDomingo.adapter = adapter
    }

    fun cargarCiudades(){
        var lista = ciudades.map { c -> c.nombre }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ciudadLugar.adapter = adapter

        binding.ciudadLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                posCiudad = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    fun cargarCategorias(){
        var lista = categorias.map { c -> c.nombre }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaLugar.adapter = adapter

        binding.categoriaLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                posCategoria = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    fun crearLugar(){
        Log.e("CrearLugarFragment", binding.ciudadLugar.selectedItem.toString())
        val nombre = binding.nombreLugar.text.toString()
        val descripcion = binding.descripcionLugar.text.toString()
        val telefono = binding.telefonoLugar.text.toString()
        val direccion = binding.direccionLugar.text.toString()
        val idCiudad = ciudades[posCiudad].id
        val idCategoria = categorias[posCategoria].id

        if( nombre.isEmpty() ){
            binding.nombreLayout.error = getString(R.string.campo_obligatorio)
        }else{
            binding.nombreLayout.error = null
        }

        if( descripcion.isEmpty() ){
            binding.descripcionLayout.error = getString(R.string.campo_obligatorio)
        }else{
            binding.descripcionLayout.error = null
        }

        if( direccion.isEmpty() ){
            binding.direccionLayout.error = getString(R.string.campo_obligatorio)
        }

        if( telefono.isEmpty() ){
            binding.telefonoLayout.error = getString(R.string.campo_obligatorio)
        }else{
            binding.telefonoLayout.error = null
        }

        if(nombre.isNotEmpty() && descripcion.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && idCiudad != -1 && idCategoria != -1 && posicion != null) {

            val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
            val codigoUsuario = sp.getInt("codigo_usuario", -1)
            if(codigoUsuario != -1){
                val nuevoLugar = Lugar(nombre, descripcion, codigoUsuario, EstadoLugar.SIN_REVISAR, idCategoria, direccion,posicion!! , idCiudad)

                val telefonos: ArrayList<String> = ArrayList()
                telefonos.add(telefono)
                nuevoLugar.telefonos = telefonos

                if(binding.inicioLunes.selectedItemPosition!=0 && binding.finalLunes.selectedItemPosition != 0){
                    val horaInicio: Int = binding.inicioLunes.selectedItem.toString().toInt()
                    val horaFinal: Int = binding.finalLunes.selectedItem.toString().toInt()
                    if(horaInicio < horaFinal){
                        val dia: ArrayList<DiaSemana> = ArrayList()
                        dia.add(DiaSemana.LUNES)
                        val horario = Horario(0, dia, horaInicio, horaFinal)
                        nuevoLugar.horarios.add(horario)
                    }else{
                        Toast.makeText(requireActivity(), getString(R.string.horario_de_lunes_no_valido), Toast.LENGTH_SHORT).show()
                    }
                }

                if(binding.inicioMartes.selectedItemPosition!=0 && binding.finalMartes.selectedItemPosition != 0){
                    val horaInicio: Int = binding.inicioMartes.selectedItem.toString().toInt()
                    val horaFinal: Int = binding.finalMartes.selectedItem.toString().toInt()
                    if(horaInicio < horaFinal){
                        val dia: ArrayList<DiaSemana> = ArrayList()
                        dia.add(DiaSemana.MARTES)
                        val horario = Horario(0, dia, horaInicio, horaFinal)
                        nuevoLugar.horarios.add(horario)
                    }else{
                        Toast.makeText(requireActivity(), getString(R.string.horario_de_martes_no_valido), Toast.LENGTH_SHORT).show()
                    }
                }

                if(binding.inicioMiercoles.selectedItemPosition!=0 && binding.finalMiercoles.selectedItemPosition != 0){
                    val horaInicio: Int = binding.inicioMiercoles.selectedItem.toString().toInt()
                    val horaFinal: Int = binding.finalMiercoles.selectedItem.toString().toInt()
                    if(horaInicio < horaFinal){
                        val dia: ArrayList<DiaSemana> = ArrayList()
                        dia.add(DiaSemana.MIERCOLES)
                        val horario = Horario(0, dia, horaInicio, horaFinal)
                        nuevoLugar.horarios.add(horario)
                    }else{
                        Toast.makeText(requireActivity(), getString(R.string.horario_de_miercoles_no_valido), Toast.LENGTH_SHORT).show()
                    }
                }

                if(binding.inicioJueves.selectedItemPosition!=0 && binding.finalJueves.selectedItemPosition != 0){
                    val horaInicio: Int = binding.inicioJueves.selectedItem.toString().toInt()
                    val horaFinal: Int = binding.finalJueves.selectedItem.toString().toInt()
                    if(horaInicio < horaFinal){
                        val dia: ArrayList<DiaSemana> = ArrayList()
                        dia.add(DiaSemana.JUEVES)
                        val horario = Horario(0, dia, horaInicio, horaFinal)
                        nuevoLugar.horarios.add(horario)
                    }else{
                        Toast.makeText(requireActivity(), getString(R.string.horario_de_jueves_no_valido), Toast.LENGTH_SHORT).show()
                    }
                }

                if(binding.inicioViernes.selectedItemPosition!=0 && binding.finalViernes.selectedItemPosition != 0){
                    val horaInicio: Int = binding.inicioViernes.selectedItem.toString().toInt()
                    val horaFinal: Int = binding.finalViernes.selectedItem.toString().toInt()
                    if(horaInicio < horaFinal){
                        val dia: ArrayList<DiaSemana> = ArrayList()
                        dia.add(DiaSemana.VIERNES)
                        val horario = Horario(0, dia, horaInicio, horaFinal)
                        nuevoLugar.horarios.add(horario)
                    }else{
                        Toast.makeText(requireActivity(), getString(R.string.horario_de_viernes_no_valido), Toast.LENGTH_SHORT).show()
                    }
                }

                if(binding.inicioSabado.selectedItemPosition!=0 && binding.finalSabado.selectedItemPosition != 0){
                    val horaInicio: Int = binding.inicioSabado.selectedItem.toString().toInt()
                    val horaFinal: Int = binding.finalSabado.selectedItem.toString().toInt()
                    if(horaInicio < horaFinal){
                        val dia: ArrayList<DiaSemana> = ArrayList()
                        dia.add(DiaSemana.SABADO)
                        val horario = Horario(0, dia, horaInicio, horaFinal)
                        nuevoLugar.horarios.add(horario)
                    }else{
                        Toast.makeText(requireActivity(), getString(R.string.horario_de_sabado_no_valido), Toast.LENGTH_SHORT).show()
                    }
                }

                if(binding.inicioDomingo.selectedItemPosition!=0 && binding.finalDomingo.selectedItemPosition != 0){
                    val horaInicio: Int = binding.inicioDomingo.selectedItem.toString().toInt()
                    val horaFinal: Int = binding.finalDomingo.selectedItem.toString().toInt()
                    if(horaInicio < horaFinal){
                        val dia: ArrayList<DiaSemana> = ArrayList()
                        dia.add(DiaSemana.DOMINGO)
                        val horario = Horario(0, dia, horaInicio, horaFinal)
                        nuevoLugar.horarios.add(horario)
                    }else{
                        Toast.makeText(requireActivity(), getString(R.string.horario_de_domingo_no_valido), Toast.LENGTH_SHORT).show()
                    }
                }


                Lugares.crear(nuevoLugar)

                Toast.makeText(requireActivity(), getString(R.string.lugar_creado_rev_mod), Toast.LENGTH_LONG).show()

                requireActivity().supportFragmentManager.beginTransaction().replace( R.id.contenido_principal, MisLugaresFragment() )
                    .addToBackStack(MainActivity.MENU_MIS_LUGARES).commit()
            }

        }

    }



}