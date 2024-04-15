package com.eam.unilocalv2.bd

import com.eam.unilocalv2.modelo.Moderador

object Moderador {

    private val lista:ArrayList<Moderador> = ArrayList()

    init {
        lista.add( Moderador(1, "Moderador1", "mode1@email.com", "3218347292","mod1@","12312"))
        lista.add( Moderador(2, "Moderador2", "mode2@email.com", "3020458294","mod2@","1233"))
    }

    fun listar():ArrayList<Moderador>{
        return lista
    }

    fun obtener(id:Int): Moderador?{
        return lista.firstOrNull { a -> a.id == id }
    }

}