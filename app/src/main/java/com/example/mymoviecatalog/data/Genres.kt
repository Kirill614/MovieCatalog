package com.example.mymoviecatalog.data

import com.google.gson.annotations.SerializedName

data class Genres (
    @SerializedName("genres")
    val genresList: ArrayList<Genre>
)
data class Genre(
    val id: Int,
    val name: String
)

