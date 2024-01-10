package com.example.ttcs_user.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ttcs_user.model.history.Information
import java.text.NumberFormat

class AdapterHistory(val information: MutableList<Information>, val detailItems: MutableList<MutableList<DataItems>>) :
    RecyclerView.Adapter<AdapterHistory.ViewHolder>() {

    interface click{
        fun clickRemove(position: Int)
    }

    lateinit var remove : click

    fun listener( listener: click ){
        remove = listener
    }


    class ViewHolder(itemView: View, remove : click) : RecyclerView.ViewHolder(itemView) {
        var recycler: RecyclerView
        var name: TextView
        var price: TextView
        var adress: TextView
        var phoneNumber: TextView
        var removeItems : Button
        init {
            recycler = itemView.findViewById(R.id.items)
            name = itemView.findViewById(R.id.name)
            price = itemView.findViewById(R.id.price)
            adress = itemView.findViewById(R.id.address)
            phoneNumber = itemView.findViewById(R.id.phone)
            removeItems = itemView.findViewById(R.id.remove)
            removeItems.setOnClickListener {
                remove.clickRemove(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_history, parent, false)
        return ViewHolder(view,remove)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.name.text = "Tên:${information[position].name}"
        holder.phoneNumber.text ="SĐT:${information[position].phoneNumber} "
        holder.adress.text = "Địa Chỉ :${information[position].address}"
        holder.price.text = "Tổng Tiền : ${information[position].total}"
        holder.recycler.adapter = AdapterItems(detailItems[position])
        holder.recycler.layoutManager = LinearLayoutManager(holder.itemView.context,LinearLayoutManager.VERTICAL,false)
    }

    override fun getItemCount(): Int {
        return information.size
    }
    fun removeItem(position: Int) {
        information.remove(information[position])
        notifyItemRemoved(position)
        Log.d("Adapter",position.toString())
    }

}

open  class AdapterItems( val detailItems: MutableList<DataItems>) : RecyclerView.Adapter<AdapterItems.ViewHodel>(){

    class ViewHodel(itemView: View) : RecyclerView.ViewHolder(itemView){
        var nameProduct : TextView
        var priceProduct : TextView
        var imageProduct : ImageView
        var sizeProduct : TextView

        init {
            nameProduct = itemView.findViewById(R.id.NameProduct)
            priceProduct = itemView.findViewById(R.id.PriceProduct)
            imageProduct = itemView.findViewById(R.id.Image_Items)
            sizeProduct = itemView.findViewById(R.id.SizeProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodel {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_history_product, parent, false)
        return ViewHodel(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHodel, position: Int) {
        holder.nameProduct.text = "Sản Phẩm : ${detailItems[position].name_Product}"
        holder.sizeProduct.text = "Size : ${detailItems[position].size}"
        holder.priceProduct.text = "Giá : ${converMonney(detailItems[position].price)}"
        Glide.with(holder.itemView.context)
            .load(detailItems[position].image)
            .into(holder.imageProduct)

    }

    override fun getItemCount(): Int {
        return detailItems.size
    }

    fun converMonney(Total: String?): String? {

        var total = Total?.toLong()
        // conver price dạng long về dạng tiền tệ
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.setMaximumFractionDigits(0)
        val convert = numberFormat.format(total)
        return convert
    }
}