package com.example.ttcs_user.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ttcs_user.R


class AdapterSelectSize(val data: ArrayList<String>) : RecyclerView.Adapter<AdapterSelectSize.ViewHolder>() {

    interface ChooseSize{
        fun Choose(position: Int)
    }
    lateinit var Onclick : ChooseSize

    fun SetUp(Choose:ChooseSize){
        Onclick = Choose
    }

    class ViewHolder(itemView: View,click: ChooseSize) : RecyclerView.ViewHolder(itemView) {
        val Textsize : TextView
        val button : Button

        init {
            button = itemView.findViewById(R.id.btsize)
            Textsize = itemView.findViewById(R.id.size)
            button.setOnClickListener {
                click.Choose(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detailsize,parent, false)
        return ViewHolder(view,Onclick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.Textsize.text = data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }
}