package com.example.ttcs_user.processBag

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.android_basic.model.checkbuy.DecideBuy
import com.example.ttcs_user.Constant
import com.example.ttcs_user.processBag.modelOrder.AppDatabase
import com.example.ttcs_user.processBag.modelOrder.Bag
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date
import java.util.regex.Pattern

class HandleBuyProduct {


    // condition
    private var conditionName = false
    private var conditionEmail = false
    private var conditionAddress = false
    private var conditionPhone = false

    // instantce cuar fire store
    val db = Firebase.firestore

    // id cua user
    val user = Firebase.auth.currentUser
    var data = arrayListOf<Bag>()

    fun addToBag(mContext : Context, product : Bag){

        val dataBase = AppDatabase.getDataBase(mContext)
        dataBase.bag().insertAll(product)

        Log.d("HandleProduct",product.toString())
    }

    fun checkOut(
        checkName: String?,
        phoneNumber: String,
        inputNotification: String,
        inputAdress: String,
        viewModel: ViewModelCheckOut?,
        requireContext: Context
    ) {

        checkName(checkName!!,viewModel)
        checkPhone(phoneNumber,viewModel)
        checkNotification(inputNotification,viewModel)
        checkAddress(inputAdress,viewModel)
        if (conditionName && conditionAddress && conditionEmail && conditionPhone) {

            successCheckOut(checkName, phoneNumber, inputNotification, inputAdress, viewModel,requireContext)

        }
    }


    private fun checkName(inputName: String, viewModel: ViewModelCheckOut?) {
        if (inputName == "") {
            viewModel?.requiredName?.postValue("không được để trống trường này ")
        } else {
            viewModel?.requiredName?.postValue("success")
            conditionName = true
        }
    }

    // check Phone
    private fun checkPhone(inputPhone: String, viewModel: ViewModelCheckOut?) {
        if (!mobiValidate(inputPhone)) {
            viewModel?.requiredPhone?.postValue("SỐ ĐIỆN THOẠI KHÔNG HỢP LỆ ")
        } else {
            viewModel?.requiredPhone?.postValue("success")
            conditionPhone = true
        }
    }


    // ham check dieu kien sđt
    fun mobiValidate(input: String): Boolean {
        val p = Pattern.compile("[0][0-9]{9}")
        val m = p.matcher(input)
        return m.matches()
    }


    // check email
    fun checkNotification(inputEmail: String, viewModel: ViewModelCheckOut?) {
        if (inputEmail == "") {
            viewModel?.requiredNotification?.postValue("khong co chu thich gi ?")
            conditionEmail = true
        } else {
            viewModel?.requiredNotification?.postValue("success")
            conditionEmail = true
        }
    }

    // check address
    fun checkAddress(inputAddress: String, viewModel: ViewModelCheckOut?) {
        if (inputAddress == "") {
            viewModel?.requiredAddess?.postValue("không được để trống trường này ")
        } else {
            viewModel?.requiredAddess?.postValue("success")
            conditionAddress = true
        }
    }



    // chốt đơn hàng khi đã đủ thông tin

    private fun successCheckOut(
        checkName: String,
        inputPhone: String,
        inputNotification: String,
        inputAddress: String,
        viewModel: ViewModelCheckOut?,
        requireContext: Context
    ) {

        // chuyen doi time dang second sang string
        val time = converTime(Timestamp(Date()).seconds)
        val total = viewModel?.totalOrder?.value
        // đẩy đơn hàng lên fire base
        if (user != null && total != null) {
            // thoong tin nguoi nhan

            val decidedBuy = DecideBuy(
                checkName, inputAddress, inputNotification, inputPhone, time, user.uid,
                total,"False"
            )
            db.collection("Order").document("${user.uid}+$time")
                .set(decidedBuy, SetOptions.merge())

            // thong tin san pham
            for (i in 0 until data.size) {
                db.collection("Order").document("${user.uid}+$time")
                    .collection("product")
                    .document("${data[i].nameProduct} Size: ${data[i].size}")
                    .set(data[i], SetOptions.merge())
            }
            // chuyên đơn hàng sang lịch sử
            viewModel.checkOutSuccess.postValue(Constant.successOrder)
            // xoa tat ca du lieu don hang ve rong lan tiep theo gio hang se trong
        }else{
            Toast.makeText(requireContext,"You must login before buy product",Toast.LENGTH_SHORT).show()
        }
    }

    fun converTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val second = seconds % 60
        val timeString = String.format("%02d:%02d:%02d", hours, minutes, second)
        return timeString
    }

}