package com.amanpathak.bitbucketaccess.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep


data class RepoModel(
    val id : String? = "",
    val avatar : String? = "",
    val name : String? = "",
    val des : String? ="",
    val isPrivate : Boolean = false,
    val createdOn : String? = "",
    val workspace : String? = "",

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(avatar)
        parcel.writeString(name)
        parcel.writeString(des)
        parcel.writeByte(if (isPrivate) 1 else 0)
        parcel.writeString(createdOn)
        parcel.writeString(workspace)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RepoModel> {
        override fun createFromParcel(parcel: Parcel): RepoModel {
            return RepoModel(parcel)
        }

        override fun newArray(size: Int): Array<RepoModel?> {
            return arrayOfNulls(size)
        }
    }
}
