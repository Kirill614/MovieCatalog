package com.example.mymoviecatalog.Network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApiHelper
@Inject constructor(private val apiService: ApiService){
    val api_key = "64ce00b71164fc4f02e665d2924b7ca3"
    val youtube_api_key = "AIzaSyBEHb0pKKo-tWVBSDF_3qZ9mTIy_tI36pw"
    open suspend fun getPersons() = apiService.getPersons(api_key,"en-US")
    open suspend fun getPersonDetails(id: Int) = apiService.getPersonDetails(id,api_key)
    open suspend fun getPopMovies() = apiService.getPopMovie(api_key,"ru")
    open suspend fun searchFilms(query: String) = apiService.searchFilms(api_key,query,"ru")
    open suspend fun getFilmDetails(id: Int) = apiService.getFilmDetails(id,api_key,"ru")
    open suspend fun getYoutubeFilms(playlistId: String) =
        apiService.getYoutubeFilms("snippet",playlistId,100,youtube_api_key)
    open suspend fun getTrailers(id: Int) = apiService.getTrailers(id,api_key)
}