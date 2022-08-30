package com.kyant.pamh.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PamData(
    @SerialName("frame_rate")
    val frameRate: Int,
    @SerialName("image")
    val image: List<Image>,
    @SerialName("main_sprite")
    val mainSprite: MainSprite,
    @SerialName("position")
    val position: List<Double>,
    @SerialName("size")
    val size: List<Double>,
    @SerialName("sprite")
    val sprite: List<Sprite>
)
