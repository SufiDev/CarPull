package com.sufidev.carpull.cache

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.sufidev.carpull.db.CarPullDatabase

class IOSDatabaseDriverFactory: DatabaseDriverFactory {
    override suspend fun createDriver(): SqlDriver {
        return NativeSqliteDriver(CarPullDatabase.Schema.synchronous(), "carpull.db")
    }
}