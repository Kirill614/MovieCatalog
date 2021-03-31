package com.example.mymoviecatalog.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviecatalog.Repository.MainRepository
import com.example.mymoviecatalog.Utils.CoroutineContextProvieder
import com.example.mymoviecatalog.data.YouTubeFilm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class YouTubeViewModel
@Inject constructor(private val mainRep: MainRepository,
                    private val contextProvider: CoroutineContextProvieder): ViewModel(){
    private val _youtubeLiveData = MutableLiveData<ViewModelViewState>()
    val youtubeLiveData = _youtubeLiveData
    val IO: CoroutineContext by lazy{Dispatchers.IO}

    fun getYoutubeFilms(playlistId: String) {
        _youtubeLiveData.value = ViewModelViewState.Loading
        viewModelScope.launch {
            val data = withContext(contextProvider.IO) {
                mainRep.getYoutubeFilms(playlistId)
            }
            _youtubeLiveData.value = ViewModelViewState.SuccessYoutubeFilms(data)
        }
    }

    sealed class ViewModelViewState(){
        object Loading: ViewModelViewState()
        data class Error(val thr: Throwable): ViewModelViewState()
        data class SuccessYoutubeFilms(val data: YouTubeFilm): ViewModelViewState()
    }
}