package com.eam.unilocalv2.modelo

class Ciudad () {

    var key:String = ""
    var nombre:String = ""

    constructor(nombre:String):this(){
        this.nombre = nombre
    }

    override fun toString(): String {
        return "Ciudad(id=$key, nombre='$nombre')"
    }
}