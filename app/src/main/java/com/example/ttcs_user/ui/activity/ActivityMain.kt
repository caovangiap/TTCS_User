package com.example.ttcs_user.ui.activity

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.ttcs_user.R
import com.example.ttcs_user.databinding.ActivityMainBinding
import com.example.ttcs_user.getData.ViewModelGetData
import com.example.ttcs_user.ui.UiSetting
import com.example.ttcs_user.ui.adapter.AdapterMain
import com.example.ttcs_user.ui.dialog.DialogDetailItems

class ActivityMain : AppCompatActivity(), UiSetting {

    private var binding : ActivityMainBinding? = null
    private var viewModelGetData : ViewModelGetData? = null
    private val localizationDelegate = LocalizationActivityDelegate(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModelGetData = ViewModelProvider(this)[ViewModelGetData::class.java]
        setContentView(binding?.root)

        allFunction()
        localizationDelegate.addOnLocaleChangedListener(object : OnLocaleChangedListener {
            override fun onAfterLocaleChanged() {

            }

            override fun onBeforeLocaleChanged() {

            }
        })
        localizationDelegate.onCreate()
    }

    private fun allFunction(){
        setUpBottomNavigation()
        setUpViewPager()

        setUpDetailProduct()
    }


    private fun setUpBottomNavigation(){
        binding?.bottomNavigation?.add(MeowBottomNavigation.Model(1, R.drawable.baseline_home_24))
        binding?.bottomNavigation?.add(MeowBottomNavigation.Model(2, R.drawable.baseline_shopping_cart_24))
        //lịch sử mua hàng
        binding?.bottomNavigation?.add(MeowBottomNavigation.Model(3, R.drawable.baseline_cloud_done_24))
        // setting user
        binding?.bottomNavigation?.add(MeowBottomNavigation.Model(4, R.drawable.baseline_person_24))
        binding?.bottomNavigation?.show(1,true)

        binding?.bottomNavigation?.setOnClickMenuListener {
            when(it.id){
                1->{
                    binding?.mainView?.currentItem = 0
                }
                2->{
                    binding?.mainView?.currentItem = 1
                }
                3->{
                    binding?.mainView?.currentItem = 2
                }
                4->{
                    binding?.mainView?.currentItem = 3
                }

                else ->{
                    binding?.mainView?.currentItem = 0
                }
            }
        }
    }

    private fun setUpViewPager(){
        val adapter = AdapterMain(this)
        binding?.mainView?.adapter = adapter
        binding?.mainView?.isUserInputEnabled = false
        binding?.mainView?.currentItem = 0

        binding?.mainView?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0->{
                        binding?.bottomNavigation?.show(1,true)
                    }
                    1 ->{
                        binding?.bottomNavigation?.show(2,true)
                    }
                    2->{
                        binding?.bottomNavigation?.show(3,true)
                    }
                    3->{
                        binding?.bottomNavigation?.show(4,true)
                    }
                    4->{
                        binding?.bottomNavigation?.show(5,true)
                    }
                }
            }
        })


    }

    private fun setUpDetailProduct(){
        viewModelGetData?.detailProductAccessory?.observe(this){
            val dialog = DialogDetailItems()
            dialog.show(supportFragmentManager,"detail shoe")
        }

        viewModelGetData?.detailProductShoe?.observe(this){
            val dialog = DialogDetailItems()
            dialog.show(supportFragmentManager,"detail accessory")

        }
    }

    override fun changeLanguage(codeLanguage: String, codeCountry: String) {
        localizationDelegate.setLanguage(this, codeLanguage, codeCountry)
    }

    public override fun onResume() {
        super.onResume()
        localizationDelegate.onResume(this)
    }

    override fun attachBaseContext(newBase: Context) {
        applyOverrideConfiguration(localizationDelegate.updateConfigurationLocale(newBase))
        super.attachBaseContext(newBase)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(super.getResources())
    }

}