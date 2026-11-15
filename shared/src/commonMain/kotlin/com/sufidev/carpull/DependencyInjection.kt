package com.sufidev.carpull

import com.sufidev.carpull.network.CarPullApi
import org.koin.core.module.Module
import org.koin.dsl.module

fun commonModule(api: CarPullApi, sdk: CarPullSDK): Module = module {
    single { api }
    single { sdk }
}
