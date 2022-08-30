package com.kyant.pamh.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FrameX(
    @SerialName("append")
    val append: List<Append>,
    @SerialName("change")
    val change: List<ChangeX>,
    @SerialName("command")
    val command: List<List<String>>,
    @SerialName("label")
    val label: String?,
    @SerialName("remove")
    val remove: List<Index>,
    @SerialName("stop")
    val stop: Boolean
)

@Serializable
data class Index(
    @SerialName("index")
    val index: Int
)
