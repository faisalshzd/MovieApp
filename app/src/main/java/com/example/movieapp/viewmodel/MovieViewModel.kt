package com.example.movieapp.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.UiMovie
import com.example.movieapp.usecase.GetGenresUseCase
import com.example.movieapp.usecase.GetMoviesByGenreUseCase
import com.example.movieapp.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getGenresUseCase: GetGenresUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase
) : ViewModel() {

    var isLoading by mutableStateOf(true)
        private set

    var popularUiMovies by mutableStateOf<List<UiMovie>>(emptyList())
        private set

    private val _genres = MutableStateFlow<List<String>>(listOf("All"))
    val genres: StateFlow<List<String>> get() = _genres

    var selectedCategory by mutableStateOf("All")
        private set

    private var genreMapCache: Map<Int, String> = emptyMap()

    fun fetchMovies(category: String = "All", apiKey: String = "13fe289de01d157201e39ab655a5ed97") {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            try {
                Log.d("API_CALL", "Fetching movies for category: $category")

                // Only call API if cache is empty
                if (genreMapCache.isEmpty()) {
                    val genresMap = getGenresUseCase.execute(apiKey)
                    genreMapCache = genresMap
                    _genres.value = listOf("All") + genresMap.values.sorted()
                }

                // Use cached map
                val genresMap = genreMapCache

                // Fetch movies based on category
                popularUiMovies = if (category == "All") {
                    getPopularMoviesUseCase.execute(genresMap)
                } else {
                    val genreId = genresMap.filterValues { it == category }.keys.firstOrNull()
                    getMoviesByGenreUseCase.execute(genreId).map { movie ->
                        movie.copy(genres = movie.genreIds.mapNotNull { genresMap[it] })
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }


    fun fetchMoviesAndGenres(apiKey: String = "13fe289de01d157201e39ab655a5ed97") {
        fetchMovies(category = "All", apiKey = apiKey)
    }

    fun updateSelectedCategory(category: String, apiKey: String = "13fe289de01d157201e39ab655a5ed97") {
        selectedCategory = category
        fetchMovies(category, apiKey)
    }
}
