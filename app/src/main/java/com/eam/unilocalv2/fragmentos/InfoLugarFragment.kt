package com.eam.unilocalv2.fragmentos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.eam.unilocalv2.R
import com.eam.unilocalv2.adapter.ViewPagerAdapterImagenes
import com.eam.unilocalv2.bd.CategoriasService
import com.eam.unilocalv2.bd.LugaresService
import com.eam.unilocalv2.databinding.FragmentInfoLugarBinding
import com.eam.unilocalv2.modelo.DiaSemana
import com.eam.unilocalv2.modelo.Lugar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Timer
import java.util.TimerTask

class InfoLugarFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentInfoLugarBinding
    var codigoLugar: String = ""
    private var lugar: Lugar? = null

    private var currentPage = 0

    lateinit var gMap: GoogleMap

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
        binding = FragmentInfoLugarBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapa_direccion_lugar) as SupportMapFragment
        mapFragment.getMapAsync(this)

        LugaresService.obtener(codigoLugar){lug ->
            lugar = lug
            if(lugar != null){
                //Cargar info layout
                cargarInformacion(lugar!!)
            }
        }

        return binding.root
    }

    fun cargarInformacion(lugar: Lugar){

        //Cargar imágenes
        if(lugar.imagenes.isNotEmpty()){
            val adapter = ViewPagerAdapterImagenes(requireActivity(), lugar.imagenes)
            binding.listaImgs.adapter = adapter

            val handler = Handler(Looper.getMainLooper())
            val update = Runnable {
                val itemCount = adapter.itemCount
                if (currentPage == itemCount) {
                    currentPage = 0
                }
                binding.listaImgs.setCurrentItem(currentPage++, true)
            }
            val timer = Timer() // Esto creará un nuevo Timer
            timer.schedule(object : TimerTask() { // tarea a ejecutar
                override fun run() {
                    handler.post(update)
                }
            }, DELAY_MS, PERIOD_MS)
        }

        //Cargar dirección y mapa
        binding.direccionLugar.text = lugar.direccion
        val pos = LatLng(lugar.posicion.lat, lugar.posicion.lng)
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 18f))
        gMap.addMarker( MarkerOptions().position(pos).title(lugar.nombre))

        binding.detalleLugar.text = lugar.descripcion

        for (horario in lugar.horarios){
            for(dia in horario.diaSemana){
                if(dia == DiaSemana.LUNES){
                    binding.horarioLunes.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else if(dia == DiaSemana.MARTES){
                    binding.horarioMartes.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else if(dia == DiaSemana.MIERCOLES){
                    binding.horarioMiercoles.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else if (dia == DiaSemana.JUEVES){
                    binding.horarioJueves.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else if (dia == DiaSemana.VIERNES){
                    binding.horarioViernes.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else if (dia == DiaSemana.SABADO){
                    binding.horarioSabado.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                } else  if (dia == DiaSemana.DOMINGO){
                    binding.horarioDomingo.text = "${horario.horaInicio}:00 - ${horario.horaFinal}:00"
                }
            }
        }

        var telefonos = ""
        if (lugar.telefonos.isNotEmpty()) {
            for (i in 0 until lugar.telefonos.size) {
                if (i < lugar.telefonos.size - 1) {
                    telefonos += "${lugar.telefonos.get(i)}\n"
                } else {
                    telefonos += "${lugar.telefonos.get(i)}"
                }
            }
        } else {
            telefonos = getString(R.string.no_tiene_telefonos_contac)
        }
        binding.contactoLugar.text = telefonos

        binding.buttonCall.setOnClickListener {
            Log.d("info_llamada", "El número de teléfono es: $telefonos")
            if (lugar.telefonos.isNotEmpty()) {
                iniciarLlamada(lugar.telefonos[0])
            }
        }

        CategoriasService.obtener(lugar.keyCategoria){categoria ->
            if(categoria != null){
                binding.iconoCategoria.text = categoria.icono
                binding.categoriaLugar.text = categoria.nombre
            }
        }

        //colores estado
        val abierto = lugar.estaAbierto()
        if(abierto){
            binding.estado.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            binding.estado.text = getString(R.string.txt_abierto)
        }else{
            binding.estado.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            binding.estado.text = getString(R.string.cerrado)
        }

    }


    private fun iniciarLlamada(numeroTelefono: String) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            Log.d("TAG", "El número de teléfono es: $numeroTelefono")

            intent.data = Uri.parse("tel:$numeroTelefono")
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                lugar?.telefonos?.get(0)?.let { iniciarLlamada(it) }
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(requireContext(), "Por favor, habilite el permiso para realizar llamadas", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Habilite el permiso de llamada en la configuración de la aplicación", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object{

        private const val DELAY_MS: Long = 0
        private const val PERIOD_MS: Long = 3500
        private const val REQUEST_CALL_PERMISSION = 0
        fun newInstance(codigoLugar:String):InfoLugarFragment{

            val args = Bundle()
            args.putString("id_lugar", codigoLugar)
            val fragmento = InfoLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.uiSettings.isRotateGesturesEnabled = false
        gMap.uiSettings.isScrollGesturesEnabled = false
        gMap.uiSettings.isZoomGesturesEnabled = true
        gMap.uiSettings.isZoomControlsEnabled = true
    }
}