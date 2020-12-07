package com.sergiodan.transformerbattle.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class MainData protected constructor(`in`: Parcel) : Parcelable {
    @SerializedName("data")
    private val data: Array<Transformer>? = `in`.createTypedArray(Transformer.CREATOR)

    @SerializedName("status")
    val status: String? = `in`.readString()
    override fun describeContents(): Int {
        return 0
    }

    fun getData(): Array<Transformer>? {
        return data
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeTypedArray(data, i)
        parcel.writeString(status)
    }

    companion object {
        val CREATOR: Parcelable.Creator<MainData?> = object : Parcelable.Creator<MainData?> {
            override fun createFromParcel(`in`: Parcel): MainData? {
                return MainData(`in`)
            }

            override fun newArray(size: Int): Array<MainData?> {
                return arrayOfNulls(size)
            }
        }
    }

}