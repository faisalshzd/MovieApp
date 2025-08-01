package com.example.movieapp.data.repository

import com.example.movieapp.data.model.GenreResponse
import com.example.movieapp.data.model.MovieResponse

interface MovieRepository {
    suspend fun getPopularMovies(): MovieResponse
    suspend fun getMovieGenres(apiKey: String): GenreResponse

    suspend fun getMoviesByGenre(genreId: Int?): MovieResponse
}
