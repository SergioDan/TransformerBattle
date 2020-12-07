package com.sergiodan.transformerbattle.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

data class Transformer(
    @SerializedName("id") val id: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("team") var team: String? = "",
    @SerializedName("strength") val strength: Int = 1,
    @SerializedName("intelligence") val intelligence: Int = 1,
    @SerializedName("speed") val speed: Int = 1,
    @SerializedName("endurance") val endurance: Int = 1,
    @SerializedName("rank") val rank: Int = 1,
    @SerializedName("courage") val courage: Int = 1,
    @SerializedName("firepower") val firepower: Int = 1,
    @SerializedName("skill") val skill: Int = 1,
    @SerializedName("team_icon") val teamIcon: String? = ""
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(team)
        parcel.writeInt(strength)
        parcel.writeInt(intelligence)
        parcel.writeInt(speed)
        parcel.writeInt(endurance)
        parcel.writeInt(rank)
        parcel.writeInt(courage)
        parcel.writeInt(firepower)
        parcel.writeInt(skill)
        parcel.writeString(teamIcon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transformer> {
        override fun createFromParcel(parcel: Parcel): Transformer {
            return Transformer(parcel)
        }

        override fun newArray(size: Int): Array<Transformer?> {
            return arrayOfNulls(size)
        }
    }
}

fun Transformer.toMap(): Map<String, Any?> {
    return mapOf("id" to id,
    "name" to name,
    "team" to team,
    "strength" to strength,
    "intelligence" to intelligence,
    "speed" to speed,
    "endurance" to endurance,
    "rank" to rank,
    "courage" to courage,
    "firepower" to firepower,
    "skill" to skill,
    "team_icon" to teamIcon)
}

fun Map<String, Int>.specsToTransformer(): Transformer {
    return Transformer(strength = this["Strength"] ?: 1,
        intelligence = this["Intelligence"] ?: 1,
        speed = this["Speed"] ?: 1,
        endurance = this["Endurance"] ?: 1,
        rank = this["Rank"] ?: 1,
        courage = this["Courage"] ?: 1,
        firepower = this["Firepower"] ?: 1,
        skill = this["Skill"] ?: 1,
        id = UUID.randomUUID().toString(),
        name = "",
        team = ""
    )
}