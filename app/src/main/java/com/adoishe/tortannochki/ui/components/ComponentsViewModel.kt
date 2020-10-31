package com.adoishe.tortannochki.ui.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ComponentsViewModel : ViewModel() {



    private val _text = MutableLiveData<String>().apply {
        value = "This is components Fragment"
    }
    val text: LiveData<String> = _text


}