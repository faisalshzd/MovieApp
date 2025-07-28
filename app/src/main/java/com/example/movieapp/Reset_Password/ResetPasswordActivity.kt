package com.example.movieapp.Reset_Password

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MovieAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

class ResetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val systemUiController = rememberSystemUiController()
                val backgroundColor = Color(0xFF171725)

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = false
                    )
                    systemUiController.setNavigationBarColor(
                        color = Color(0xFF171725),
                        darkIcons = false
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundColor
                ) {
                    ResetPasswordScreen(onBackClick = { finish() })
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}

fun isValidEmail(email: String): Boolean {
    return email.contains("@") &&
            email.contains(".") &&
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(onBackClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val montserratMedium = FontFamily(Font(R.font.montserrat_medium, FontWeight.Medium))

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color(0xFF171725)
    ) { paddingValues -> Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF171725))
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            .padding(horizontal = 24.dp)
    )
    {
            // Back Button
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .background(Color(0xFF252836), RoundedCornerShape(8.dp))
                        .height(28.dp)
                        .width(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Back",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Reset Password",
                color = Color.White,
                fontSize = 28.sp,
                fontFamily = montserratMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Text(
                text = "Recover your account password",
                color = Color(0xFF92929D),
                fontSize = 14.sp,
                fontFamily = montserratMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Email Address",
                color = Color(0xFFEBEBEF),
                fontSize = 12.sp,
                fontFamily = montserratMedium,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        text = "you@example.com",
                        color = Color(0xFF92929D),
                        fontSize = 14.sp,
                        fontFamily = montserratMedium,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .border(1.dp, Color(0xFF252836), RoundedCornerShape(24.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color(0xFF92929D),
                    unfocusedTextColor = Color(0xFF92929D)
                ),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    when {
                        email.isBlank() -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Please enter your email address")
                            }
                        }

                        !isValidEmail(email) -> {
                            scope.launch {
                                snackbarHostState.showSnackbar("Please enter a valid email (e.g., name@example.com)")
                            }
                        }

                        else -> {
                            val intent = Intent(context, VerificationScreenActivity::class.java)
                            intent.putExtra("email", email)
                            context.startActivity(intent)
                            (context as? Activity)?.finish()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF12CDD9))
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = montserratMedium,
                )
            }
        }
    }
}
