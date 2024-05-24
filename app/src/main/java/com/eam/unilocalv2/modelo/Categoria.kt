package com.eam.unilocalv2.modelo

class Categoria (){
    var key:String = ""
    var nombre:String = ""
    var icono:String = ""

    constructor(nombre:String, icono:String):this(){
        this.nombre = nombre
        this.icono = icono
    }

    override fun toString(): String {
        return "Categoria(key=$key, nombre='$nombre', icono='$icono')"
    }

}