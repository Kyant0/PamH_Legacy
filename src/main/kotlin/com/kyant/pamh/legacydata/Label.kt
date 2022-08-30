package com.kyant.pamh.legacydata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Label(
    @SerialName("attack")
    val attack: List<Int>,
    @SerialName("idle")
    val idle: List<Int>,
    @SerialName("plantfood")
    val plantfood: List<Int>,
    @SerialName("water")
    val water: List<Int>
)
