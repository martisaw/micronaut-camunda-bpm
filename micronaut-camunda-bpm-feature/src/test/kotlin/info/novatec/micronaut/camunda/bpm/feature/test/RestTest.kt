package info.novatec.micronaut.camunda.bpm.feature.test

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject
/**
 * Tests if the REST API is accessible
 *
 * @author Martin Sawilla
 */
@MicronautTest
class RestTest {
    @Inject
    @field:Client("/engine-rest")
    var client: RxHttpClient? = null
    @Test
    fun engine() {
        val request: HttpRequest<String> = HttpRequest.GET("/engine")
        val body = client!!.toBlocking().retrieve(request)

        // CustomizedEngine because there is a Singleton replacing the DefaultEngine
        Assertions.assertEquals("[{\"name\":\"CustomizedEngine\"}]", body)
    }
}