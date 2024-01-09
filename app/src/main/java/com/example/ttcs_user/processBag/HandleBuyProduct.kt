package com.example.ttcs_user.processBag

import android.content.Context
import android.util.Log
import com.example.ttcs_user.processBag.modelOrder.AppDatabase
import com.example.ttcs_user.processBag.modelOrder.Bag

class HandleBuyProduct {

    fun addToBag(mContext : Context, product : Bag){

        val dataBase = AppDatabase.getDataBase(mContext)
        dataBase.bag().insertAll(product)

        Log.d("HandleProduct",product.toString())
    }
}