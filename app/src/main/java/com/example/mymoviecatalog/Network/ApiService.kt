package com.example.mymoviecatalog.Network
import com.example.mymoviecatalog.data.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

 interface ApiService {
    @GET("person/popular?")
    open suspend fun getPersons(@Query("api_key") apiKey: String,
                           @Query("language") lang:String): PersonModel
    @GET("person/{person_id}")
    open suspend fun getPersonDetails(@Path("person_id") id: Int,
                                 @Query("api_key") apiKey: String): ActorDetailsModel

    @GET("/3/movie/popular")
    open suspend fun getPopMovie(@Query("api_key") apiKey: String,@Query("language") lang: String): PopMovieModel

    @GET("/3/search/movie")
    open suspend fun searchFilms(@Query("api_key") apiKey: String,@Query("query") query: String,
                            @Query("language")lang: String): SearchMovie

    @GET("movie/{movie_id}")
    open suspend fun getFilmDetails(@Path("movie_id")id: Int,@Query("api_key")apiKey: String,
                               @Query("language") lang: String): FoundFilmModel

    @GET("https://youtube.googleapis.com/youtube/v3/playlistItems?")
    open suspend fun getYoutubeFilms(@Query("part")part: String,@Query("playlistId")id: String,
                                @Query("maxResults")maxResults: Int,
                                @Query("key")key: String): YouTubeFilm

    @GET("movie/{movie_id}/videos")
    open suspend fun getTrailers(@Path("movie_id")movieId: Int,
                            @Query("api_key") apiKey: String): Trailers
}