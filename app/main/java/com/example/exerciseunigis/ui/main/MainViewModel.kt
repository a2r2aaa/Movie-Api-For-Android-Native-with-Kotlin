package com.example.apiRest.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val _myLiveData = MutableLiveData<String>()
    val myLiveData: LiveData<String> = _myLiveData

    fun updateValue(newValue: String) {
        _myLiveData.value = newValue
    }




}