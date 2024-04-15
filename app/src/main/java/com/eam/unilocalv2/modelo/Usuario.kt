package com.eam.unilocalv2.modelo

class Usuario (id: Int, nombre: String, apellidos:String, celular:String,
               var nickname: String, var ciudad: String, correo: String,password: String):
    Persona(id,nombre,apellidos,celular,correo,password) {

    var busquedasRecientes: ArrayList<String> = ArrayList()
    var lugaresFavoritos:ArrayList<String> = ArrayList()

}