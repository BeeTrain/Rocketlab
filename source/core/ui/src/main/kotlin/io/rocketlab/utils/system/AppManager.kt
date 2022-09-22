package io.rocketlab.utils.system

import android.content.Context
import android.content.pm.PackageManager
import io.rocketlab.utils.system.config.Environment

private const val APP_VERSION_PATTERN = "%s %s"

class AppManager(
    private val context: Context,
    environment: Environment
) {

    val buildType = environment.buildType.title

    val appVersionName: String
        get() = APP_VERSION_PATTERN.format(versionName, appVersionCode.toString())

    @Suppress("DEPRECATION")
    val appVersionCode: Int
        get() {
            return try {
                val manager = context.packageManager
                val info = manager.getPackageInfo(context.packageName, 0)
                info.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                -1
            }
        }

    @Suppress("DEPRECATION")
    val versionName: String
        get() {
            return try {
                val manager = context.packageManager
                val info = manager.getPackageInfo(context.packageName, 0)
                info.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                "n/a"
            }
        }
}