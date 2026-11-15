package com.sufidev.carpull

import com.sufidev.carpull.cache.Database
import com.sufidev.carpull.cache.DatabaseDriverFactory
import com.sufidev.carpull.entity.TripEntity
import com.sufidev.carpull.network.CarPullApi

class CarPullSDK private constructor(
    private val database: Database,
    private val api: CarPullApi,
) {
    companion object {
        suspend fun create(
            databaseDriverFactory: DatabaseDriverFactory,
            api: CarPullApi,
        ): CarPullSDK {
            return CarPullSDK(
                database = Database.create(databaseDriverFactory),
                api = api,
            )
        }
    }

    @Throws(Exception::class)
    suspend fun getTrips(forceReload: Boolean): List<TripEntity> {
        val cachedTrips = database.getAllTrips()
        return if (cachedTrips.isNotEmpty() && !forceReload) {
            cachedTrips
        } else {
            api.getAllTrips().also {
                database.clearAndCreateTrips(it)
            }
        }
    }
}
