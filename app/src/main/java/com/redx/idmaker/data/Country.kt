package com.redx.idmaker.data

data class Country(
    val code: String,
    val name: String,
    val flag: String,
    val idTypes: List<IdType>
)
