package com.example.movieapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))

    //Coroutine-based navigation after delay
    LaunchedEffect(Unit) {
        delay(1500)
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.cn_tv),
            contentDescription = "My Image",
            modifier = Modifier
                .padding(start = 157.dp,top=379.dp)
                .width(88.dp)
                .height(88.dp)
        )

        Text(
            text = "CINEMAX",
            color = Color(0xFF12CDD9),
            fontSize = 28.sp,
            fontWeight = FontWeight(600),
            fontFamily = montserratSemiBold,
            letterSpacing = 0.12.sp,
            modifier = Modifier
                .padding(top = 485.dp, start = 133.dp)
        )
    }
}