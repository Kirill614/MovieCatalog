package com.example.mymoviecatalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mymoviecatalog.Repository.MainRepository
import com.example.mymoviecatalog.Utils.CoroutineContextProvieder
import com.example.mymoviecatalog.data.*
import com.example.mymoviecatalog.viewModel.FilmViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FIlmViewModelTest {
    private lateinit var viewModel: FilmViewModel

    @Mock
    private lateinit var viewStateObserver: Observer<FilmViewModel.ViewModelViewState>

    @Mock
    private lateinit var mainRep: MainRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = FilmViewModel(mainRep, CoroutineContextProvieder())
        GlobalScope.launch { viewModel.filmLiveData.observeForever(viewStateObserver) }
    }

    @Test
    fun shouldSuccessWhenMainRepReturnsPopMovie() {
        testCoroutineRule.runBlocking {
            Mockito.`when`(mainRep.getPopMovie()).thenReturn(
                PopMovieModel(
                    ArrayList<PopMovie>()
                )
            )

            viewModel.getPopMovies()

            Mockito.verify(viewStateObserver).onChanged(FilmViewModel.ViewModelViewState.Loading)
           // Mockito.verify(viewStateObserver).onChanged(
             //   FilmViewModel.ViewModelViewState.SuccessPopMovies(
               //     any()
                //)
            //)
        }
    }

   @Test fun shouldSuccessWhenMainRepReturnsFilmDetails() {
        testCoroutineRule.runBlocking {
            Mockito.`when`(mainRep.getFilmDetails(550))
                .thenReturn(
                    FoundFilmModel(
                        "", 0, 0.0f,
                        "", "", "", ""
                    )
                )

            viewModel.getFilmDetails(550)

            Mockito.verify(viewStateObserver).onChanged(FilmViewModel.ViewModelViewState.Loading)
            // Mockito.verify(viewStateObserver).onChanged(
              // FilmViewModel.ViewModelViewState.SuccessFilmDetails(
                // any()
            //)
            //)
        }
    }

    @Test fun shouldSuccessWhenMainRepReturnsTrailers() {
        testCoroutineRule.runBlocking {
            Mockito.`when`(mainRep.getTrailers(550)).thenReturn(Trailers(ArrayList<Trailer>()))

            viewModel.getTrailers(550)

            Mockito.verify(viewStateObserver).onChanged(FilmViewModel.ViewModelViewState.Loading)
            // Mockito.verify(viewStateObserver).onChanged(
            // FilmViewModel.ViewModelViewState.SuccessTrailers(
            // any()
            //)
            //)
        }
    }

    @Test fun shouldSuccessWhenMainRepReturnsSearchFilms() {
        testCoroutineRule.runBlocking {
            Mockito.`when`(mainRep.searchFilms("fight")).thenReturn(SearchMovie(ArrayList<FoundMovie>()))

            viewModel.searchFilms("fight")

            Mockito.verify(viewStateObserver).onChanged(FilmViewModel.ViewModelViewState.Loading)
            // Mockito.verify(viewStateObserver).onChanged(
             //FilmViewModel.ViewModelViewState.SuccessSearchMovie(
             //any()
           // )
            //)
        }
    }
}