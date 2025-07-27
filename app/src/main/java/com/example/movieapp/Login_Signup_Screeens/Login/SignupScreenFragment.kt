package com.example.movieapp.Login_Signup_Screeens.Login

import SignUpScreen
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.movieapp.ui.theme.MovieAppTheme

class SignupScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MovieAppTheme {
                    val navController=findNavController()
                    SignUpScreen(
                        navController,
                        onTermsClick = {
                            // TODO: navigate to Terms fragment or show dialog
                        },
                        onPrivacyClick = {
                            // TODO: navigate to Privacy fragment or show dialog
                        }
                    )
                }
            }
        }
    }
}
