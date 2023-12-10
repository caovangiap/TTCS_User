package com.example.ttcs_user.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ttcs_user.databinding.FragmentUserBinding
import com.example.ttcs_user.login.HandleLogin
import com.example.ttcs_user.login.ViewModelLogin
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

class FragmentUser : Fragment() {

    private var binding : FragmentUserBinding? = null
    private val Req_One_Tap = 2
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private var viewModelLogin : ViewModelLogin? =null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater,container,false)
        viewModelLogin = ViewModelProvider(requireActivity())[ViewModelLogin::class.java]

        // intent login
        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!

                    // lay thong tin dang nhap
                    HandleLogin().firebaseAuthWithGoogle(account,viewModelLogin)
                } catch (e: ApiException) {
                    // Xử lý lỗi
                }
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allFunction()
    }

    private fun allFunction(){
        binding?.btnUpgradeAccount?.setOnClickListener {
            loginAccount()
        }

        updateLoginFunction()
    }

    /**
     * kích hoạt login account
     */
    private fun loginAccount(){
        val intent =  HandleLogin().createSignInIntent(requireContext(),requireActivity())
        googleSignInLauncher.launch(intent.signInIntent)
    }

    /**
     * update login function
     */
    private fun updateLoginFunction(){
        viewModelLogin?.imageUser?.observe(viewLifecycleOwner){
            if (it!= null){
                binding?.backgroundUserPremium?.visibility = View.INVISIBLE
                Glide.with(requireContext())
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding?.avtAccount!!)
            }else{
                binding?.backgroundUserPremium?.visibility = View.VISIBLE
            }
        }

    }

}