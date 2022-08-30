package com.kyant.pamh.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sprite(
    @SerialName("frame")
    val frame: List<FrameX>,
    @SerialName("frame_rate")
    val frameRate: Double,
    @SerialName("name")
    val name: String,
    @SerialName("work_area")
    val workArea: List<Int>
)
