package io.rocketlab.utils.extension

val Any.TAG
    get() = this::class.java.canonicalName ?: "UnknownClass"

inline fun ignoreError(crossinline action: () -> Unit) {
    try {
        action.invoke()
    } catch (e: Throwable) {
        // ignored
    }
}

inline fun <reified T : Throwable> catchError(
    crossinline action: () -> Unit,
    crossinline error: (T) -> Unit
) {
    try {
        action.invoke()
    } catch (e: Throwable) {
        error.invoke(e as T)
    }
}