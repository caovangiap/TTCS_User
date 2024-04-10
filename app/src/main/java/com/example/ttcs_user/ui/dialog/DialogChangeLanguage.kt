package com.example.ttcs_user.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttcs_user.databinding.DialogChangeLanguageBinding
import com.example.ttcs_user.ui.UiSetting
import com.example.ttcs_user.ui.activity.ActivityMain
import com.example.ttcs_user.ui.adapter.AdapterChangeLanguage
import com.example.ttcs_user.ui.adapter.DataLanguage

class DialogChangeLanguage : DialogFragment() {

    private var binding: DialogChangeLanguageBinding? = null
    private var language : DataLanguage? = null
    private var possitionLanguageChoose = 0


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // remove background under xml
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogChangeLanguageBinding.inflate(inflater,container,false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allFunction()
    }

    private fun allFunction(){
        val countryList = mutableListOf(
            DataLanguage("English", "ru", "RU"),
            DataLanguage("Portugal", "pt", "PT"),
            DataLanguage("대한민국", "ko", "KR"),
            DataLanguage("España", "ca", "rES"),
            DataLanguage("中国", "zh", "CN"),
            DataLanguage("France", "fr", "rFR"),
            DataLanguage("Italia", "ca", "rIT"),
            DataLanguage("Deutschland", "de", "DE"),
            DataLanguage("Россия", "os", "rRU")
        )

        val eventButton = object: AdapterChangeLanguage.EventButoon{
            override fun chooselanguage(data: DataLanguage, position: Int) {
                language = data
                possitionLanguageChoose = position
            }

        }
        val adapter = AdapterChangeLanguage(countryList,eventButton,0)
        binding?.listRecyclerView?.adapter = adapter
        binding?.listRecyclerView?.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

        acceptChangeLanguage()
    }

    private fun acceptChangeLanguage(){
        val changeLanguage : UiSetting = activity as ActivityMain
        binding?.acceptLanguage?.setOnClickListener {
            if (language!=null){

                changeLanguage.changeLanguage(language!!.codeLanguage, language!!.codeCountry)

            }
            dismiss()
        }
    }
}