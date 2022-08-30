package com.kyant.pamh.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Remove(
    @SerialName("index")
    val index: Int
)
