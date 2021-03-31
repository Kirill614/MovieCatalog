package com.example.mymoviecatalog.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviecatalog.Repository.MainRepository
import com.example.mymoviecatalog.Utils.CoroutineContextProvieder
import com.example.mymoviecatalog.data.FoundFilmModel
import com.example.mymoviecatalog.data.PopMovieModel
import com.example.mymoviecatalog.data.SearchMovie
import com.example.mymoviecatalog.data.Trailers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FilmViewModel
    @Inject constructor(private val mainRep: MainRepository,
                        private val contextProvider: CoroutineContextProvieder): ViewModel() {
    private val _filmLiveData = MutableLiveData<ViewModelViewState>()
    val filmLiveData = _filmLiveData
    private val IO: CoroutineContext by lazy {Dispatchers.IO}

    fun getPopMovies(){
        _filmLiveData.value = ViewModelViewState.Loading
        viewModelScope.launch {
            val data = withContext(contextProvider.IO){
                mainRep.getPopMovie()
            }
            _filmLiveData.value = ViewModelViewState.SuccessPopMovies(data)
        }
    }

    fun getFilmDetails(id: Int){
        _filmLiveData.value = ViewModelViewState.Loading
        viewModelScope.launch {
            val data = withContext(contextProvider.IO){
                mainRep.getFilmDetails(id)
            }
            _filmLiveData.value = ViewModelViewState.SuccessFilmDetails(data)
        }
    }

    fun getTrailers(id: Int){
        _filmLiveData.value = ViewModelViewState.Loading
        viewModelScope.launch {
            val data = withContext(IO){
                mainRep.getTrailers(id)
            }
            _filmLiveData.value = ViewModelViewState.SuccessTrailers(data)
        }
    }

    fun searchFilms(query: String){
        _filmLiveData.value = ViewModelViewState.Loading
        viewModelScope.launch {
            val data = withContext(IO){
                mainRep.searchFilms(query)
            }
            _filmLiveData.value = ViewModelViewState.SuccessSearchMovie(data)
        }
    }

    sealed class ViewModelViewState{
        object Loading: ViewModelViewState()
        data class Error(val thr: Throwable): ViewModelViewState()
        data class SuccessPopMovies(val data: PopMovieModel): ViewModelViewState()
        data class SuccessFilmDetails(val data: FoundFilmModel): ViewModelViewState()
        data class SuccessTrailers(val data: Trailers): ViewModelViewState()
        data class SuccessSearchMovie(val data: SearchMovie): ViewModelViewState()
    }

}