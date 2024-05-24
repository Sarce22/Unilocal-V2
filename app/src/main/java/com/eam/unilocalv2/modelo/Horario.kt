package com.eam.unilocalv2.modelo

class Horario (){

    var key: String = ""
    var id:Int = 0
    var diaSemana:ArrayList<DiaSemana> = ArrayList()
    var horaInicio:Int = 0
    var horaFinal:Int = 0

    constructor(id:Int, diaSemana:ArrayList<DiaSemana>, horaInicio:Int, horaFinal:Int  ):this(){
        this.diaSemana = diaSemana
        this.horaFinal = horaFinal
        this.horaInicio = horaInicio
    }
}