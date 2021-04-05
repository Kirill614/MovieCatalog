package com.example.mymoviecatalog.di

import androidx.lifecycle.ViewModel
import com.example.mymoviecatalog.viewModel.ActorsViewModel
import com.example.mymoviecatalog.viewModel.FilmViewModel
import com.example.mymoviecatalog.viewModel.YouTubeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ActorsViewModel::class)
    internal abstract fun binsActorsViewModel(viewModel: ActorsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilmViewModel::class)
    internal abstract fun binsFilmViewModel(viewModel: FilmViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(YouTubeViewModel::class)
    internal abstract fun binsYoutubeViewModel(viewModel: YouTubeViewModel): ViewModel
}