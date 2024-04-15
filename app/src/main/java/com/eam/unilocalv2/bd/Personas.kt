package com.eam.unilocalv2.bd

import com.eam.unilocalv2.modelo.Persona

object Personas {

    fun login(correo:String, password:String): Persona?{
        var respuesta:Persona?

        respuesta = Usuarios.listar().firstOrNull{ u -> u.password == password && u.correo == correo }

        if(respuesta == null){
            respuesta = Moderador.listar().firstOrNull{ u -> u.password == password && u.correo == correo }
        }

        return respuesta
    }

}