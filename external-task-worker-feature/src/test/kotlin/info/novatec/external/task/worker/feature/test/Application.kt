package info.novatec.external.task.worker.feature.test

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("info.novatec.external.task.worker.feature.test")
        .start()
}