package com.kyant.pamh.legacydata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Layer(
    @SerialName("frame")
    val frame: List<Frame>,
    @SerialName("id")
    val id: List<Int>,
    @SerialName("parent")
    val parent: List<Int>,
    @SerialName("range")
    val range: List<Int>
)
