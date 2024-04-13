package com.eam.unilocalv2.modelo

import java.util.Date

class Lugar (var nombre:String, var descripcion:String, var idCreador:Int, var estado:EstadoLugar,
             var idCategoria:Int, var direccion:String, var posicion: Posicion, var idCiudad:Int){

    var id:Int = 0
    var imagenes:ArrayList<String> = ArrayList()
    var telefonos:ArrayList<String> = ArrayList()
    var fecha: Date = Date()
    var horarios:ArrayList<Horario> = ArrayList()
}