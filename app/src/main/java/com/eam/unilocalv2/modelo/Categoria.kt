package com.eam.unilocalv2.modelo

class Categoria (var id:Int, var nombre:String, var icono: String){
    override fun toString(): String {
        return nombre
    }
}