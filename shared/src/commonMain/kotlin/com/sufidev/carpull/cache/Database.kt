package com.sufidev.carpull.cache

import app.cash.sqldelight.db.SqlDriver
import com.sufidev.carpull.db.CarPullDatabase
import com.sufidev.carpull.entity.TripEntity
import com.sufidev.carpull.entity.UserEntity

internal class Database private constructor(driver: SqlDriver) {
    private val database = CarPullDatabase(driver)
    private val dbQuery = database.carPullDataBaseQueries

    companion object {
        suspend fun create(factory: DatabaseDriverFactory): Database =
            Database(factory.createDriver())
    }

    internal fun getAllTrips(): List<TripEntity> {
        return dbQuery.selectAllTrips(::mapTripSelecting)
            .executeAsList()
            .map { trip ->
                trip.copy(
                    passengerList = dbQuery.selectPassengersForTrip(trip.id)
                        .executeAsList()
                        .map { passenger ->
                            UserEntity(
                                id = passenger.id.toInt(),
                                name = passenger.name
                            )
                        }
                )
            }
    }

    private fun mapTripSelecting(
        id: Long,
        name: String,
        tripStart: String,
        tripEnd: String,
        tripDriverId: Long,
        tripStatus: String
    ): TripEntity {
        return TripEntity(
            id = id,
            name = name,
            tripStart = tripStart,
            tripEnd = tripEnd,
            tripDriverId = tripDriverId,
            passengerList = emptyList(),
            tripStatus = tripStatus
        )
    }

    internal suspend fun clearAndCreateTrips(trips: List<TripEntity>) {
        dbQuery.transaction {
            dbQuery.removeAllTrips()
            trips.forEach { trip ->
                dbQuery.insertTrip(
                    id = trip.id,
                    name = trip.name,
                    tripStart = trip.tripStart,
                    tripEnd = trip.tripEnd,
                    tripDriverId = trip.tripDriverId,
                    tripStatus = trip.tripStatus
                )
            }
        }
    }
}