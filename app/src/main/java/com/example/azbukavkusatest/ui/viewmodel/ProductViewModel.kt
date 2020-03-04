package com.example.azbukavkusatest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    private val selectedImageLd = MutableLiveData<String>()

    fun getSelectedImageLd() = selectedImageLd
}