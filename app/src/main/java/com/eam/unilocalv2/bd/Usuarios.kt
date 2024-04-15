package com.eam.unilocalv2.bd

import com.eam.unilocalv2.modelo.Usuario

object Usuarios {

    private val usuarios: ArrayList<Usuario> = ArrayList()

    init {
        usuarios.add(Usuario(1,"Sebastian","Arce","3122477439","Sarce", "Montenegro","sebas@","12345"))
        usuarios.add(Usuario(2,"Mariana","Portela","3106204594","mariana","Armenia", "mari@", "1234"))
        usuarios.add(Usuario(3,"Juan","Acosta","3182458203","pechu", "Calarca","juan@", "4321"))
    }

    fun listar(): ArrayList<Usuario> {
        return usuarios
    }

    fun agregar(usuario: Usuario): Boolean {
        val correo = usuarios.firstOrNull{u -> u.correo == usuario.correo}
        val nickname = usuarios.firstOrNull{u -> u.nickname == usuario.nickname}
        if(correo == null && nickname == null && usuario.id == 0){
            val id = usuarios.get(usuarios.size-1).id + 1
            usuario.id = id
            usuarios.add(usuario)
            return true
        }
        return false
    }

    fun buscar(id:Int): Usuario?{
        return usuarios.firstOrNull { u -> u.id == id }
    }

}