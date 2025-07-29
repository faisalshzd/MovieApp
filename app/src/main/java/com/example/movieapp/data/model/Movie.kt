package com.example.movieapp.data.model

data class MovieResponse(
    val results: List<Movie>
)

data class Movie(
    val title: String,
    val poster_path: String,
    val vote_average: Double,
    val genre_ids: List<Int>
)