package com.sufidev.carpull.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripEntity(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("tripStart")
    val tripStart: String,
    @SerialName("tripEnd")
    val tripEnd: String,
    @SerialName("tripDriverId")
    val tripDriverId: Long,
    @SerialName("passengerList")
    val passengerList: List<UserEntity>,
    @SerialName("tripStatus")
    val tripStatus: String
)
