package com.example.ttcs_user.login

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class HandleLogin {

    /**
     * Tạo một GoogleSignInClient
      */
    fun createSignInIntent(context: Context, activity: Activity): GoogleSignInClient {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(com.firebase.ui.auth.R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(activity, googleSignInOptions)
    }

    /**
     * handle when login success
     */
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount, viewModelLogin: ViewModelLogin?) {
        // update information login
        viewModelLogin?.imageUser?.postValue(account.photoUrl.toString())
        viewModelLogin?.nameUser?.postValue(account.displayName)
        viewModelLogin?.emailUser?.postValue(account.email)

    }

}