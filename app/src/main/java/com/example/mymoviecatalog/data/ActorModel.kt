package com.example.mymoviecatalog.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PersonModel(
    @SerializedName("results")
    val personsList: ArrayList<Person>
)

data class Person(
    val name: String?,
    val id: Int,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("known_for")
    val moviesList: ArrayList<Movie>
)

data class Movie(
    @SerializedName("original_title")
    val movieTitle: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val date: String?,
    @SerializedName("vote_average")
    val audienceScore: String?,
    @SerializedName("genre_ids")
    val genresList: ArrayList<Int>,
    val id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(),
        parcel.readArrayList(ClassLoader.getSystemClassLoader()) as ArrayList<Int>, parcel.readInt()
    )

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(source: Parcel): Movie {
            return Movie(source)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(movieTitle)
        parcel.writeString(posterPath)
        parcel.writeString(date)
        parcel.writeString(audienceScore)
        parcel.writeList(genresList)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }


}
