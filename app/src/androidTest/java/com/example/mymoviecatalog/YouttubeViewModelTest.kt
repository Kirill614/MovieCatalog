package com.example.mymoviecatalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mymoviecatalog.Repository.MainRepository
import com.example.mymoviecatalog.Utils.CoroutineContextProvieder
import com.example.mymoviecatalog.data.Film
import com.example.mymoviecatalog.data.YouTubeFilm
import com.example.mymoviecatalog.viewModel.YouTubeViewModel
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class YouttubeViewModelTest {
    private lateinit var viewModel: YouTubeViewModel
    @Mock
    private lateinit var viewStateObserver: Observer<YouTubeViewModel.ViewModelViewState>
    @Mock
    private lateinit var mainRep: MainRepository
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewModel = YouTubeViewModel(mainRep, CoroutineContextProvieder())
        viewModel.youtubeLiveData.observeForever(viewStateObserver)
    }
    @Test
    fun shouldSuccessWhenMainRepReturnsYoutubeFilms(){
        testCoroutineRule.runBlocking {
            Mockito.`when`(mainRep.getYoutubeFilms("")).thenReturn(
                YouTubeFilm(
                    ArrayList<Film>()
                )
            )

            viewModel.getYoutubeFilms("")

            verify(viewStateObserver).onChanged(YouTubeViewModel.ViewModelViewState.Loading)
        }
    }
}