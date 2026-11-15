package com.sufidev.carpull.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripEntity(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("tripStart")
    val tripStart: String,
    @SerialName("tripEnd")
    val tripEnd: String,
    @SerialName("tripDriver")
    val tripDriver: UserEntity,
    @SerialName("passengerList")
    val passengerList: List<UserEntity>,
    @SerialName("tripStatus")
    val tripStatus: String
)
