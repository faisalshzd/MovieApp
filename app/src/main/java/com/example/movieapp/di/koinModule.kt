package com.example.movieapp.di

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.movieapp.model.PageData
import com.example.movieapp.R
import org.koin.dsl.module

val koinModule = module {
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    val montserratMedium = FontFamily(Font(R.font.montserrat_medium, FontWeight.Medium))

    single<List<PageData>> {
        listOf(
            PageData(
                imageResId = R.drawable.splash1,
                title = "Lorem ipsum dolor sit amet consectetur esplicit",
                description = "Semper in cursus magna et eu varius nunc adipiscing. Elementum justo, laoreet id sem semper parturient.",
                titleFontSize = 18.sp,
                titleFontWeight = FontWeight(600),
                titleFontFamily = montserratSemiBold,
                titleColor = Color.White,
                descFontSize = 16.sp,
                descFontWeight = FontWeight(500),
                descFontFamily = montserratMedium,
                descColor = Color.LightGray
            ),
            PageData(
                imageResId = R.drawable.splash2,
                title = "Lorem ipsum dolor sit amet consectetur esplicit",
                description = "Semper in cursus magna et eu varius nunc adipiscing. Elementum justo, laoreet id sem semper parturient.",
                titleFontSize = 26.sp,
                titleFontWeight = FontWeight(600),
                titleFontFamily = montserratSemiBold,
                titleColor = Color.White,
                descFontSize = 14.sp,
                descFontWeight = FontWeight(500),
                descFontFamily = montserratMedium,
                descColor = Color(0xFF92929D)
            ),
            PageData(
                imageResId = R.drawable.splash3,
                title = "Lorem ipsum dolor sit amet consectetur esplicit",
                description = "Semper in cursus magna et eu varius nunc adipiscing. Elementum justo, laoreet id sem semper parturient.",
                titleFontSize = 18.sp,
                titleFontWeight = FontWeight(600),
                titleFontFamily = montserratSemiBold,
                titleColor = Color.White,
                descFontSize = 16.sp,
                descFontWeight = FontWeight(500),
                descFontFamily = montserratMedium,
                descColor = Color(0xFF92929D)
            ),
        )
    }
}
