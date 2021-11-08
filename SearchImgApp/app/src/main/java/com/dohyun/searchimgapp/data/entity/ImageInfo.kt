package com.dohyun.searchimgapp.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ImageInfo(
    val collection: String,
    @SerializedName("thumbnail_url")
    val thumbnameUrl: String,
    @SerializedName("display_sitename")
    val siteName: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val width: Int,
    val height: Int,
    @SerializedName("doc_url")
    val docUrl: String,
    val datetime: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(collection)
        parcel.writeString(thumbnameUrl)
        parcel.writeString(siteName)
        parcel.writeString(imageUrl)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeString(docUrl)
        parcel.writeString(datetime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageInfo> {
        override fun createFromParcel(parcel: Parcel): ImageInfo {
            return ImageInfo(parcel)
        }

        override fun newArray(size: Int): Array<ImageInfo?> {
            return arrayOfNulls(size)
        }
    }
}
