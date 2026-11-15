package com.sufidev.carpull

import com.sufidev.carpull.cache.DatabaseDriverFactory
import com.sufidev.carpull.cache.JSDatabaseDriverFactory
import com.sufidev.carpull.network.CarPullApi
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun jsModule(): Module = module {
    single<DatabaseDriverFactory> { JSDatabaseDriverFactory() }
}

suspend fun initializeJsDependencies() {
    val koin = GlobalContext.getOrNull()
        ?: startKoin {
            modules(jsModule())
        }.koin

    if (koin.getOrNull<CarPullSDK>() == null) {
        val api = CarPullApi()
        val sdk = CarPullSDK.create(
            databaseDriverFactory = koin.get<DatabaseDriverFactory>(),
            api = api,
        )
        koin.loadModules(listOf(commonModule(api, sdk)))
    }
}
