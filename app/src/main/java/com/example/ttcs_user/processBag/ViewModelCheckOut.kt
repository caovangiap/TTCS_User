package com.example.ttcs_user.processBag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelCheckOut : ViewModel() {

    val requiredName = MutableLiveData<String>()
    val requiredPhone = MutableLiveData<String>()
    val requiredNotification = MutableLiveData<String>()
    val requiredAddess = MutableLiveData<String>()

    val totalOrder = MutableLiveData<String>()

    val checkOutSuccess = MutableLiveData<String>()
}