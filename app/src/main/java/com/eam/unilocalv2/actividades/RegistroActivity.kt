package com.eam.unilocalv2.actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eam.unilocalv2.R
import com.eam.unilocalv2.bd.UsuariosService
import com.eam.unilocalv2.databinding.ActivityRegistroBinding
import com.eam.unilocalv2.modelo.Usuario

class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegistrar.setOnClickListener { registrarUsuario() }
    }

    fun registrarUsuario(){
        val nombre = binding.editTextNombre.text.toString()
        val apellidos = binding.editTextApellidos.text.toString()
        val nickname = binding.editTextNickname.text.toString()
        val email = binding.editTextCorreo.text.toString()
        val celular = binding.editTextCelular.text.toString()
        val ciudad = binding.editTextCiudad.text.toString()
        val password = binding.editTextContraseA.text.toString()
        val confirmarpassword = binding.editTextConfirmarContraseA.text.toString()

        if(apellidos.isEmpty()) {
            binding.editTextApellidos.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextApellidos.error = null
        }

        if(celular.isEmpty()) {
            binding.editTextCelular.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextCiudad.error = null
        }

        if(nombre.isEmpty()) {
            binding.editTextNombre.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextNombre.error = null
        }

        if(nickname.isEmpty()) {
            binding.editTextNickname.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextNickname.error = null
        }

        if(email.isEmpty()) {
            binding.editTextCorreo.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextCorreo.error = null
        }

        if(ciudad.isEmpty()) {
            binding.editTextCiudad.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextCiudad.error = null
        }

        if(password.isEmpty()) {
            binding.editTextContraseA.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextContraseA.error = null
        }

        if(confirmarpassword.isEmpty()) {
            binding.editTextConfirmarContraseA.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextConfirmarContraseA.error = null
        }

        if(nombre.isNotEmpty() && nickname.isNotEmpty() && email.isNotEmpty() && ciudad.isNotEmpty() && password.isNotEmpty() && confirmarpassword.isNotEmpty() && apellidos.isNotEmpty() && celular.isNotEmpty()){
            if(password==confirmarpassword){
                binding.editTextConfirmarContraseA.error = null
                if(UsuariosService.agregar(Usuario(0,nombre,apellidos,celular,nickname,ciudad, email,password))){
                    Toast.makeText(this, getString(R.string.registrado_exitosamente), Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                }else{
                    Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                }
            }else{
                binding.editTextConfirmarContraseA.error = getString(R.string.la_contrasena_ingresada_no_coincide)
            }
        }
    }

}