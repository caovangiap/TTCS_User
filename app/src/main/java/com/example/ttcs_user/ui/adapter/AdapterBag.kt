package com.example.ttcs_user.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.ttcs_user.R
import com.example.ttcs_user.processBag.modelOrder.Bag
import java.text.NumberFormat


interface EventClick{
    fun remove(data: Bag)
}

class AdapterBag (val data: MutableList<Bag>, private val remove : EventClick) : RecyclerView.Adapter<AdapterBag.ViewHolder>() {


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image : ImageView
        val name : TextView
        val price : TextView
        val size : TextView
        private val number : TextView
        val button : Button

        init {
            image = itemView.findViewById(R.id.Image_Items)
            name = itemView.findViewById(R.id.NameProduct)
            price = itemView.findViewById(R.id.PriceProduct)
            size = itemView.findViewById(R.id.SizeProduct)
            number = itemView.findViewById(R.id.NumberProduct)
            button = itemView.findViewById(R.id.Remove)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_bag, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].nameProduct
        holder.size.text = data[position].size

        // conver dạng long thành tiền tệ
        val price = data[position].price?.toLong()
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0
        val convert = numberFormat.format(price)
        //

        holder.price.text = convert
        Glide.with(holder.itemView.context)
            .load(data[position].image)
            .apply(RequestOptions().transforms(FitCenter(), RoundedCorners(30)))
            .into(holder.image)


        holder.button.setOnClickListener {
            remove.remove(data[position])
            data.remove(data[position])
            notifyItemRemoved(position)
            Log.d("Adapter",position.toString())
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}