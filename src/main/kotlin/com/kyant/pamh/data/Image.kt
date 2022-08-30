package com.kyant.pamh.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("name")
    val name: String,
    @SerialName("size")
    val size: List<Int>,
    @SerialName("transform")
    val transform: List<Double>
)
