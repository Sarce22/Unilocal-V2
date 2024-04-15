package com.eam.unilocalv2.bd

import com.eam.unilocalv2.modelo.Ciudad

object Ciudades {

    private val lista:ArrayList<Ciudad> = ArrayList()

    init {
        lista.add( Ciudad(1, "Armenia") )
        lista.add( Ciudad(2, "Calarcá") )
        lista.add( Ciudad(3, "Pereira") )
        lista.add( Ciudad(4, "Montenegro") )
        lista.add( Ciudad(5, "Pijao") )
    }

    fun listar():ArrayList<Ciudad>{
        return lista
    }

    fun obtener(id:Int): Ciudad?{
        return lista.firstOrNull { c -> c.id == id }
    }
}