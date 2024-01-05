package com.example.ttcs_user.ui.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ttcs_user.ui.fragment.home.FragmentAccessory
import com.example.ttcs_user.ui.fragment.home.FragmentShoe

class AdapterViewFragmentHome (fragmentManager: FragmentManager, lifecycle: Lifecycle)  : FragmentStateAdapter(fragmentManager,lifecycle){

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> FragmentShoe()
            1 -> FragmentAccessory()
            else-> FragmentShoe()
        }
    }

}