package com.example.ttcs_user.ui.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ttcs_user.processBag.modelOrder.Bag
import com.example.ttcs_user.R
import com.example.ttcs_user.databinding.DetailItemsBinding
import com.example.ttcs_user.getData.ViewModelGetData
import com.example.ttcs_user.model.product.ProductShoe
import com.example.ttcs_user.model.productaccessory.AccessoryData
import com.example.ttcs_user.processBag.HandleBuyProduct
import java.text.NumberFormat

class DialogDetailItems : DialogFragment(){

    private var binding : DetailItemsBinding? = null
    private var viewModel : ViewModelGetData? = null
    private var dataShoe : ProductShoe? =null
    private var detailAccessory : AccessoryData? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailItemsBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity())[ViewModelGetData::class.java]
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allFunction()
    }

    private fun allFunction(){
        setUpView()
        binding?.ToolBar?.setNavigationIcon(R.drawable.back)
        binding?.ToolBar?.setNavigationOnClickListener {
            dismiss()
        }

        selectSize()
    }

    private fun setUpView(){

        /**
         * view shoe
          */
        viewModel?.detailProductShoe?.observe(viewLifecycleOwner){

            if (it!=null){
                dataShoe = it

                Glide.with(requireContext())
                    .load(it.Image?.URL1?.IG1)
                    .into(binding!!.Image1)

                Glide.with(requireContext())
                    .load(it.Image?.URL2?.IG1)
                    .into(binding!!.Image2)

                binding?.ImageView1?.setOnClickListener {
                    Glide.with(requireContext())
                        .load(dataShoe?.Image?.URL1?.IG1)
                        .into(binding!!.viewFragment)
                }

                binding?.ImageView2?.setOnClickListener {
                    Glide.with(requireContext())
                        .load(dataShoe?.Image?.URL2?.IG1)
                        .into(binding!!.viewFragment)
                }

                Glide.with(requireContext())
                    .load(it.Image?.URL2?.IG1)
                    .into(binding!!.viewFragment)

                binding?.productName?.text = it.Name
                changeMoney(it.Price)


                binding?.AddToBag?.setOnClickListener {
                    val productBag = Bag(null,
                        dataShoe!!.Name,
                        dataShoe!!.Condition,
                        dataShoe!!.Price,
                        dataShoe!!.Size,
                        dataShoe!!.Image?.URL2?.IG1,
                        1)

                    addToBag(productBag)
                }

            }

        }


        /**
         * view accessory
          */
        viewModel?.detailProductAccessory?.observe(viewLifecycleOwner){
            if (it!=null){
                detailAccessory = it
                Glide.with(this)
                    .load(it.Image)
                    .into(binding!!.Image1)

                Glide.with(this)
                    .load(it.Image)
                    .into(binding!!.Image2)

                binding?.ImageView1?.setOnClickListener {
                    Glide.with(this)
                        .load(detailAccessory?.Image)
                        .into(binding!!.Image1)
                }

                binding?.ImageView2?.setOnClickListener {
                    Glide.with(this)
                        .load(detailAccessory?.Image)
                        .into(binding!!.Image1)
                }
                Glide.with(requireContext())
                    .load(it.Image)
                    .into(binding!!.viewFragment)

                binding?.productName?.text = it.Name
                changeMoney(it.Price)

                binding?.AddToBag?.setOnClickListener {
                    val productBag = Bag(null,
                        detailAccessory!!.Name,
                        detailAccessory!!.Condition,
                        detailAccessory!!.Price,
                        detailAccessory!!.Size,
                        detailAccessory!!.Image,
                        1)

                    addToBag(productBag)
                }
            }
        }


    }

    @SuppressLint("SetTextI18n")
    fun changeMoney(totalPrice : String?){
        val total = totalPrice?.toLong()
        // conver price dạng long về dạng tiền tệ
        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits = 0
        val convert = numberFormat.format(total)
        // set text tổng tiền
        binding?.productPrice?.text = convert
    }

    @SuppressLint("SetTextI18n")
    private fun selectSize(){
        val dialog = DialogChooseSize()
        binding?.SelectSize?.setOnClickListener {
            dialog.show(parentFragmentManager,"chooseSize")
        }

        viewModel?.productSize?.observe(viewLifecycleOwner){
            if (it != null){
                binding?.SelectSize?.text = "30"
            }
            binding?.SelectSize?.text = it
            dialog.dismiss()
        }


    }

    private fun addToBag(product : Bag){
        HandleBuyProduct().addToBag(requireContext(),product)
        dismiss()
    }


}