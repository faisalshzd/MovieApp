package com.example.movieapp.Login_Signup_Screeens.Login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Login_or_Sign() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Welcome to CINEMAX!", modifier = Modifier.padding(32.dp))
    }
}
