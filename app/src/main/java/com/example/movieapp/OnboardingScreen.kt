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
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.movieapp.model.PageData
import com.example.movieapp.ui.onboarding.PageOneLayout
import com.example.movieapp.ui.onboarding.PageThreeLayout
import com.example.movieapp.ui.onboarding.PageTwoLayout
import kotlinx.coroutines.delay


@Composable
fun OnboardingScreen(navController: NavController) {
    val pages: List<PageData> = koinInject()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {

            delay(2000)
            val nextPage = if (pagerState.currentPage < pages.lastIndex) {
                pagerState.currentPage + 1
            } else {
                null
            }

            if (nextPage != null) {
                pagerState.animateScrollToPage(nextPage)
            } else {
                navController.navigate("login_or_signup") {
                    popUpTo("onboarding") { inclusive = true }
                }
                break
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1D2B))
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            CustomOnboardingPage(pageIndex = pageIndex, pageData = pages[pageIndex])
        }

        // Dots
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
                        .background(if (isSelected) Color(0xFF12CDD9) else Color(0xFF0B656E))
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1F1D2B))
        ) {
            // Horizontal Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIndex ->
                CustomOnboardingPage(pageIndex = pageIndex, pageData = pages[pageIndex])
            }

            // Page Indicators
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
                            .background(if (isSelected) Color(0xFF12CDD9) else Color(0xFF0B656E))
                    )
                }
            }
            // Button + Outline using Canvas
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 24.dp, bottom = 80.dp)
            ) {
                // Animate border progress based on page
                Canvas(modifier = Modifier.size(78.dp)) {
                    val strokeWidth = 8f
                    val cornerRadius = 40f
                    val gapRatio = 0.05f // 5% of each segment reserved as gap

                    // Full rounded rectangle path
                    val fullPath = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(0f, 0f, size.width, size.height),
                                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                            )
                        )
                    }

                    // Path measure
                    val pathMeasure = PathMeasure()
                    pathMeasure.setPath(fullPath, false)
                    val totalLength = pathMeasure.length

                    // Per segment
                    val segmentsToDraw = when (pagerState.currentPage) {
                        0 -> 1
                        1 -> 2
                        else -> 3
                    }

                    val totalSegments = 3
                    val baseSegmentLength = totalLength / totalSegments
                    val gapLength = baseSegmentLength * gapRatio
                    val drawLength = baseSegmentLength - gapLength
                    val offset = totalLength * 0.825f // Start from top-center clockwise

                    val finalPath = Path()

                    for (i in 0 until segmentsToDraw) {
                        val start = (offset - i * baseSegmentLength + totalLength) % totalLength
                        val end = (start - drawLength + totalLength) % totalLength

                        val segmentPath = Path()

                        if (start > end) {
                            pathMeasure.getSegment(end, start, segmentPath)
                        } else {
                            pathMeasure.getSegment(0f, start, segmentPath)
                            val remaining = Path()
                            pathMeasure.getSegment(end, totalLength, remaining)
                            segmentPath.addPath(remaining)
                        }

                        finalPath.addPath(segmentPath)
                    }


                    drawPath(
                        path = finalPath,
                        color = Color(0xFF12CDD9),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                    )
                }
                // Icon Button in the center
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF12CDD9)),
                    modifier = Modifier
                        .align(Alignment.Center)
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            contentDescription = "Next",
                            tint = Color(0xFF1F1D2B),
                            modifier = Modifier
                                .fillMaxSize()
                                .height(14.dp)
                                .width(7.dp)
                        )
                    }
                }
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