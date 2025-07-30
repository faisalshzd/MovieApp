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

    var popularUiMovies by mutableStateOf<List<UiMovie>>(emptyList())
        private set

    private val _genreMap = MutableStateFlow<Map<Int, String>>(emptyMap())

    fun fetchMoviesAndGenres(apiKey: String = "13fe289de01d157201e39ab655a5ed97") {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val genres = getGenresUseCase.execute(apiKey)
                _genreMap.value = genres


                popularUiMovies = getPopularMoviesUseCase.execute(genres)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
