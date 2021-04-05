package com.example.mymoviecatalog.Repository

import com.example.mymoviecatalog.Network.ApiHelper
import javax.inject.Inject

open class MainRepository
@Inject constructor(private val apiHelper: ApiHelper) {
    open suspend fun getPersons() = apiHelper.getPersons()
    open suspend fun getPersonDetails(id: Int) = apiHelper.getPersonDetails(id)
    open suspend fun getPopMovie() = apiHelper.getPopMovies()
    open suspend fun searchFilms(query: String) = apiHelper.searchFilms(query)
    open suspend fun getFilmDetails(id: Int) = apiHelper.getFilmDetails(id)
    open suspend fun getYoutubeFilms(playlistId: String) = apiHelper.getYoutubeFilms(playlistId)
    open suspend fun getTrailers(id: Int) = apiHelper.getTrailers(id)
}