package com.example.lab5_api

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<Type>
)

data class Type(
    val name: String
)
