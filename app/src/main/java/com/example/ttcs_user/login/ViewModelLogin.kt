package com.example.ttcs_user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelLogin : ViewModel() {

    val imageUser = MutableLiveData<String>()
    val nameUser = MutableLiveData<String>()
    val emailUser = MutableLiveData<String>()

}