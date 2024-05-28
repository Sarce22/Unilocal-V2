package com.eam.unilocalv2.actividades

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.eam.unilocalv2.databinding.ActivityLoginBinding
import com.eam.unilocalv2.modelo.Usuario
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.eam.unilocalv2.R
import com.eam.unilocalv2.modelo.Rol
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocationPermission()

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null) {
            hacerRedireccion(user)
        } else {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.buttonLogin.setOnClickListener{ login() }
            binding.textForgotPassword.setOnClickListener{ irAOlvidoContrasenia() }
            binding.textViewRegisterHere.setOnClickListener { irARegsitro() }
        }

        /*val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val correo = sp.getString("correo_usuario", "")
        val tipo = sp.getString("tipo_usuario", "")
        if(correo!!.isNotEmpty() && tipo!!.isNotEmpty()){
            when(tipo){
                "usuario" -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
                "moderador" -> {
                    val intent = Intent(this, ModMainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
            this.finish()
        } else {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnIniciarSesion.setOnClickListener{ login() }
            binding.txtOlvidoContrasenia.setOnClickListener{ irAOlvidoContrasenia() }
            binding.txtNoCuenta.setOnClickListener { irARegsitro() }
        }*/
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

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(correo.toString(), password.toString())
                .addOnCompleteListener { it ->
                    if(it.isSuccessful){

                        val user = FirebaseAuth.getInstance().currentUser

                        if(user != null){
                            hacerRedireccion(user)
                        }
                    }else{
                        Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
                }

            /*val persona = Personas.login(correo.toString(), password.toString())
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
                Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
            }*/
        }
    }

    fun hacerRedireccion(user: FirebaseUser){
        Firebase.firestore
            .collection("usuarios")
            .document(user.uid)
            .get()
            .addOnSuccessListener {u ->
                val usuario = u.toObject(Usuario::class.java)
                if(usuario != null){
                    val rol = usuario.rol
                    var intent: Intent = Intent()
                    if(rol == Rol.USUARIO){
                        intent = Intent(this, MainActivity::class.java)
                    } else if(rol == Rol.MODERADOR){
                        intent = Intent(this, ModMainActivity::class.java)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                } else {
                    FirebaseAuth.getInstance().signOut()
                    binding = ActivityLoginBinding.inflate(layoutInflater)
                    setContentView(binding.root)

                    binding.buttonLogin.setOnClickListener{ login() }
                    binding.textForgotPassword.setOnClickListener{ irAOlvidoContrasenia() }
                    binding.textViewRegisterHere.setOnClickListener { irARegsitro() }
                }
            }
            .addOnFailureListener {
                FirebaseAuth.getInstance().signOut()
                binding = ActivityLoginBinding.inflate(layoutInflater)
                setContentView(binding.root)

                binding.buttonLogin.setOnClickListener{ login() }
                binding.textForgotPassword.setOnClickListener{ irAOlvidoContrasenia() }
                binding.textViewRegisterHere.setOnClickListener { irARegsitro() }
            }
    }

    fun irARegsitro(){
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
    fun irAOlvidoContrasenia(){
        val intent = Intent(this, OlvidoContrasenaActivity::class.java)
        startActivity(intent)

    }

    private fun getLocationPermission() {
        if (!(ContextCompat.checkSelfPermission(baseContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
        }
    }

}