package com.kyant.pamh.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Append(
    @SerialName("additive")
    val additive: Boolean,
    @SerialName("index")
    val index: Int,
    @SerialName("name")
    val name: String?,
    @SerialName("preload_frame")
    val preloadFrame: Int,
    @SerialName("resource")
    val resource: Int,
    @SerialName("sprite")
    val sprite: Boolean,
    @SerialName("time_scale")
    val timeScale: Double
)
