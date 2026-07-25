package com.redx.idmaker.data

enum class FieldType { TEXT, DATE, DROPDOWN, MULTILINE }

data class DocumentField(
    val key: String,
    val label: String,
    val type: FieldType = FieldType.TEXT,
    val required: Boolean = true,
    val options: List<String> = emptyList()
)
