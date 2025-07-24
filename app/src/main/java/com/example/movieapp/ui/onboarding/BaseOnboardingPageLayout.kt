package com.example.movieapp.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.model.PageData

@Composable
fun BaseOnboardingPageLayout(
    pageData: PageData,
    topImage: @Composable () -> Unit,
    overlayContent: @Composable () -> Unit = {},
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = (screenHeight / 2) + 50.dp

    Box(modifier = Modifier.fillMaxSize()) {
        // Top image composable
        topImage()

        // Overlay content like duration/rating (optional)
        overlayContent()

        // Title
        Text(
            text = pageData.title,
            color = pageData.titleColor,
            fontSize = pageData.titleFontSize,
            fontWeight = pageData.titleFontWeight,
            fontFamily = pageData.titleFontFamily,
            letterSpacing = 0.13.sp,
            lineHeight = pageData.titleFontSize * 1.8,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = imageHeight + 46.dp)
                .padding(horizontal = 55.87.dp)
        )

        // Description
        Text(
            text = pageData.description,
            color = pageData.descColor,
            fontSize = pageData.descFontSize,
            fontWeight = pageData.descFontWeight,
            fontFamily = pageData.descFontFamily,
            letterSpacing = 0.12.sp,
            lineHeight = pageData.descFontSize * 1.6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = imageHeight + 135.dp)
                .padding(horizontal = 55.5.dp)
        )
    }
}
