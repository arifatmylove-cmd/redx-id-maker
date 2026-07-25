package com.redx.idmaker.data

enum class IdCategory { NATIONAL_ID, DRIVING_LICENCE, PASSPORT, VOTER_ID, COMPANY }

data class IdType(
    val id: String,
    val name: String,
    val category: IdCategory,
    val requiredFields: List<DocumentField>
)
