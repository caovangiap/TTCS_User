package com.example.ttcs_user.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.indiandev.smartrateapp.util.RateDialogManager

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
                    Log.d("FragmentUser",account.toString())
                    binding?.userNameAccount?.text = account.displayName
                    binding?.userGmailAccount?.text = account.email

                    // lay thong tin dang nhap
                    HandleLogin().firebaseAuthWithGoogle(account,viewModelLogin)
                } catch (e: ApiException) {
                    // Xử lý lỗi
                    Log.d("FragmentUser",e.toString())
                }
            }

        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allFunction()

        binding?.rateUs?.setOnClickListener {
            rateFunction(savedInstanceState)
        }
    }

    private fun allFunction(){
        binding?.btnUpgradeAccount?.setOnClickListener {
            loginAccount()
        }

        updateLoginFunction()

        binding?.btnFeedBack?.setOnClickListener {
            feedBackShop(requireContext(),"khongchovao@gmail.com","My FeedBack","I need: ")
        }
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

    @SuppressLint("QueryPermissionsNeeded")
    private fun feedBackShop(context: Context, recipient: String, subject: String, body: String){
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context,"Email app doesn't exist", Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun rateFunction(savedInstanceState: Bundle?) {
        binding?.rateUs?.setOnClickListener {
            RateDialogManager.showRateDialog(requireContext(), savedInstanceState)
        }
    }


}