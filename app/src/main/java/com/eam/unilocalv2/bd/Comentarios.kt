package com.eam.unilocalv2.bd

import com.eam.unilocalv2.modelo.Comentario

object Comentarios {

    private val lista:ArrayList<Comentario> = ArrayList()


    fun listar(idLugar:Int):ArrayList<Comentario>{
        return lista.filter { c -> c.idLugar == idLugar }.toCollection(ArrayList())
    }

    fun crear(comentario: Comentario){
        comentario.id = lista.size + 1
        lista.add( comentario )
    }

    fun comentado(idLugar:Int, idUsuario:Int): Boolean{
        var list = listar(idLugar)
        for (coment in list){
            if(coment.idUsuario == idUsuario){
                return true
            }
        }
        return false
    }

    fun eliminarComentario(comentario: Comentario){
        lista.remove(comentario)
    }
}