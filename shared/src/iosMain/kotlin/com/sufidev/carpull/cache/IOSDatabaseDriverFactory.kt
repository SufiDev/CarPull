package com.sufidev.carpull.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.sufidev.carpull.db.CarPullDatabase

class IOSDatabaseDriverFactory: DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(CarPullDatabase.Schema, "carpull.db")
    }
}