package com.example.ttcs_user.getData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ttcs_user.model.product.ProductShoe
import com.example.ttcs_user.model.productaccessory.AccessoryData

class ViewModelGetData  : ViewModel() {


    val productShoe = MutableLiveData<MutableList<ProductShoe>>()

    val productAccessory = MutableLiveData<MutableList<AccessoryData>>()


    val detailProductAccessory = MutableLiveData<AccessoryData>()

    val detailProductShoe = MutableLiveData<ProductShoe>()

    val productSize = MutableLiveData<String>()
}