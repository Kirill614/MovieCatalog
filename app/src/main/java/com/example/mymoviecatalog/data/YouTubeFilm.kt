package com.example.mymoviecatalog.data


data class YouTubeFilm(
    val items: ArrayList<Film>
)

data class Film(
    val snippet: Snippet?
)


data class Snippet(
    val title: String?,
    val thumbnails: Thumbnails?,
    val resourceId: Resource?,
    val description: String?
)
data class Thumbnails(
    val high: High
)
data class High(
    val url: String
)
data class Resource(
    val videoId: String?
)
