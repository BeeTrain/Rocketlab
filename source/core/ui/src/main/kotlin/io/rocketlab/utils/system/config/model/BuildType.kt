package io.rocketlab.utils.system.config.model

enum class BuildType(val title: String) {
    DEBUG("debug"),
    RELEASE("release");

    companion object {

        fun getByName(name: String): BuildType {
            return values()
                .find { it.title.equals(name, true) }
                ?: throw IllegalStateException("no matched build types found")
        }
    }
}