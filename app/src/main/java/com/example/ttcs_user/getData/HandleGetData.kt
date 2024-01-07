package com.example.ttcs_user.getData

import android.content.ContentValues
import android.util.Log
import com.example.ttcs_user.model.product.ProductShoe
import com.example.ttcs_user.model.productaccessory.AccessoryData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HandleGetData {

    fun getDataAllShoe(viewModel: ViewModelGetData?) {
        val db = Firebase.firestore
        val data = mutableListOf<ProductShoe>()

        // lấy data từ firebase về
        db.collection("Product")
            .document("Shoe").collection("AllShoe")

            .whereEqualTo("Size", "30")
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                for (doc in snapshot!!) {
                    doc.toObject(ProductShoe::class.java).let { data.add(it) }
                    Log.d("shoe",doc.data.values.toString())
                }

                viewModel?.productShoe?.postValue(data)
            }
    }

    fun getDataAllAccessory(viewModel: ViewModelGetData?) {
        val db = Firebase.firestore
        val data = mutableListOf<AccessoryData>()

        // lấy data từ firebase về
        db.collection("Product")
            .document("Accessory").collection("AllAccessory")

            .whereEqualTo("Size", "10")
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                for (doc in snapshot!!) {
                    doc.toObject(AccessoryData::class.java).let { data.add(it) }
                    Log.d("accessory",doc.toString())
                }

                viewModel?.productAccessory?.postValue(data)
            }
    }


}