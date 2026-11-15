package com.sufidev.carpull.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
)
