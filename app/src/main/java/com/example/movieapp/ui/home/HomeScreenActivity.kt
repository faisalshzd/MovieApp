package com.example.movieapp.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
    val genreMap by viewModel.genreMap.collectAsState()
    val pagerState = rememberPagerState()
    val categories = listOf("All", "Comedy", "Animation", "Dokumenter")
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
                        Text("Hello, Smith", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("Let’s stream your favorite movie", color = Color.Gray, fontSize = 12.sp)
                    }
                }
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search a title..", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) },
                trailingIcon = { Icon(Icons.Default.FilterList, contentDescription = null, tint = Color.White) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1E1E1E),
                    unfocusedContainerColor = Color(0xFF1E1E1E),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = CircleShape,
                singleLine = true
            )
            Spacer(Modifier.height(20.dp))

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
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Pager Indicator
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color(0xFF00FFFF),
                inactiveColor = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Categories
            Text("Categories", color = Color.White, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
            LazyRow {
                items(categories) { category ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .border(
                                width = 1.dp,
                                color = if (category == selectedCategory) Color(0xFF00FFFF) else Color.Transparent,
                                shape = CircleShape
                            )
                            .background(
                                if (category == selectedCategory) Color(0xFF1E1E1E) else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = category, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Most Popular Header
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Most popular", color = Color.White, fontSize = 18.sp)
                Text("See All", color = Color(0xFF00FFFF), fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(36.dp)
                            .background(
                                if (selected) Color.White else Color.Transparent,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.first,
                            contentDescription = item.second,
                            tint = if (selected) Color(0xFF171725) else Color.White
                        )
                    }
                },
                label = {
                    Text(
                        text = item.second,
                        color = if (selected) Color.White else Color.LightGray,
                        fontSize = 12.sp
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
