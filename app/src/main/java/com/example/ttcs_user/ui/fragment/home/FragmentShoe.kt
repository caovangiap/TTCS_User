package com.example.ttcs_user.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.android_basic.ui.allitems.AdapterProduct
import com.example.ttcs_user.databinding.FragmentShoeBinding
import com.example.ttcs_user.getData.HandleGetData
import com.example.ttcs_user.getData.ViewModelGetData
import com.example.ttcs_user.model.product.ProductShoe
import java.lang.Exception

class FragmentShoe : Fragment(), AdapterProduct.Setonclick {


    private var binding : FragmentShoeBinding? = null
    private var viewModel : ViewModelGetData? = null

    private var productShoe = mutableListOf<ProductShoe>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentShoeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity())[ViewModelGetData::class.java]
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allFunction()
    }

    private fun allFunction(){
        getViewProduct()
    }

    private fun getViewProduct(){
        val handle = HandleGetData()
        handle.getDataAllShoe(viewModel)

        viewModel?.productShoe?.observe(viewLifecycleOwner){
            Log.d("FragmentShoe",it.toString())

            if (it.size !=0){
                val adapterShoe = AdapterProduct(it)
                adapterShoe.listener(this)
                binding?.viewItem?.adapter = adapterShoe
                binding?.viewItem?.layoutManager = StaggeredGridLayoutManager(
                    2,
                    GridLayoutManager.VERTICAL
                )
                productShoe = it

            }
        }

    }

    override fun onclick(position: Int) {
        Log.d("Shoe", "click")
        try {
            viewModel?.detailProductShoe?.postValue(productShoe[position])
        }catch (e:Exception){
            Log.d("FragmentShoe",e.toString())
        }

    }

}