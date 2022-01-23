package com.preko.speedcarv2.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ViagemModel(
    val regiao: String? = null,
    val origem: String? = null,
    val destino: String? = null,
    val preco: String? = null,
    val tempoMedio: String? = null,
    val nomePassageiro: String? = null,
    val cpfPassageiro: String? = null,
    val telefonePassageiro: String? = null) {

}