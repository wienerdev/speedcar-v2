package com.example.speedcarv2.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserModel(
    val nomeCompleto: String? = null,
    val dtNascimento: String? = null,
    val cpf: String? = null,
    val telefone: String? = null,
    val endereco: String? = null,
    val modeloVeiculo: String? = null,
    val corVeiculo: String? = null,
    val placaVeiculo: String? = null,
    val email: String? = null) {

}