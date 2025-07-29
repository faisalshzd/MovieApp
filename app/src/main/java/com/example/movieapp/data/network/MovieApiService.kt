package com.example.movieapp.data.network

import com.example.movieapp.data.model.GenreResponse
import com.example.movieapp.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "13fe289de01d157201e39ab655a5ed97"
    ): MovieResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = "13fe289de01d157201e39ab655a5ed97"
    ): GenreResponse
}
