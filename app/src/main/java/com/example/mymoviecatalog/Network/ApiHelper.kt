package com.example.mymoviecatalog.Network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApiHelper
@Inject constructor(private val apiService: ApiService) {
    val youtube_api_key = "AIzaSyBEHb0pKKo-tWVBSDF_3qZ9mTIy_tI36pw"
    open suspend fun getPersons() = apiService.getPersons( "en-US")
    open suspend fun getPersonDetails(id: Int) = apiService.getPersonDetails(id)
    open suspend fun getPopMovies() = apiService.getPopMovie( "ru")
    open suspend fun searchFilms(query: String) = apiService.searchFilms( query, "ru")
    open suspend fun getFilmDetails(id: Int) = apiService.getFilmDetails(id, "ru")
    open suspend fun getYoutubeFilms(playlistId: String) =
        apiService.getYoutubeFilms("snippet", playlistId, 100, youtube_api_key)

    open suspend fun getTrailers(id: Int) = apiService.getTrailers(id)
}