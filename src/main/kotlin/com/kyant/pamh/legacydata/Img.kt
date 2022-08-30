package com.kyant.pamh.legacydata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Img(
    @SerialName("origin")
    val origin: List<Double>,
    @SerialName("src")
    val src: String,
    @SerialName("sz")
    val sz: List<Double>
)
