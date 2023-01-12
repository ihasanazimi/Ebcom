package com.example.ebcom.utility.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    open val errorLiveData = MutableLiveData<ArrayList<String>>()
    open var progressLiveData = MutableLiveData<Boolean>(false)

    // for clear data after change state
    open fun clearErrorLiveData(){
        val data = errorLiveData.value?.apply { clear() } ?: arrayListOf()
        errorLiveData.value = data
    }


    override fun onCleared() {
        clearErrorLiveData()
        super.onCleared()
    }
}



