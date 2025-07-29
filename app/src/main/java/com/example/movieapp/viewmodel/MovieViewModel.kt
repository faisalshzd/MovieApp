package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    // Movie list exposed to UI
    var popularMovies by mutableStateOf<List<Movie>>(emptyList())
        private set

    // Genre map: genreId -> genreName
    private val _genreMap = MutableStateFlow<Map<Int, String>>(emptyMap())
    val genreMap: StateFlow<Map<Int, String>> = _genreMap

    fun fetchMoviesAndGenres(apiKey: String = "13fe289de01d157201e39ab655a5ed97") {
        viewModelScope.launch {
            try {
                // Fetch and store genres
                val genres = repository.getGenres(apiKey)
                _genreMap.value = genres

                // Fetch and store movies
                popularMovies = repository.getPopularMovies()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
