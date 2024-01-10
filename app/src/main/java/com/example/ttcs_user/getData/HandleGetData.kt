package com.example.ttcs_user.getData

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ttcs_user.model.history.DataItems
import com.example.ttcs_user.model.history.Information
import com.example.ttcs_user.model.product.ProductShoe
import com.example.ttcs_user.model.productaccessory.AccessoryData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HandleGetData {


    private var dataInformation = mutableListOf<Information>()
    // instantce cuar fire store
    private val db = Firebase.firestore

    // id cua user
    private val user = Firebase.auth.currentUser


    // list chi tiết mặt hàng
    var dataItems = mutableListOf<MutableList<DataItems>>()

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


    fun getDataHistory(viewModel: ViewModelGetData){

        val id = mutableListOf<String>()
        Log.d("test",user?.uid.toString())

        // call id
        db.collection("Order")
            .whereEqualTo("idUser", "M5sS4GCgkOQ9BgRWnzk28RLRETf2")
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                // cac id don hang da dat
                for (doc in snapshot!!) {
                    Log.d("test1",doc.id)
                    id.add(doc.id)
                }

                getDetailData(id,viewModel)
            }
    }


    // call data chi tiet don hang
    private fun getDetailData(id: MutableList<String>, viewModel: ViewModelGetData){


        for (i in 0 until id.size){
            db.collection("Order")
                .document(id[i])
                .collection("product")
                .whereEqualTo("condition", "true")
                .addSnapshotListener { snapshot, e ->

                    if (e != null) {
                        Log.w(ContentValues.TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    // chi tiet mat hang
                    val detaiData = mutableListOf<DataItems>()
                    for (doc in snapshot!!) {
                        doc.toObject(DataItems::class.java).let { detaiData.add(it) }
                        Log.d("HandleGetData",doc.data.values.toString())
                    }

                    // tat ca chi tiet dc add vao 1 list
                    dataItems.add(detaiData)
                    // cac item product
                    viewModel.historyItemsBuy.postValue(dataItems)
                }
        }


        // thong tin don hang
        db.collection("Order")
            .whereEqualTo("idUser", user?.uid)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }
                // thong tin ve vi tri
                for (doc in value!!) {
                    Log.d("information",doc.toObject(Information::class.java).toString())
                    doc.toObject(Information::class.java).let { dataInformation.add(it) }
                }

                viewModel.historyOrderBuy.postValue(dataInformation)
            }
    }
    
    // reset data history để mảng data không bị chồng chéo data cũ
    fun resetDataHistory(){
        dataInformation.clear()
        dataItems.clear()
    }


}