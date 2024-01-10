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
import com.example.ttcs_user.databinding.FragmentProductAccessoryBinding
import com.example.ttcs_user.getData.HandleGetData
import com.example.ttcs_user.getData.ViewModelGetData
import com.example.ttcs_user.model.productaccessory.AccessoryData
import com.example.ttcs_user.ui.adapter.AdapterAccessory
import java.lang.Exception

class FragmentAccessory: Fragment(), AdapterAccessory.Setonclick {

    private var binding : FragmentProductAccessoryBinding? = null
    private var viewModel : ViewModelGetData? = null

    private var productAccessory = mutableListOf<AccessoryData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductAccessoryBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity())[ViewModelGetData::class.java]
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allFunction()
    }


    private fun allFunction(){
        val handle = HandleGetData()
        handle.getDataAllAccessory(viewModel)

        viewModel?.productAccessory?.observe(viewLifecycleOwner){
            val adapter = AdapterAccessory(it)
            adapter.listener(this)
            binding?.viewItem?.adapter = adapter
            binding?.viewItem?.layoutManager = StaggeredGridLayoutManager(
                2,
                GridLayoutManager.VERTICAL
            )

            if (it.size!=0){
                productAccessory = it
            }
        }
    }

    override fun onclick(position: Int) {
        Log.d("Shoe", "Click")
        try {
            productAccessory[position]
            viewModel?.detailProductAccessory?.postValue(productAccessory[position])
        }catch (e: Exception){
            Log.d("FragmentAccessory",e.toString())
        }
    }

}