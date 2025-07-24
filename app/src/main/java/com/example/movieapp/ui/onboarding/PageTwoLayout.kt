package com.example.movieapp.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.model.PageData

@Composable
fun PageTwoLayout(pageData: PageData) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = (screenHeight / 2)+50.dp

    Box(modifier = Modifier.fillMaxSize()) {

        // Background box with color and offset from top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .background(Color(0xFF171725))
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = pageData.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y=60.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )
        }

        // Title Text
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
                .padding(top = imageHeight + 37.dp)
                .padding(horizontal = 31.dp)
        )

        // Description Text
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
                .padding(top = imageHeight + 150.dp)
                .padding(horizontal = 24.dp)
        )
    }
}

