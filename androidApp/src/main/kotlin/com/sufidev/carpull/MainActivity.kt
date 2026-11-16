package com.sufidev.carpull

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val localNetworkPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                launchApp()
            } else {
                showLocalNetworkPermissionRationale()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (
            Build.VERSION.SDK_INT >= ANDROID_17_API &&
            checkSelfPermission(ACCESS_LOCAL_NETWORK_PERMISSION) != PackageManager.PERMISSION_GRANTED
        ) {
            localNetworkPermissionLauncher.launch(ACCESS_LOCAL_NETWORK_PERMISSION)
        } else {
            launchApp()
        }
    }

    private fun launchApp() {
        lifecycleScope.launch {
            initializeAndroidDependencies(applicationContext)
            setContent {
                App()
            }
        }
    }

    private fun showLocalNetworkPermissionRationale() {
        AlertDialog.Builder(this)
            .setTitle("Allow access to your local network")
            .setMessage("CarPull needs local network access to load rides from your server.")
            .setPositiveButton("Try again") { _, _ ->
                localNetworkPermissionLauncher.launch(ACCESS_LOCAL_NETWORK_PERMISSION)
            }
            .setNegativeButton("Continue offline") { _, _ ->
                launchApp()
            }
            .setOnCancelListener {
                launchApp()
            }
            .show()
    }

    private companion object {
        const val ANDROID_17_API = 37
        const val ACCESS_LOCAL_NETWORK_PERMISSION =
            "android.permission.ACCESS_LOCAL_NETWORK"
    }
}
