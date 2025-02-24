package com.example.navegacao1.model.dados

import com.google.firebase.firestore.DocumentId

data class Usuario(
    @DocumentId var id: String? = null,
    val nome: String = "",
    val senha: String = ""
)
