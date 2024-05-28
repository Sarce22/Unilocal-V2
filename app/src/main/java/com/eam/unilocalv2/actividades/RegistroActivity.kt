package com.eam.unilocalv2.actividades

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.eam.unilocalv2.R
import com.eam.unilocalv2.bd.UsuariosService
import com.eam.unilocalv2.databinding.ActivityRegistroBinding
import com.eam.unilocalv2.modelo.Rol
import com.eam.unilocalv2.modelo.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialogo_progreso)
        dialog = builder.create()

        binding.btnRegistrar.setOnClickListener{ registrarUsuario() }
    }

    fun registrarUsuario(){
        val nombre = binding.editTextNombre.text.toString()
        val nickname = binding.editTextNickname.text.toString()
        val email = binding.editTextCorreo.text.toString()
        val ciudad = binding.editTextCiudad.text.toString()
        val password = binding.editTextContraseA.text.toString()
        val celular = binding.editTextCelular.text.toString()
        val cpassword = binding.editTextConfirmarContraseA.text.toString()

        if(nombre.isEmpty()) {
            binding.editTextNombre.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextNombre.error = null
        }

        if(celular.isEmpty()) {
            binding.editTextCelular.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextCelular.error = null
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
            if(password.length >= 6){
                binding.editTextContraseA.error = null
            } else {
                binding.editTextContraseA.error = getString(R.string.seis_caracteres_minimo)
            }
        }

        if(cpassword.isEmpty()) {
            binding.editTextConfirmarContraseA.error = getString(R.string.campo_obligatorio)
        } else {
            binding.editTextConfirmarContraseA.error = null
        }

        if(nombre.isNotEmpty() && celular.isNotEmpty() && nickname.isNotEmpty() && email.isNotEmpty() && ciudad.isNotEmpty() && password.isNotEmpty() && cpassword.isNotEmpty() && password.length >= 6){
            if(password==cpassword){
                setDialog(true)
                Firebase.firestore.collection("usuarios")
                    .whereEqualTo("nickname", nickname)
                    .get()
                    .addOnSuccessListener {
                        if(it.isEmpty){
                            binding.editTextNickname.error = null
                            binding.editTextConfirmarContraseA.error = null
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                    if(it.isSuccessful){
                                        val usuario = FirebaseAuth.getInstance().currentUser
                                        if(usuario != null){
                                            val usuarioRegistro = Usuario(celular, nombre, nickname, ciudad, email, Rol.USUARIO)
                                            usuarioRegistro.key = usuario.uid
                                            Firebase.firestore
                                                .collection("usuarios")
                                                .document(usuarioRegistro.key)
                                                .set(usuarioRegistro)
                                                .addOnSuccessListener {
                                                    setDialog(false)
                                                    Toast.makeText(this, getString(R.string.registrado_exitosamente), Toast.LENGTH_LONG).show()
                                                    startActivity(Intent(this, LoginActivity::class.java))
                                                    finish()
                                                }
                                                .addOnFailureListener {
                                                    setDialog(false)
                                                    Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                                                }
                                        } else {
                                            setDialog(false)
                                            Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                                        }
                                    } else {
                                        setDialog(false)
                                        Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                                    }
                                }
                                .addOnFailureListener {
                                    setDialog(false)
                                    Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                                }
                        } else {
                            setDialog(false)
                            binding.editTextNickname.error = "Nickname en uso"
                            Toast.makeText(this, "Nickname en uso", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener {
                        setDialog(false)
                        Toast.makeText(this, getString(R.string.no_se_pudo_registrar), Toast.LENGTH_LONG).show()
                    }
            }else{
                binding.editTextConfirmarContraseA.error = getString(R.string.la_contrasena_ingresada_no_coincide)
            }
        }
    }

    private fun setDialog(show: Boolean){
        if (show) dialog.show() else dialog.dismiss()
    }

}