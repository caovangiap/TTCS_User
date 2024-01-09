package com.example.ttcs_user.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttcs_user.R
import com.example.ttcs_user.databinding.DialogDetailsizeBinding
import com.example.ttcs_user.getData.ViewModelGetData
import com.example.ttcs_user.ui.adapter.AdapterSelectSize

class DialogChooseSize : DialogFragment(){

    private var binding : DialogDetailsizeBinding? = null
    private var viewModel : ViewModelGetData? = null

    // lưu trữ các size
    val Size = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogDetailsizeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity())[ViewModelGetData::class.java]
        setUpView()
        return binding?.root
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
//        // remove background under xml
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    fun setUpView() {

        binding?.toolbar?.setNavigationIcon(R.drawable.back)
         //yêu cầu activity điều hướng quay trở lại
        binding?.toolbar?.setNavigationOnClickListener {
            dismiss()
        }
        selectSize()
    }

    // hiển thị view chọn size và set sự kiện chọn size
    fun selectSize(){
        // adaptersize
        for (i in 30..50) {
            Size.add(i.toString())
        }
        val adapter = AdapterSelectSize(Size)
        adapter.SetUp(object : AdapterSelectSize.ChooseSize {
            override fun Choose(position: Int) {
                viewModel?.productSize?.postValue(Size[position])
                Log.d("clcik", "size")
            }
        })

        binding?.DetailSize?.adapter = adapter
        binding?.DetailSize?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}