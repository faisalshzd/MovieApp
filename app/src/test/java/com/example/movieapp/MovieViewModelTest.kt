package com.example.movieapp

import app.cash.turbine.test
import com.example.movieapp.data.model.UiMovie
import com.example.movieapp.usecase.GetGenresUseCase
import com.example.movieapp.usecase.GetMoviesByGenreUseCase
import com.example.movieapp.usecase.GetPopularMoviesUseCase
import com.example.movieapp.viewmodel.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest {

    private lateinit var getGenresUseCase: GetGenresUseCase
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private lateinit var getMoviesByGenreUseCase: GetMoviesByGenreUseCase

    private val dummyGenresMap = mapOf(1 to "Action", 2 to "Comedy")

    private val dummyPopularMovies = listOf(
        UiMovie("Movie A", "/posterA.jpg", 8.0, listOf("Action"), listOf(1)),
        UiMovie("Movie B", "/posterB.jpg", 7.0, listOf("Comedy"), listOf(2))
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        getGenresUseCase = mock(GetGenresUseCase::class.java)
        getPopularMoviesUseCase = mock(GetPopularMoviesUseCase::class.java)
        getMoviesByGenreUseCase = mock(GetMoviesByGenreUseCase::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): MovieViewModel {
        return MovieViewModel(
            getGenresUseCase,
            getPopularMoviesUseCase,
            getMoviesByGenreUseCase
        )
    }

    @Test
    fun `fetchMoviesAndGenres - should fetch genres and popular movies`() = runTest {
        // Given
        val viewModel = createViewModel()
        val dummyGenresMap = mapOf(1 to "Action", 2 to "Comedy")
        val dummyPopularMovies = listOf(
            UiMovie("Movie 1", "/poster1.jpg", 7.5, listOf("Action"), listOf(1)),
            UiMovie("Movie 2", "/poster2.jpg", 6.8, listOf("Comedy"), listOf(2))
        )

        whenever(getGenresUseCase.execute(any())).thenReturn(dummyGenresMap)
        whenever(getPopularMoviesUseCase.execute(dummyGenresMap)).thenReturn(dummyPopularMovies)

        viewModel.fetchMoviesAndGenres()
        advanceUntilIdle()

        viewModel.genres.test {
            assertEquals(listOf("All", "Action", "Comedy"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.popularUiMovies.test {
            assertEquals(dummyPopularMovies, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateSelectedCategory should update selectedCategory value`() = runTest {
        val viewModel = createViewModel()
        assertEquals("All", viewModel.selectedCategory)

        viewModel.updateSelectedCategory("Comedy")
        advanceUntilIdle()

        assertEquals("Comedy", viewModel.selectedCategory)
    }

    @Test
    fun `genre API should not be called again if already cached`() = runTest {
        val viewModel = createViewModel()
        whenever(getGenresUseCase.execute(any())).thenReturn(dummyGenresMap)
        whenever(getPopularMoviesUseCase.execute(dummyGenresMap)).thenReturn(dummyPopularMovies)

        viewModel.fetchMoviesAndGenres()
        advanceUntilIdle()

        viewModel.updateSelectedCategory("All")
        advanceUntilIdle()

        verify(getGenresUseCase, times(1)).execute(any())
    }

    @Test
    fun `fetchMovies emits loading true then false`() = runTest {
        val viewModel = createViewModel()
        whenever(getGenresUseCase.execute(any())).thenReturn(dummyGenresMap)
        whenever(getPopularMoviesUseCase.execute(dummyGenresMap)).thenReturn(dummyPopularMovies)

        viewModel.isLoading.test {
            viewModel.fetchMoviesAndGenres()
            assertTrue(awaitItem())  // loading = true
            assertFalse(awaitItem()) // loading = false
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchMovies - handles exception safely`() = runTest {
        val viewModel = createViewModel()
        whenever(getGenresUseCase.execute(any())).thenThrow(RuntimeException("API error"))

        viewModel.fetchMovies()
        advanceUntilIdle()

        viewModel.popularUiMovies.test {
            assertTrue(awaitItem().isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchMovies - invalid category should not crash`() = runTest {
        val viewModel = createViewModel()
        whenever(getGenresUseCase.execute(any())).thenReturn(dummyGenresMap)
        whenever(getMoviesByGenreUseCase.execute(null)).thenReturn(emptyList())

        viewModel.fetchMovies("NonexistentCategory")
        advanceUntilIdle()

        viewModel.popularUiMovies.test {
            assertTrue(awaitItem().isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
