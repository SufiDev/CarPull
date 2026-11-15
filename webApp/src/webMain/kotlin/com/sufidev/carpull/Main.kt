package com.sufidev.carpull

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

@OptIn(ExperimentalComposeUiApi::class)
fun startWebApp() {
    ComposeViewport {
        App()
    }
}