package com.sergiodan.transformerbattle.data.model

import com.google.gson.annotations.SerializedName

data class Transformer(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("team") var team: String,
    @SerializedName("strength") val strength: Int = 0,
    @SerializedName("intelligence") val intelligence: Int = 0,
    @SerializedName("speed") val speed: Int = 0,
    @SerializedName("endurance") val endurance: Int = 0,
    @SerializedName("rank") val rank: Int = 0,
    @SerializedName("courage") val courage: Int = 0,
    @SerializedName("firepower") val firepower: Int = 0,
    @SerializedName("skill") val skill: Int = 0,
    @SerializedName("team_icon") val teamIcon: String
)