package io.rocketlab.utils.system

import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import io.rocketlab.utils.provider.activity.ActivityContextProvider

class AppWindowManager(
    private val activityContextProvider: ActivityContextProvider
) {

    private val currentActivity: ComponentActivity
        get() = getComponentActivity()

    @Suppress("DEPRECATION")
    fun enableFullscreen() {
        currentActivity.apply {
            actionBar?.hide()
            WindowCompat.setDecorFitsSystemWindows(window, false)
            when {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.R -> {
                    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                }
                else -> {
                    window.insetsController?.apply {
                        hide(WindowInsets.Type.statusBars())
                        hide(WindowInsets.Type.navigationBars())
                        systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    fun disableFullscreen() {
        currentActivity.apply {
            WindowCompat.setDecorFitsSystemWindows(window, true)
            when {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.R -> {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                }
                else -> {
                    window.insetsController?.apply {
                        show(WindowInsets.Type.statusBars())
                        show(WindowInsets.Type.navigationBars())
                        systemBarsBehavior = WindowInsetsController.BEHAVIOR_DEFAULT
                    }
                }
            }
        }
    }

    private fun getComponentActivity(): ComponentActivity {
        return (activityContextProvider.activityContext as? ComponentActivity)
            ?: throw Exception("ComponentActivity is null")
    }
}