package com.eam.unilocalv2.modelo

class RegistroEstadoLugar (var estadoAnterior: EstadoLugar = EstadoLugar.SIN_REVISAR,
                           var nuevoEstado: EstadoLugar = EstadoLugar.SIN_REVISAR,
                           var lugar: Lugar){
}