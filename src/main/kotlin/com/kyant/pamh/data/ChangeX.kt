package com.kyant.pamh.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangeX(
    @SerialName("color")
    val color: List<Double>?,
    @SerialName("index")
    val index: Int,
    @SerialName("source_rectangle")
    val sourceRectangle: String?,
    @SerialName("sprite_frame_number")
    val spriteFrameNumber: Int?,
    @SerialName("transform")
    val transform: List<Double>
)
