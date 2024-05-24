package com.eam.unilocalv2.modelo

class Posicion () {

    var lat:Double = 0.0
    var lng:Double = 0.0

    constructor(lat:Double, lng:Double):this(){
        this.lat = lat
        this.lng = lng
    }
    override fun toString(): String {
        return "Posicion(lat=$lat, long=$lng)"
    }
}