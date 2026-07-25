package com.redx.idmaker.generator

import android.graphics.Bitmap

data class IdCardData(
    val idTypeId: String,
    val photo: Bitmap?,
    val fields: Map<String, String>
)
