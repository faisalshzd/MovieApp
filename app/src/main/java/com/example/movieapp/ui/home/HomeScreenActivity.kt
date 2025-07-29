package com.example.movieapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.movieapp.data.model.Movie
import com.example.movieapp.viewmodel.MovieViewModel

@Composable
fun HomeScreenActivity(viewModel: MovieViewModel) {
    val genreMap by viewModel.genreMap.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMoviesAndGenres()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Most Popular", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(viewModel.popularMovies) { movie ->
                MovieCard(movie = movie, genreMap = genreMap)
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie, genreMap: Map<Int, String>) {
    val genres = movie.genre_ids.joinToString(", ") { id ->
        genreMap[id] ?: "Unknown"
    }

    Column(
        modifier = Modifier
            .padding(end = 16.dp)
            .width(140.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
            fontSize = 14.sp
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
            Text("${movie.vote_average}", color = Color.White, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = genres,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.LightGray,
            fontSize = 10.sp
        )
    }
}
