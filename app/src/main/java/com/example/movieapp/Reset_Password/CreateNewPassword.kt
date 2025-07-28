package com.example.movieapp.Reset_Password

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MovieAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

class CreateNewPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MovieAppTheme {
                val systemUiController = rememberSystemUiController()
                val backgroundColor = Color(0xFF171725)

                SideEffect {
                    systemUiController.setSystemBarsColor(backgroundColor, darkIcons = false)
                    systemUiController.setNavigationBarColor(backgroundColor, darkIcons = false)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundColor
                ) {
                    CreateNewPasswordScreen(
                        onBackClick = {
                            val intent = Intent(this, ResetPasswordActivity::class.java)
                            startActivity(intent)
                            finish()
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewPasswordScreen(
    onBackClick: () -> Unit
) {
    val montserratMedium = FontFamily(Font(R.font.montserrat_medium, FontWeight.Medium))
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
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
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF171725))
            )
        },
        containerColor = Color(0xFF171725)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Create New Password",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = montserratSemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your new password",
                color = Color(0xFF92929D),
                fontSize = 14.sp,
                fontFamily = montserratMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Text for "New Password" label
            Text(
                text = "New Password",
                color = Color(0xFFEBEBEF),
                fontSize = 12.sp,
                fontFamily = montserratMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordInputField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = "Enter new password",
                fontFamily = montserratMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Text for "Confirm Password" label
            Text(
                text = "Confirm Password",
                color = Color(0xFFEBEBEF),
                fontSize = 12.sp,
                fontFamily = montserratMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordInputField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirm new password",
                fontFamily = montserratMedium
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    // TODO: Implement password update logic
                    // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF12CDD9))
            ) {
                Text(
                    text = "Reset",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = montserratMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    fontFamily: FontFamily
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFF92929D),
                fontSize = 14.sp,
                fontFamily = fontFamily,
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (passwordVisible) R.drawable.password_eye_open else R.drawable.password_eye_off
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = Color(0xFF9CA4AB),
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        textStyle = LocalTextStyle.current.copy(
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = fontFamily
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color(0xFF92929D)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
            .border(1.dp, Color(0xFF252836), RoundedCornerShape(24.dp)), // Border and rounded corners
        singleLine = true // Ensures single line
    )
}