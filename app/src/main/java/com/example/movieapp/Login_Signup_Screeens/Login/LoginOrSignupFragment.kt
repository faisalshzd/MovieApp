package com.example.movieapp.Login_Signup_Screeens.Login

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R

class LoginOrSignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_signup_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginText = view.findViewById<TextView>(R.id.loginText)
        val signupButton = view.findViewById<View>(R.id.signupButton)
        val fullText = "I already have an account? Login"
        val loginWord = "Login"
        val spannable = SpannableString(fullText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_loginOrSignupFragment_to_loginScreenFragment)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = android.graphics.Color.parseColor("#12CDD9")
                ds.isUnderlineText = false
            }
        }


        val startIndex = fullText.indexOf(loginWord)
        val endIndex = startIndex + loginWord.length
        spannable.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        loginText.text = spannable
        loginText.movementMethod = LinkMovementMethod.getInstance()
        loginText.highlightColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)

        // Navigate to SignUp screen
        signupButton?.setOnClickListener {
            findNavController().navigate(R.id.action_loginOrSignupFragment_to_signupScreenFragment)
        }

    }
}

@Composable
fun Login_or_Sign(navController: NavController) {
    AndroidView(
        factory = { context ->
            FragmentContainerView(context).apply {
                id = R.id.login_nav_host_fragment_container

                val navHostFragment = NavHostFragment.create(R.navigation.login_nav_graph)

                (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                    .replace(this.id, navHostFragment)
                    .setPrimaryNavigationFragment(navHostFragment)
                    .commit()
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}




