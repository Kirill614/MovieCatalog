package com.example.mymoviecatalog.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ActorDetailsModel (
    val biography: String?,
    val name: String?,
    @SerializedName("profile_path")
    val profilePath: String?
): Parcelable{
    constructor(parcel: Parcel):this(parcel.readString(),parcel.readString(),parcel.readString())

    companion object CREATOR: Parcelable.Creator<ActorDetailsModel>{
        override fun createFromParcel(source: Parcel): ActorDetailsModel {
            return ActorDetailsModel(
                source
            )
        }
        override fun newArray(size: Int): Array<ActorDetailsModel?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(biography)
        parcel.writeString(name)
        parcel.writeString(profilePath)
    }
    override fun describeContents(): Int {
        return 0
    }

}
