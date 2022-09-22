package io.rocketlab.utils.system

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.io.File
import java.util.UUID

private const val BUILD_TAGS_KEY = "test-keys"
private const val SDK = "sdk"
private const val GOOGLE_SDK = "google_sdk"
private const val DEVICE_NAME_PATTERN = "%s %s"
private const val ANDROID_VERSION_PATTERN = "Android (%s)"

class DeviceManager(
    private val context: Context
) {

    val isRooted: Boolean
        get() {
            return when {
                isEmulator.not() && Build.TAGS != null && Build.TAGS.contains(BUILD_TAGS_KEY) -> true
                File("/system/app/Superuser.apk").exists() -> true
                File("/system/xbin/su").exists() && isEmulator.not() -> true
                else -> false
            }
        }

    val isEmulator: Boolean
        get() {
            val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            return SDK == Build.PRODUCT || GOOGLE_SDK == Build.PRODUCT || androidId == null
        }

    val deviceId: String
        get() {
            val secureId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            val uuid = if (secureId.isNotEmpty()) {
                UUID.nameUUIDFromBytes(secureId.toByteArray())
            } else {
                UUID.randomUUID()
            }
            return uuid.toString()
        }

    val androidVersion: String
        get() = ANDROID_VERSION_PATTERN.format(Build.VERSION.SDK_INT)

    val androidVersionNumber: Int
        get() = Build.VERSION.SDK_INT

    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                model
            } else {
                DEVICE_NAME_PATTERN.format(manufacturer, model)
            }
        }

    val screenWidth: Int
        get() {
            val displayMetrics = context.resources.displayMetrics
            return displayMetrics.widthPixels
        }

    val screenHeight: Int
        get() {
            val displayMetrics = context.resources.displayMetrics
            return displayMetrics.heightPixels
        }

    fun getDeviceResolution(): String {
        return "${screenWidth}x${screenHeight}"
    }
}