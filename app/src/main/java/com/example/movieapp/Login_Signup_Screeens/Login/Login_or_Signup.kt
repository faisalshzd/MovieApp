// LoginOrSignupFragment.kt
package com.example.movieapp.Login_Signup_Screeens.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.example.movieapp.R

class LoginOrSignupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_signup_activity, container, false)
    }
}
@Composable
fun Login_or_Sign() {
    AndroidView(
        factory = { ctx ->
            val fragmentContainer = androidx.fragment.app.FragmentContainerView(ctx).apply {
                id = View.generateViewId() // Ensure unique ID
            }

            val fragmentManager = (ctx as AppCompatActivity).supportFragmentManager

            fragmentManager.beginTransaction()
                .replace(fragmentContainer.id, LoginOrSignupFragment())
                .commitAllowingStateLoss()

            fragmentContainer
        }
    )
}

