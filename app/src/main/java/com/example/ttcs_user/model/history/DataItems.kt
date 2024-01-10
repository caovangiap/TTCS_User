package com.example.ttcs_user.model.history

import android.os.Parcelable

import com.google.firebase.database.Exclude


data class DataItems(

    val id : Long? = 0,
    val name_Product: String? = "",
    val condition: String? = "",
    val price: String? ="",
    val size: String? = "",
    val image: String? = "",
    val number: Int ? = 0
) : Parcelable{

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name_Product" to name_Product,
            "condition" to condition,
            "size" to size,
            "id" to id,
            "Image" to image,
            "number" to number,
            "price" to price,
        )
    }
}
