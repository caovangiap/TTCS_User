package com.example.ttcs_user.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttcs_user.databinding.FragmentBagBinding
import com.example.ttcs_user.processBag.modelOrder.AppDatabase
import com.example.ttcs_user.processBag.modelOrder.Bag
import com.example.ttcs_user.ui.adapter.AdapterBag
import com.example.ttcs_user.ui.adapter.EventClick
import java.text.NumberFormat

/**
 * fragment: gio hang
 */
class FragmentCart : Fragment(), EventClick {

    private var binding : FragmentBagBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBagBinding.inflate(inflater,container,false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allFunction()
    }

    private fun allFunction(){

        val dataBase = AppDatabase.getDataBase(requireContext()).bag()
        val dataBag =  dataBase.getAll()
        dataBag.observe(viewLifecycleOwner){

            val adapter = AdapterBag(it,this)
            Log.d("FragmentCart",it.toString())
            binding?.AllItems?.adapter = adapter
            binding?.AllItems?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    override fun remove(position: Int) {

        Log.d("d",position.toString())

    }


    @SuppressLint("SetTextI18n")
    fun allTotal(data:MutableList<Bag>){

        var total = 0L
        for (i in 0 until data.size) {
            total += data[i].price!!.toLong()
        }
        // conver price dạng long về dạng tiền tệ
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0
        val convert = numberFormat.format(total)
        // set text tổng tiền
        binding?.Total?.text = "Total :$convert"

    }


}