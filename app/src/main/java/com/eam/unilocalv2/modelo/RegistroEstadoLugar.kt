package com.eam.unilocalv2.modelo

class RegistroEstadoLugar() {

    var key: String = ""
    var estadoAnterior: EstadoLugar = EstadoLugar.SIN_REVISAR
    var nuevoEstado: EstadoLugar = EstadoLugar.SIN_REVISAR
    var lugar: Lugar = Lugar()

    constructor(estadoAnterior: EstadoLugar, nuevoEstado: EstadoLugar, lugar: Lugar) : this() {
        this.estadoAnterior = estadoAnterior
        this.nuevoEstado = nuevoEstado
        this.lugar = lugar
    }

}