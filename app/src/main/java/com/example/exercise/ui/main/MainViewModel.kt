package com.example.apiRest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val _myLiveData = MutableLiveData<String>()
    val myLiveData: LiveData<String> = _myLiveData

    fun updateValue(newValue: String) {
        _myLiveData.value = newValue
    }




}