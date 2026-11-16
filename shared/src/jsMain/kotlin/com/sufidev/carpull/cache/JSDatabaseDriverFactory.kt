package com.sufidev.carpull.cache

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import com.sufidev.carpull.db.CarPullDatabase
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.w3c.dom.Worker

class JSDatabaseDriverFactory: DatabaseDriverFactory {
    override suspend fun createDriver(): SqlDriver {
        val driver = WebWorkerDriver(
            Worker(js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")),
        )
        CarPullDatabase.Schema.awaitCreate(driver)
        return driver
    }
}