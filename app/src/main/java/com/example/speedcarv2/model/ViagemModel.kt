package com.example.speedcarv2.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ViagemModel(
    val regiao: String? = null,
    val origem: String? = null,
    val destino: String? = null,
    val preco: String? = null,
    val tempoMedio: String? = null) {

}