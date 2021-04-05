package com.example.mymoviecatalog.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviecatalog.Repository.MainRepository
import com.example.mymoviecatalog.Utils.CoroutineContextProvieder
import com.example.mymoviecatalog.data.ActorDetailsModel
import com.example.mymoviecatalog.data.PersonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ActorsViewModel
@Inject constructor(
    private val mainRep: MainRepository,
    private val contextProvider: CoroutineContextProvieder
) : ViewModel() {
    private val _actorsLiveData = MutableLiveData<ViewModelViewState>()
    val actorsLiveData = _actorsLiveData
    //val IO: CoroutineContext by lazy { Dispatchers.IO }
    //val IO: CoroutineContext = Dispatchers.IO

    fun getPersons() {
        _actorsLiveData.value = ViewModelViewState.Loading
        viewModelScope.launch {
            val data = withContext(contextProvider.IO) {
                mainRep.getPersons()
            }
            _actorsLiveData.value = ViewModelViewState.SuccessActors(data)
        }
    }

    fun getActorDetails(id: Int) {
        _actorsLiveData.value = ViewModelViewState.Loading
        viewModelScope.launch {
            val data = withContext(contextProvider.IO) {
                mainRep.getPersonDetails(id)
            }
            _actorsLiveData.value = ViewModelViewState.SuccessActorDetails(data)
        }
    }

    sealed class ViewModelViewState {
        object Loading : ViewModelViewState()
        data class Error(val trowable: Throwable) : ViewModelViewState()
        data class SuccessActors(val data: PersonModel) : ViewModelViewState()
        data class SuccessActorDetails(val data: ActorDetailsModel) : ViewModelViewState()
    }

}