package com.example.ttcs_user.model.history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Information(
    val name: String? = null,
    val address: String? = null,
    val note: String? = null,
    val phoneNumber: String? = null,
    val timestamp: String? = null,
    val id_user: String? = null,
    val total: String? = null
) : Parcelable