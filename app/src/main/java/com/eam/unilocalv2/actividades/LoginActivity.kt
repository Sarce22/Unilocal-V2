package com.eam.unilocalv2.actividades

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eam.unilocalv2.bd.Personas
import com.eam.unilocalv2.databinding.ActivityLoginBinding
import com.eam.unilocalv2.modelo.Moderador
import com.eam.unilocalv2.modelo.Usuario
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.eam.unilocalv2.R
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp = getSharedPreferences("session", MODE_PRIVATE)
        val correo = sp.getString("correo_usuario", "")
        val tipo = sp.getString("tipo_usuario", "")

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (correo!!.isNotEmpty() && tipo!!.isNotEmpty()) {
            when (tipo) {
                "usuario" -> {
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    })
                }

                "moderador" -> {
                    startActivity(Intent(this, ModMainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    })
                }
            }
            this.finish()
        } else {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.textViewRegisterHere.setOnClickListener { irRegistro() }
            binding.buttonLogin.setOnClickListener { login() }
            binding.textForgotPassword.setOnClickListener { irRecuperarContraseña() }
        }
    }


    fun login(){
        val correo = binding.editTextEmail.text
        val password = binding.editTextPassword.text

        if(correo.isEmpty()) {
            binding.editTextEmail.error = "Es obligatorio"
        } else {
            binding.editTextEmail.error = null
        }

        if(password.isEmpty()) {
            binding.editTextPassword.error = "Es obligatorio"
        } else {
            binding.editTextPassword.error = null
        }

        if(correo.isNotEmpty() && password.isNotEmpty()){
            val persona = Personas.login(correo.toString(), password.toString())
            if(persona != null){

                val tipo = if(persona is Usuario) "usuario" else "moderador"
                val sp = this.getSharedPreferences("sesion", Context.MODE_PRIVATE).edit()
                sp.putInt("codigo_usuario", persona.id)
                sp.putString("correo_usuario", persona.correo)
                sp.putString("tipo_usuario", tipo)
                sp.commit()

                when(persona){
                    //Usuario
                    is Usuario -> startActivity(Intent(this, MainActivity::class.java))
                    //Moderador
                    is Moderador -> startActivity(Intent(this, ModMainActivity::class.java))
                }
            } else {
                Snackbar.make(window.decorView.rootView, getString(R.string.datos_incorrectos), BaseTransientBottomBar.LENGTH_SHORT).show()
            }
        }
    }


    private fun irRegistro() {
        startActivity(Intent(this, RegistroActivity::class.java))
    }

    private fun irRecuperarContraseña() {
        startActivity(Intent(this, OlvidoContrasenaActivity::class.java))
    }

}