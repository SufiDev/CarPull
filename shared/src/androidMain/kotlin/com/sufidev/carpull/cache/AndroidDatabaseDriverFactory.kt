package com.sufidev.carpull.cache

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sufidev.carpull.db.CarPullDatabase

class AndroidDatabaseDriverFactory(private val context: Context): DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(CarPullDatabase.Schema, context, "carpull.db")
    }
}