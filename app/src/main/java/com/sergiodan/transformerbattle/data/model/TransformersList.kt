package com.sergiodan.transformerbattle.data.model

import com.google.gson.annotations.SerializedName

data class TransformersList(
    @SerializedName("transformers") val transformers: List<Transformer>
)
