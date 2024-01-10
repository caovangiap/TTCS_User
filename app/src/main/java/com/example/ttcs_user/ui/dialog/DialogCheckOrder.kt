package com.example.ttcs_user.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.ttcs_user.Constant
import com.example.ttcs_user.R
import com.example.ttcs_user.databinding.DialogCheckoutBinding
import com.example.ttcs_user.processBag.HandleBuyProduct
import com.example.ttcs_user.processBag.ViewModelCheckOut
import com.example.ttcs_user.processBag.modelOrder.AppDatabase

class DialogCheckOrder : DialogFragment() {

    private var binding : DialogCheckoutBinding? = null
    private var viewModel : ViewModelCheckOut? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity())[ViewModelCheckOut::class.java]
        binding = DialogCheckoutBinding.inflate(inflater,container,false)
        return binding?.root

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        // remove background under xml
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allFunction()
    }

    private fun allFunction(){
        setUpView()
    }

    private fun setUpView(){

        binding?.check?.setOnClickListener {
            HandleBuyProduct().checkOut(binding?.CheckName?.text.toString(),
                binding?.phoneNumber?.text.toString(),
                binding?.InputEmail?.text.toString(),
                binding?.InputPassWord?.text.toString(),
                viewModel,
                requireContext()
                )
        }

        viewModel?.totalOrder?.observe(viewLifecycleOwner){
            binding?.Total?.text = it
        }


        // cac cacn bao khi nhap sai du lieu
        viewModel?.requiredName?.observe(viewLifecycleOwner){
            binding?.Name?.helperText = it
        }

        viewModel?.requiredAddess?.observe(viewLifecycleOwner){
            binding?.address?.helperText =  it
        }

        viewModel?.requiredPhone?.observe(viewLifecycleOwner){
            binding?.Phone?.helperText = it
        }

        viewModel?.requiredNotification?.observe(viewLifecycleOwner){
            binding?.Email?.helperText = it
        }

        viewModel?.totalOrder?.observe(viewLifecycleOwner){
            binding?.Total?.text = it
        }

        viewModel?.checkOutSuccess?.observe(viewLifecycleOwner){
            if (it == Constant.successOrder){
                AppDatabase.getDataBase(requireContext()).bag().deleteAll()
                Toast.makeText(requireContext(),"Success Order",Toast.LENGTH_SHORT).show()
                viewModel?.checkOutSuccess?.postValue("")
                dismiss()
            }
        }
        setUpToolBar()
    }
    fun setUpToolBar(){

        binding?.ToolBar?.setNavigationIcon(R.drawable.back)
        //yêu cầu activity điều hướng quay trở lại
        binding?.ToolBar?.setNavigationOnClickListener {
            dismiss()
        }

    }


}