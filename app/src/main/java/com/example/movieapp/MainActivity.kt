package com.example.movieapp

import android.app.Application
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.Login_Signup_Screeens.Login.Login_or_Sign
import com.example.movieapp.Reset_Password.CreateNewPasswordScreen
import com.example.movieapp.Reset_Password.ResetPasswordScreen
import com.example.movieapp.Reset_Password.VerificationScreen
import com.example.movieapp.di.koinModule
import com.example.movieapp.ui.home.HomeScreenActivity
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.viewmodel.MovieViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(koinModule)
        }
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val systemUiController = rememberSystemUiController()
                val backgroundColor = Color(0xFF1F1D2B)

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = false
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundColor
                ) {
                    val navController = rememberNavController()

                    val shouldShowHome = intent.getBooleanExtra("navigate_to_home", false)

                    if (shouldShowHome) {
                        androidx.compose.runtime.LaunchedEffect(Unit) {
                            navController.navigate("home_screen") {
                                popUpTo("login_or_signup") { inclusive = true }
                            }
                        }
                    }


                    val shouldShowLoginFragment = intent.getBooleanExtra("navigate_to_login_fragment", false)
                    if (shouldShowLoginFragment) {
                        // Delay until after composition
                        androidx.compose.runtime.LaunchedEffect(Unit) {
                            navController.navigate("login_or_signup")
                        }
                    }


                    NavHost(
                        navController = navController,
                        startDestination = "splash"
                    ) {
                        composable("splash") {
                            SplashScreen(navController, firebaseAnalytics)
                        }
                        composable("onboarding") {
                            OnboardingScreen(navController)
                        }
                        composable("login_or_signup") {
                            Login_or_Sign()
                        }
                        composable("reset_password") {
                            ResetPasswordScreen(onBackClick = { navController.popBackStack() })
                        }
                        composable("verification_screen/{email}") { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: "example@gmail.com"
                            VerificationScreen(
                                email = email,
                                onBackClick = { navController.popBackStack() },
                                onResendClick = { /* TODO: Handle resend logic */ },
                                onContinue={         navController.navigate("create_new_password_screen")}
                            )
                        }
                        composable("create_new_password_screen") {
                            CreateNewPasswordScreen(
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable("home_screen") {
                            val viewModel: MovieViewModel = koinViewModel()
                            HomeScreenActivity(viewModel)
                        }

                    }
                }
            }
        }
    }
}


