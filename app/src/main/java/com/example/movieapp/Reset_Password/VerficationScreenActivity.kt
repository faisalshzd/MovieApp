package com.example.movieapp.Reset_Password

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MovieAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.core.view.WindowCompat

class VerificationScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val emailFromIntent = intent.getStringExtra("email") ?: "example@gmail.com"

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MovieAppTheme {
                val systemUiController = rememberSystemUiController()
                val backgroundColor = Color(0xFF171725)

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color(0xFF171725),
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
                    VerificationScreen(
                        email = emailFromIntent,
                        onBackClick = {
                            finish()
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

                        },
                        onResendClick = { /* TODO: Implement resend logic */ },
                        onContinue = {
                            val intent = Intent(this, CreateNewPasswordActivity::class.java)
                            startActivity(intent)
                            finish()
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }

                    )
                }
            }
        }
    }
}

@Composable
fun VerificationScreen(
    email: String,
    onBackClick: () -> Unit,
    onResendClick: () -> Unit,
    onContinue:()->Unit
) {
    val otpLength = 4
    val otpValues = remember { mutableStateListOf(*Array(otpLength) { "" }) }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    val focusedIndex = remember { mutableStateOf(-1) }

    val montserratMedium = FontFamily(Font(R.font.montserrat_medium, FontWeight.Medium))
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF171725))
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 24.dp)
    ) {
        // Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
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
            text = "Verifying Your Account",
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = montserratSemiBold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color(0xFF9CA4AB))) {
                    append("We have just sent you 4 digit code via your\nemail ")
                }
                withStyle(style = SpanStyle(color = Color.White)) {
                    append(email)
                }
            },
            fontSize = 14.sp,
            fontFamily = montserratMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(otpLength) { index ->
                OtpInputField(
                    value = otpValues[index],
                    onValueChange = { newValue ->
                        if (newValue.length <= 1) {
                            otpValues[index] = newValue
                            if (newValue.isNotEmpty() && index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequesters[index]),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    autofocus = index == 0,
                    isFocused = focusedIndex.value == index,
                    onFocusChange = { focused ->
                        if (focused) focusedIndex.value = index
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                onContinue()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top=64.dp)
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF12CDD9))
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = montserratMedium,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 42.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Didn't receive code? ",
                color = Color(0xFF9CA4AB),
                fontSize = 16.sp,
                fontFamily = montserratMedium,
            )
            Text(
                text = "Resend",
                color = Color(0xFF12CDD9),
                fontSize = 16.sp,
                fontFamily = montserratMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onResendClick() }
            )
        }
    }
}

@Composable
fun OtpInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    autofocus: Boolean = false,
    isFocused: Boolean = false,
    onFocusChange: (Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(autofocus) {
        if (autofocus) {
            focusRequester.requestFocus()
        }
    }

    BasicTextField(
        value = TextFieldValue(text = value, selection = TextRange(value.length)),
        onValueChange = { onValueChange(it.text) },
        modifier = modifier
            .width(72.dp)
            .height(72.dp)
            .background(Color.Transparent, RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = if (isFocused) Color(0xFF12CDD9) else Color(0xFF252836),
                shape = RoundedCornerShape(16.dp)
            )
            .onFocusChanged { focusState ->
                onFocusChange(focusState.isFocused)
            }
            .focusRequester(focusRequester),
        keyboardOptions = keyboardOptions,
        textStyle = LocalTextStyle.current.copy(
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            fontFamily = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
        ),
        singleLine = true,
        cursorBrush = SolidColor(Color(0xFF12CDD9)),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        }
    )
}
