package com.example.mymoviecatalog.data

import com.google.gson.annotations.SerializedName

data class SearchMovie (
    @SerializedName("results")
    val foundMovieList: ArrayList<FoundMovie>
)

data class FoundMovie(
    val title:String,
    @SerializedName("poster_path")
    val posterPath: String,
    val id: Int
)