package io.rocketlab.utils

import java.util.concurrent.atomic.AtomicInteger

object RequestCodeGenerator {

    private val SEED = AtomicInteger()

    val next: Int
        get() = SEED.incrementAndGet()
}