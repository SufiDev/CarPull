package com.sufidev.carpull

import android.content.Context
import com.sufidev.carpull.cache.AndroidDatabaseDriverFactory
import com.sufidev.carpull.cache.DatabaseDriverFactory
import com.sufidev.carpull.network.CarPullApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun androidModule(context: Context): Module = module {
    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(context) }
}

suspend fun initializeAndroidDependencies(context: Context) {
    val koin = GlobalContext.getOrNull()
        ?: startKoin {
            androidContext(context.applicationContext)
            modules(androidModule(context.applicationContext))
        }.koin

    if (koin.getOrNull<CarPullSDK>() == null) {
        val api = CarPullApi(baseUrl = "http://10.0.2.2:3030")
        val sdk = CarPullSDK.create(
            databaseDriverFactory = koin.get<DatabaseDriverFactory>(),
            api = api,
        )
        koin.loadModules(listOf(commonModule(api, sdk)))
    }
}
