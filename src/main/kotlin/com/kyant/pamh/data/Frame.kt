package com.kyant.pamh.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Frame(
    @SerialName("append")
    val append: List<Append>,
    @SerialName("change")
    val change: List<Change>,
    @SerialName("command")
    val command: List<List<String>>,
    @SerialName("label")
    val label: String? = null,
    @SerialName("remove")
    val remove: List<Remove>,
    @SerialName("stop")
    val stop: Boolean
)
