package com.example.movieapp.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.movieapp.model.PageData

@Composable
fun PageOneLayout(pageData: PageData) {
    BaseOnboardingPageLayout(
        pageData = pageData,
        topImage = {
            val imageHeight = (LocalConfiguration.current.screenHeightDp.dp / 2) + 50.dp
            Image(
                painter = painterResource(id = pageData.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),
                contentScale = ContentScale.Crop
            )
        }
    )
}
