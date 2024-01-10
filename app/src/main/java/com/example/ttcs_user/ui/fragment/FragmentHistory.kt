package com.example.ttcs_user.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttcs_user.databinding.FragmentHistoryBinding
import com.example.ttcs_user.getData.HandleGetData
import com.example.ttcs_user.getData.ViewModelGetData
import com.example.ttcs_user.model.history.DataItems
import com.example.ttcs_user.model.history.Information
import com.example.ttcs_user.ui.adapter.AdapterHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentHistory : Fragment() {


    private var binding: FragmentHistoryBinding? = null
    private var viewModel: ViewModelGetData? = null

    private var orderProduct : MutableList<Information>? = null
    private var itemsProduct : MutableList<MutableList<DataItems>>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ViewModelGetData::class.java]
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showHistory()
    }

    private fun showHistory() {

        HandleGetData().resetDataHistory()
        HandleGetData().getDataHistory(viewModel!!)

        CoroutineScope(Dispatchers.Main).launch{


            val items = CoroutineScope(Dispatchers.Main).launch {
                viewModel?.historyItemsBuy?.observe(viewLifecycleOwner){
                    itemsProduct = it
                }
            }


            val order = CoroutineScope(Dispatchers.Main).launch {
                viewModel?.historyOrderBuy?.observe(viewLifecycleOwner) {

                    val adapter = AdapterHistory(it, itemsProduct!!)
                    adapter.listener(object : AdapterHistory.Click {
                        override fun clickRemove(position: Int) {
                            adapter.removeItem(position)
                        }
                    })

                    binding?.HistoryBuy?.adapter =
                        adapter
                    binding?.HistoryBuy?.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding?.HistoryBuy?.addItemDecoration(
                        DividerItemDecoration(
                            context,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                }
            }
            items.join()
            order.join()
        }

    }


}