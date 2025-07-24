package com.example.movieapp.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.model.PageData
import com.example.movieapp.R

@Composable
fun PageThreeLayout(pageData: PageData) {
    val montserratMedium = FontFamily(Font(R.font.montserrat_medium, FontWeight.Medium))
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))

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
        },
        overlayContent = {
            Box(modifier = Modifier.fillMaxSize()) {
                // Duration box
                Box(
                    modifier = Modifier
                        .padding(top = 80.dp, end = 32.dp)
                        .width(85.dp)
                        .height(85.dp)
                        .align(Alignment.TopEnd)
                        .border(1.dp, Color(0x1AFFFFFF), RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF171725))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.duration),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            "Duration",
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            fontFamily = montserratMedium
                        )
                        Text(
                            "1h 20m",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = montserratSemiBold
                        )
                    }
                }

                // Rating box
                Box(
                    modifier = Modifier
                        .padding(top = 62.dp, start = 36.dp)
                        .width(85.dp)
                        .height(85.dp)
                        .align(Alignment.TopStart)
                        .border(1.dp, Color(0x1AFFFFFF), RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF171725))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.rating),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            "Rating",
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            fontFamily = montserratMedium
                        )
                        Text(
                            "9 / 10",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = montserratSemiBold
                        )
                    }
                }
            }
        }
    )
}
