package com.sufidev.carpull.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.w3c.dom.Worker

class JSDatabaseDriverFactory: DatabaseDriverFactory {
    override suspend fun createDriver(): SqlDriver {
        return WebWorkerDriver(
            Worker(js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")),
        )
    }
}