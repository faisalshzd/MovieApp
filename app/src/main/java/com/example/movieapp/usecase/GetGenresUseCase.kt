package com.example.movieapp.usecase

import com.example.movieapp.data.repository.MovieRepository

class GetGenresUseCase(private val repository: MovieRepository) {
    suspend fun execute(apiKey: String): Map<Int, String> {
        val genreResponse = repository.getMovieGenres(apiKey)
        return genreResponse.genres.associateBy({ it.id }, { it.name })
    }
}
