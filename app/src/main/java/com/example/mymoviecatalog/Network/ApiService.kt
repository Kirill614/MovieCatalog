package com.example.mymoviecatalog.Network

import com.example.mymoviecatalog.data.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object{
        private const val PERSON_POPULAR = "person/popular"
        private const val PERSON_DETAILS = "person/{person_id}"
        private const val MOVIE_POPULAR = "/3/movie/popular"
        private const val MOVIE_DETAILS = "movie/{movie_id}"
        private const val YOUTUBE_FILMS_URL = "https://youtube.googleapis.com/youtube/v3/playlistItems?"
        private const val MOVIE_TRAILERS = "movie/{movie_id}/videos"
        private const val MOVIE_SEARCH = "/3/search/movie"
        private const val PARAM_LANGUAGE = "language"
        private const val MOVIE_ID = "movie_id"
        const val BASE_URL_IMAGE_TMDB = "https://image.tmdb.org/t/p/w500"
    }
    @GET(PERSON_POPULAR)
    open suspend fun getPersons(
        @Query(PARAM_LANGUAGE) lang: String
    ): PersonModel

    @GET(PERSON_DETAILS)
    open suspend fun getPersonDetails(
        @Path("person_id") id: Int
    ): ActorDetailsModel

    @GET(MOVIE_POPULAR)
    open suspend fun getPopMovie(
        @Query(PARAM_LANGUAGE) lang: String
    ): PopMovies

    @GET(MOVIE_SEARCH)
    open suspend fun searchFilms(
        @Query("query") query: String,
        @Query(PARAM_LANGUAGE) lang: String
    ): MovieSearch

    @GET(MOVIE_DETAILS)
    open suspend fun getFilmDetails(
        @Path(MOVIE_ID) id: Int,
        @Query(PARAM_LANGUAGE) lang: String
    ): FilmDetails

    @GET(YOUTUBE_FILMS_URL)
    open suspend fun getYoutubeFilms(
        @Query("part") part: String, @Query("playlistId") id: String,
        @Query("maxResults") maxResults: Int,
        @Query("key") key: String
    ): YouTubeFilm

    @GET(MOVIE_TRAILERS)
    open suspend fun getTrailers(
        @Path(MOVIE_ID) movieId: Int
    ): Trailers
}