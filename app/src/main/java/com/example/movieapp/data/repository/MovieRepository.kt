package com.example.movieapp.data.repository

import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.network.MovieApiService

class MovieRepository(private val apiService: MovieApiService) {
    suspend fun getPopularMovies(): List<Movie> {
        return apiService.getPopularMovies().results
    }
    private var genreMap: Map<Int, String>? = null

    suspend fun getGenres(apiKey: String): Map<Int, String> {
        if (genreMap == null) {
            val response = apiService.getMovieGenres(apiKey)
            genreMap = response.genres.associateBy({ it.id }, { it.name })
        }
        return genreMap!!
    }
}
