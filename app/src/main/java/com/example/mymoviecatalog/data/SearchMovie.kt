package com.example.mymoviecatalog.data

import com.google.gson.annotations.SerializedName

data class MovieSearch (
    @SerializedName("results")
    val foundMovieList: ArrayList<FoundMovie>
)

data class FoundMovie(
    val title:String,
    @SerializedName("poster_path")
    val posterPath: String,
    val id: Int
)