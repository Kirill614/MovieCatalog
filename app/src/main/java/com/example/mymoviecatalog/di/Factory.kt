package com.example.mymoviecatalog.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Provider

class Factory
@Inject constructor(
    private val viewModels: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
     val viewModelProvider = viewModels[modelClass]
        return viewModelProvider?.get() as T
    }
}