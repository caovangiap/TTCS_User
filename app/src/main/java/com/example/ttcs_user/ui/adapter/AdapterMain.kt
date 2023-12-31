package com.example.ttcs_user.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ttcs_user.ui.fragment.FragmentCart
import com.example.ttcs_user.ui.fragment.FragmentHistory
import com.example.ttcs_user.ui.fragment.FragmentHome
import com.example.ttcs_user.ui.fragment.FragmentUser

class AdapterMain(activity: AppCompatActivity)  : FragmentStateAdapter(activity){

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> FragmentHome()
            1 -> FragmentCart()

            2 -> FragmentHistory()
            3 -> FragmentUser()
            else-> FragmentHome()
        }
    }

}