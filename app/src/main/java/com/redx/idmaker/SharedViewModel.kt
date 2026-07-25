package com.redx.idmaker

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redx.idmaker.data.Country
import com.redx.idmaker.data.IdType

class SharedViewModel : ViewModel() {
    val selectedCountry = MutableLiveData<Country?>()
    val selectedIdType = MutableLiveData<IdType?>()
    val formFields = MutableLiveData<MutableMap<String, String>>(mutableMapOf())
    val photoBitmap = MutableLiveData<Bitmap?>()
    val generatedCard = MutableLiveData<Bitmap?>()

    fun reset() {
        selectedCountry.value = null
        selectedIdType.value = null
        formFields.value = mutableMapOf()
        photoBitmap.value = null
        generatedCard.value = null
    }

    fun setField(key: String, value: String) {
        val map = formFields.value ?: mutableMapOf()
        map[key] = value
        formFields.value = map
    }
}
