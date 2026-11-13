package com.sufidev.carpull

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform