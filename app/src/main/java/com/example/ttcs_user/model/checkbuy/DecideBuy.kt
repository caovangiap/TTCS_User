package com.example.android_basic.model.checkbuy

import android.os.Parcelable


import kotlinx.parcelize.Parcelize

@Parcelize
data class DecideBuy(

    val name: String? = null,
    val address: String? = null,
    val note: String? = null,
    val phoneNumber: String? = null,
    val timestamp: String? = null,
    val idUser : String?= null,
    val total : String? = null,
    val complate : String
) : Parcelable
