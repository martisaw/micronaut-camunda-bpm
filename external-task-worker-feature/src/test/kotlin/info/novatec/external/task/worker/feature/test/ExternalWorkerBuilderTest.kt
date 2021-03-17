package info.novatec.external.task.worker.feature.test

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

@MicronautTest
class ExternalWorkerBuilderTest {

    @Test
    fun `do nothing test` () {
        println("hello")
    }

    // TODO implement tests that check the configuration possibilities
    // Well I probably need a Camunda BPM Platform Server the client can connect to?
    // What exactly do I want to check?
}