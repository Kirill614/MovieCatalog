package com.example.mymoviecatalog.di

import android.content.Context
import com.example.mymoviecatalog.base.BaseFragment
import com.example.mymoviecatalog.ui.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class,NetworkModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context?): Builder
        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(activity: ActorDetailsActivity)
    fun inject(fragment: FilmSearchFragment)
    fun inject(activity: FilmDetailsActivity)
    fun inject(fragment: InfoFragment)
    fun inject(fragment: YoutubeFilmFragment)
    fun inject(fragment: BaseFragment)
}