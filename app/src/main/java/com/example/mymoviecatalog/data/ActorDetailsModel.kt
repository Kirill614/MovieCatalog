package com.example.mymoviecatalog.data

import com.google.gson.annotations.SerializedName
data class ActorDetailsModel (
    val biography: String?,
    val name: String?,
    @SerializedName("profile_path")
    val profilePath: String?
)
