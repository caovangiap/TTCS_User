package com.example.ttcs_user.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.ttcs_user.R


data class DataLanguage(
    val country : String,
    val codeLanguage: String,
    val codeCountry : String,

)


class AdapterChangeLanguage( val data: MutableList<DataLanguage>,
                             val eventButton: EventButoon,
                             val positionChoose: Int?): RecyclerView.Adapter<AdapterChangeLanguage.ViewHolder>()   {

    var currentSelecItems = -1


    interface EventButoon{

        fun chooselanguage(data: DataLanguage,position: Int)
    }

    class ViewHolder(items : View) : RecyclerView.ViewHolder(items){
        val itemsDownload : RadioButton

        init {
            itemsDownload = items.findViewById(R.id.btn_items)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_quality,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemsDownload.text = data[position].country

        // set check for radio button
        holder.itemsDownload.isChecked = position == currentSelecItems

        // first check for radio button
        if (currentSelecItems == -1 && position== positionChoose){
            holder.itemsDownload.isChecked = true
            // check for first show items quality
            eventButton.chooselanguage(data[0],positionChoose)
        }

        // change laguage
        holder.itemsDownload.setOnClickListener {
            currentSelecItems = position
            notifyDataSetChanged()
            eventButton.chooselanguage(data[position],position)
        }

    }
}