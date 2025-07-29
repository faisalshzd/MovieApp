package com.example.movieapp.data.repository

import com.example.movieapp.data.model.GenreResponse
import com.example.movieapp.data.model.MovieResponse
import com.example.movieapp.data.network.MovieApiService

class MovieRepositoryImpl(private val apiService: MovieApiService) : MovieRepository {
    override suspend fun getPopularMovies(): MovieResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getMovieGenres(apiKey: String): GenreResponse {
        return apiService.getMovieGenres(apiKey)
    }
}
