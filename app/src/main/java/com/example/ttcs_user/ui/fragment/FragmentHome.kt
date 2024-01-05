package com.example.ttcs_user.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ttcs_user.R
import com.example.ttcs_user.databinding.FragmentHomeBinding
import com.example.ttcs_user.ui.adapter.AdapterViewFragmentHome
import com.google.android.material.tabs.TabLayoutMediator

class FragmentHome : Fragment() {

    var binding : FragmentHomeBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
        setUpViewPage()
        setUpTabLayout()
    }


    private fun setUpToolBar(){
        binding?.toolBar?.inflateMenu(R.menu.menu_toolbar)
        binding?.toolBar?.setOnMenuItemClickListener {

            when(it.itemId){
                R.id.action_search->{

                    true
                }

                else -> false
            }
        }
    }

    private fun setUpTabLayout(){
        TabLayoutMediator(binding?.myTabLayOut!!, binding?.viewFragmentManager!!) { tab, position ->
            binding?.viewFragmentManager?.setCurrentItem(tab.position, true)
            if (position == 1){
                tab.text = "Shoe"
            }
            if (position == 0) {
                tab.text = "Accessory"
            }
        }.attach()
    }

    private fun setUpViewPage(){
        binding?.viewFragmentManager?.adapter = AdapterViewFragmentHome(childFragmentManager,lifecycle)
        binding?.viewFragmentManager?.isUserInputEnabled = false
    }

}