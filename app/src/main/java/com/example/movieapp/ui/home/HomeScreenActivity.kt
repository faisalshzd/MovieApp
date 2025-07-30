package com.example.movieapp.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.data.model.UiMovie
import com.example.movieapp.viewmodel.MovieViewModel
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenActivity(viewModel: MovieViewModel) {
    val pagerState = rememberPagerState()
    val categories = listOf("All", "Comedy", "Animation", "Documentary")
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    val montserratMedium = FontFamily(Font(R.font.montserrat_medium, FontWeight.Medium))
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))

    LaunchedEffect(Unit) {
        viewModel.fetchMoviesAndGenres()
    }

    Scaffold(
        bottomBar = { BottomNavBar() },
        containerColor = Color(0xFF171725)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))

            // Top Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, Color.White, CircleShape)
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text("Hello, Smith", color = Color.White, fontSize = 16.sp, fontFamily = montserratSemiBold)
                        Text("Let’s stream your favorite movie", color = Color(0xFF92929D), fontSize = 12.sp)
                    }
                }
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(Modifier.height(33.dp))

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search a title..", color = Color(0xFF92929D)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF92929D)) },
                trailingIcon = { Icon(Icons.Default.FilterList, contentDescription = null, tint = Color(0xFF92929D)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF252836),
                    unfocusedContainerColor = Color(0xFF252836),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(color = Color(0xFF92929D)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = CircleShape,
                singleLine = true
            )
            Spacer(Modifier.height(24.dp))

            // Pager
            HorizontalPager(
                count = 3,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) { page ->
                val movie = viewModel.popularUiMovies.getOrNull(page)
                val imageUrl = movie?.posterUrl ?: "https://via.placeholder.com/500x750.png?text=No+Image"

                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Pager Indicator
            CustomPagerIndicator(
                pagerState = pagerState,
                pageCount = 3, // or however many pages you have
                modifier = Modifier.align(Alignment.CenterHorizontally),
                activeColor = Color(0xFF12CDD9),
                inactiveColor = Color(0xFF11535B)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Categories
            Text("Categories", color = Color.White, fontSize = 16.sp, fontFamily = montserratSemiBold)
            Spacer(Modifier.height(15.dp))
            val chipShape = RoundedCornerShape(25)
            LazyRow {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) Color(0xFF252836) else Color.Transparent,
                                shape = chipShape
                            )
                            .background(
                                color = if (isSelected) Color(0xFF252836) else Color.Transparent,
                                shape = chipShape
                            )
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) Color(0xFF12CDD9) else Color.White,
                            fontSize = 12.sp,
                            fontFamily=montserratMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Most Popular Header
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Most popular", color = Color.White, fontSize = 16.sp, fontFamily = montserratSemiBold)
                Text("See All", color = Color(0xFF12CDD9), fontSize = 14.sp, fontFamily = montserratMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Movie Cards
            LazyRow {
                items(viewModel.popularUiMovies) { movie ->
                    MovieCard(movie = movie)
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: UiMovie) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .padding(end = 16.dp)
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = movie.title,
            color = Color.White,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow, modifier = Modifier.size(16.dp))
            Text(text = "${movie.rating}", color = Color.White, fontSize = 12.sp)
        }
        Text(text = movie.genres, color = Color.LightGray, fontSize = 10.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF12CDD9),
    inactiveColor: Color = Color(0xFF11535B)
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        repeat(pageCount) { index ->
            val isSelected = pagerState.currentPage == index
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(if (isSelected) 18.dp else 6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(if (isSelected) activeColor else inactiveColor)
            )
        }
    }
}


@Composable
fun BottomNavBar() {
    var selectedIndex by remember { mutableStateOf(0) }

    val items = listOf(
        Icons.Default.Home to "Home",
        Icons.Default.Search to "Search",
        Icons.Default.Download to "Downloads",
        Icons.Default.Person to "Profile"
    )

    NavigationBar(
        containerColor = Color(0xFF171725),
        contentColor = Color.White
    ) {
        items.forEachIndexed { index, item ->
            val selected = selectedIndex == index

            NavigationBarItem(
                selected = selected,
                onClick = { selectedIndex = index },
                icon = {
                    if (selected) {
                        // Icon and Text side by side in a rounded background
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(20.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = item.first,
                                contentDescription = item.second,
                                tint = Color(0xFF171725),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = item.second,
                                color = Color(0xFF171725),
                                fontSize = 12.sp,
                                maxLines = 1,                  // Limits to one line
                                overflow = TextOverflow.Ellipsis // Adds "..." if it overflows
                            )

                        }
                    } else {
                        Icon(
                            imageVector = item.first,
                            contentDescription = item.second,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


