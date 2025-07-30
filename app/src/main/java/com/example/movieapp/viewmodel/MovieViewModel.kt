package com.example.movieapp.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.UiMovie
import com.example.movieapp.usecase.GetGenresUseCase
import com.example.movieapp.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getGenresUseCase: GetGenresUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    var isLoading by mutableStateOf(true)
        private set

    var popularUiMovies by mutableStateOf<List<UiMovie>>(emptyList())
        private set

    private val _genres = MutableStateFlow<List<String>>(listOf("All"))
    val genres: StateFlow<List<String>> get() = _genres


    var selectedCategory by mutableStateOf("All")
        private set

    val filteredMovies: List<UiMovie>
        get() = if (selectedCategory == "All") {
            popularUiMovies
        } else {
            popularUiMovies.filter { movie ->
                selectedCategory in movie.genres
            }
        }

    fun fetchMoviesAndGenres(apiKey: String = "13fe289de01d157201e39ab655a5ed97") {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading = true
                val genresMap = getGenresUseCase.execute(apiKey)
                _genres.value = listOf("All") + genresMap.values.sorted()
                popularUiMovies = getPopularMoviesUseCase.execute(genresMap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                isLoading = false
            }
        }
    }

    fun updateSelectedCategory(category: String) {
        selectedCategory = category
    }
}
