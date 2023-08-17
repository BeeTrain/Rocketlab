package io.rocketlab.screen.settings.domain.model

enum class AppTheme(val id: Int) {
    AS_IN_SYSTEM(0),
    LIGHT(1),
    DARK(2);

    companion object {

        fun getOrDefault(id: Int?): AppTheme {
            return entries.firstOrNull { it.id == id } ?: AS_IN_SYSTEM
        }
    }
}