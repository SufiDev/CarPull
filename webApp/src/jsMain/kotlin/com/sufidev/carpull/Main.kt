package com.sufidev.carpull

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun main() {
    MainScope().launch {
        initializeJsDependencies()
        startWebApp()
    }
}
