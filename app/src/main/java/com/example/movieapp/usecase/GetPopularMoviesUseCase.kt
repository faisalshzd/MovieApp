package com.example.movieapp.usecase

import com.example.movieapp.data.model.UiMovie
import com.example.movieapp.data.repository.MovieRepository

class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend fun execute(genreMap: Map<Int, String>): List<UiMovie> {
        // Extract results from MovieResponse
        val movieResponse = repository.getPopularMovies()
        return movieResponse.results.map { movie ->
            UiMovie(
                title = movie.title,
                posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                rating = movie.vote_average,
                genres = movie.genre_ids.joinToString(", ") { id ->
                    genreMap[id] ?: "Unknown"
                }
            )
        }
    }
}
