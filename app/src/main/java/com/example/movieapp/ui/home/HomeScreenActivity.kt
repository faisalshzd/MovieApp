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
import androidx.compose.ui.layout.ContentScale
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
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer



@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenActivity(viewModel: MovieViewModel) {
    val pagerState = rememberPagerState()
    val categories by viewModel.genres.collectAsState()
    val selectedCategory = viewModel.selectedCategory
    var searchQuery by remember { mutableStateOf("") }
    val montserratMedium = FontFamily(Font(R.font.montserrat_medium, FontWeight.Medium))
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    val isLoading by viewModel.isLoading.collectAsState()
    val popularMovies by viewModel.popularUiMovies.collectAsState()


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
               // .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))

            // Top Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
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
                        Text(
                            "Hello, Smith",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = montserratSemiBold
                        )
                        Text(
                            "Let’s stream your favorite movie",
                            color = Color(0xFF92929D),
                            fontSize = 12.sp
                        )
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

            // Search Field
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search a title..", color = Color(0xFF92929D)) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFF92929D)
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = null,
                        tint = Color(0xFF92929D)
                    )
                },
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
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                shape = CircleShape,
                singleLine = true
            )

            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
            // Pager
            HorizontalPager(
                count = 3,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) { page ->
                val movie = popularMovies.getOrNull(page)
                val imageUrl =
                    movie?.posterUrl ?: "https://via.placeholder.com/500x750.png?text=No+Image"
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(0.dp))
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Black Panther: Wakanda Forever",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = montserratSemiBold
                        )
                        Text(
                            text = "On March 2, 2022",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = montserratMedium
                        )
                    }
                }
            }
            }


            Spacer(modifier = Modifier.height(8.dp))

            CustomPagerIndicator(
                pagerState = pagerState,
                pageCount = 3,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                activeColor = Color(0xFF12CDD9),
                inactiveColor = Color(0xFF11535B)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Categories
            Text("Categories", color = Color.White, fontSize = 16.sp, fontFamily = montserratSemiBold, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(15.dp))
            LazyRow {

                items(categories)
                { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .border(
                                width = 5.dp,
                                color = if (isSelected) Color(0xFF252836) else Color.Transparent,
                                shape = RoundedCornerShape(20)
                            )
                            .background(
                                color = if (isSelected) Color(0xFF252836) else Color.Transparent,
                                shape = RoundedCornerShape(20)
                            )
                            .clickable { viewModel.updateSelectedCategory(category) }
                            .padding(horizontal = 7.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) Color(0xFF12CDD9) else Color.White,
                            fontSize = 12.sp,
                            fontFamily = montserratMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Most Popular
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Most popular", color = Color.White, fontSize = 16.sp, fontFamily = montserratSemiBold)
                Text("See All", color = Color(0xFF12CDD9), fontSize = 14.sp, fontFamily = montserratMedium)
            }

            // Selected movie Cards
            when {
                isLoading -> {
                    LazyRow {
                        items(5) { MovieCardShimmer() }
                    }
                }

                popularMovies.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF12CDD9),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No movies found in this category.",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                    }
                }

                else -> {
                    LazyRow {
                        items(popularMovies) { movie ->
                            MovieCard(movie = movie)
                        }
                    }
                }
            }


        }
        }
    }

@Composable
fun MovieCard(movie: UiMovie) {
    val montserratMedium = FontFamily(Font(R.font.montserrat_medium, FontWeight.Medium))
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(end = 5.dp)
            .padding(start=16.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            // Movie Poster
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = "movie image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Rating badge (top-right)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(
                        color = Color(0x52252836),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFF8700),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format("%.1f", movie.rating),
                        color = Color(0xFFFF8700),
                        fontSize = 12.sp,
                        fontFamily = montserratSemiBold
                    )
                }
            }
        }
        // Bottom container for title and genre
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = Color(0xFF252836),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = montserratSemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = movie.genres.joinToString(", "),
                color = Color(0xFF8E8E96),
                fontSize = 12.sp,
                fontFamily = montserratMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
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
                                .background(Color(0xFF252836), RoundedCornerShape(20.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = item.first,
                                contentDescription = item.second,
                                tint = Color(0xFF12CDD9),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = item.second,
                                color = Color(0xFF12CDD9),
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                        }
                    } else {
                        Icon(
                            imageVector = item.first,
                            contentDescription = item.second,
                            tint = Color(0xFF92929D),
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

@Composable
fun MovieCardShimmer() {
    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(end = 5.dp, start = 16.dp)
    ) {
        // Shimmer Poster
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(),
                            color = Color(0xFF3A3A4B),
                    shape = RoundedCornerShape(8.dp)
                )
        )

    }
}
