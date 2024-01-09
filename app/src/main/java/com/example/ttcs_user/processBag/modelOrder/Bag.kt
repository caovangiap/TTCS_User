package com.example.ttcs_user.processBag.modelOrder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Bag(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long?,
    @ColumnInfo(name = "Name")

    val nameProduct: String?,


    @ColumnInfo(name = "Condition")
    val condition: String?,

    @ColumnInfo(name = "Price")
    val price: String?,

    @ColumnInfo(name = "Size")
    val size: String?,

    @ColumnInfo(name = "Image")
    val image: String?,

    @ColumnInfo(name = "Number")
    val number : Int?
)

