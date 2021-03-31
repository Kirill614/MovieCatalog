package com.example.mymoviecatalog.data

import com.google.gson.annotations.SerializedName

data class FoundFilmModel(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val runtime: Int?,
    @SerializedName("vote_average")
    val rating: Float,
    val title: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val date: String?,
    val overview: String?
)
