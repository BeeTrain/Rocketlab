package io.rocketlab.app.di.provider

import android.app.ActivityManager
import android.content.Context
import android.os.Process
import io.rocketlab.app.BuildConfig
import io.rocketlab.utils.system.config.Environment
import io.rocketlab.utils.system.config.model.BuildType

object EnvironmentProvider {

    fun provide(context: Context): Environment {
        return object : Environment {
            override val isMainProcess = isMainProcess(context)
            override val buildType = BuildType.getByName(BuildConfig.BUILD_TYPE)
            override val isDebug = buildType != BuildType.RELEASE
        }
    }

    private fun isMainProcess(context: Context): Boolean {
        val processPid = Process.myPid()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        val currentProcessInfo = activityManager?.runningAppProcesses?.firstOrNull { it.pid == processPid }

        return context.packageName == currentProcessInfo?.processName
    }
}