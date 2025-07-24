package com.example.movieapp.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

data class PageData(
    val imageResId: Int,
    val title: String,
    val description: String,

    val titleFontSize: TextUnit,
    val titleFontWeight: FontWeight,
    val titleFontFamily: FontFamily,
    val titleColor: Color,

    val descFontSize: TextUnit,
    val descFontWeight: FontWeight,
    val descFontFamily: FontFamily,
    val descColor: Color,
)