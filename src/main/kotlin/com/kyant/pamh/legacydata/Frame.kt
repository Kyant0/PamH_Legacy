package com.kyant.pamh.legacydata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Frame(
    @SerialName("matrix")
    val matrix: List<Double>,
    @SerialName("opacity")
    val opacity: Double
)
