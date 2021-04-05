package com.example.mymoviecatalog.data

import com.google.gson.annotations.SerializedName

data class PopMovies(
    @SerializedName("results")
    val popMovieList: ArrayList<PopMovie>
)

data class PopMovie(
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterUrl: String,
    val id: Int
)
