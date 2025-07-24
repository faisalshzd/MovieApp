package com.example.movieapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay


@Composable
fun OnboardingScreen(navController: NavController) {
    val pages: List<PageData> = koinInject ()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()

//    LaunchedEffect(Unit) {
//        while (true) {
//            delay(2000)
//            val nextPage = if (pagerState.currentPage < pages.lastIndex) {
//                pagerState.currentPage + 1
//            } else {
//                null
//            }
//
//            if (nextPage != null) {
//                pagerState.animateScrollToPage(nextPage)
//            } else {
//                navController.navigate("main") {
//                    popUpTo("onboarding") { inclusive = true }
//                }
//                break
//            }
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1D2B))
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            //OnboardingPageContent(pageData = pages[pageIndex])
            CustomOnboardingPage(pageIndex = pageIndex, pageData = pages[pageIndex])
        }

        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, bottom = 100.dp)
        ) {
            pages.indices.forEach { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(
                            width = if (isSelected) 32.dp else 10.dp,
                            height = 10.dp
                        )
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected)  Color(0xFF12CDD9) else Color(0xFF0B656E))
                )
            }
        }
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF12CDD9)),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 80.dp)
                .size(60.dp)
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        val nextPage = if (pagerState.currentPage < pages.lastIndex)
                            pagerState.currentPage + 1 else null

                        if (nextPage != null) {
                            pagerState.animateScrollToPage(nextPage)
                        } else {
                            navController.navigate("main") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxSize().padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    contentDescription = "Next",
                    tint = Color(0xFF1F1D2B),
                    modifier = Modifier.fillMaxSize()
                        .height(14.dp)
                        .width(7.dp)
                )
            }
        }
    }
}

@Composable
fun CustomOnboardingPage(pageIndex: Int, pageData: PageData) {
    when (pageIndex) {
        0 -> PageOneLayout(pageData)
        1 -> PageTwoLayout(pageData)
        2 -> PageThreeLayout(pageData)
    }
}

@Composable
fun PageOneLayout(pageData: PageData) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = (screenHeight / 2) + 50.dp
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = pageData.imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
            contentScale = ContentScale.Crop
        )

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
                .padding(top = imageHeight+46.dp)
                .padding(horizontal = 55.87.dp)
        )

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
                .padding(top = imageHeight+135.dp)
                .padding(horizontal = 55.5.dp)
        )
    }
}
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
                .padding(top = imageHeight + 37.dp) // adjust based on offset
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


@Composable
fun PageThreeLayout(pageData: PageData) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = (screenHeight / 2) + 50.dp
    val montserratMedium = FontFamily(Font(R.font.montserrat_medium, FontWeight.Medium))
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = pageData.imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .padding(top = 80.dp, end = 32.dp)
                .width(85.dp)
                .height(85.dp)
                .align(Alignment.TopEnd)
                .border(
                    width = 1.dp,
                    color = Color(0x1AFFFFFF),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF171725))
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color(0xFF171725), shape = RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.duration),
                        contentDescription = "Duration Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                Text(
                    text = "Duration",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    fontFamily = montserratMedium,
                )

                Text(
                    text = "1h 20m",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = montserratSemiBold
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 62.dp, start = 36.dp)
                .width(85.dp)
                .height(85.dp)
                .align(Alignment.TopStart)
                .border(
                    width = 1.dp,
                    color = Color(0x1AFFFFFF),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF171725))
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .background(Color(0xFF171725), shape = RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.rating),
                        contentDescription = "Duration Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                Text(
                    text = "Rating",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    fontFamily = montserratMedium,
                )

                Text(
                    text = "9 / 10",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = montserratSemiBold
                )
            }
        }
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
                .padding(top = imageHeight+46.dp)
                .padding(horizontal = 55.87.dp)
        )

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
                .padding(top = imageHeight+135.dp)
                .padding(horizontal = 55.5.dp)
        )
    }
}

