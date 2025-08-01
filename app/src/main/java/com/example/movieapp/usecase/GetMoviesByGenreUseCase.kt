package com.example.movieapp.usecase

import com.example.movieapp.data.model.UiMovie
import com.example.movieapp.data.repository.MovieRepository

class GetMoviesByGenreUseCase(
    private val repository: MovieRepository
) {
    suspend fun execute(genreId: Int?): List<UiMovie> {
        val response = repository.getMoviesByGenre(genreId)
        return response.results.map { movie ->
            UiMovie(
                title = movie.title,
                posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                rating = movie.vote_average,
                genres = emptyList(),
                genreIds = movie.genre_ids
            )
        }
    }
}
