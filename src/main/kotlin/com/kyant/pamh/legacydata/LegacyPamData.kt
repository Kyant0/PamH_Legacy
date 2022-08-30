package com.kyant.pamh.legacydata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LegacyPamData(
    @SerialName("frameRate")
    val frameRate: Int,
    @SerialName("frameSum")
    val frameSum: Int,
    @SerialName("img")
    val img: List<Img>,
    @SerialName("label")
    val label: Label,
    @SerialName("layer")
    val layer: List<Layer>,
    @SerialName("origin")
    val origin: List<Double>,
    @SerialName("sub")
    val sub: List<String>
)
