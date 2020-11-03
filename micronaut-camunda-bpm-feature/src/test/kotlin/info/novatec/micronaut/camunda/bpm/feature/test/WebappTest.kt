package info.novatec.micronaut.camunda.bpm.feature.test

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class WebappTest {
    @Inject
    @field:Client("/camunda")
    var client: RxHttpClient? = null
    @Test
    fun welcome() {
        val request: HttpRequest<String> = HttpRequest.GET("/app/welcome/default")
        val res: HttpResponse<*> = client!!.toBlocking().exchange<String, Any>(request)
        Assertions.assertEquals(200, res.status().code)
    }

    @Test
    fun admin() {
        val request: HttpRequest<String> = HttpRequest.GET("/app/admin/default")
        val res: HttpResponse<*> = client!!.toBlocking().exchange<String, Any>(request)
        Assertions.assertEquals(200, res.status().code)
    }

    @Test
    fun cockpit() {
        val request: HttpRequest<String> = HttpRequest.GET("/app/cockpit/default")
        val res: HttpResponse<*> = client!!.toBlocking().exchange<String, Any>(request)
        Assertions.assertEquals(200, res.status().code)
    }

    @Test
    fun tasklist() {
        val request: HttpRequest<String> = HttpRequest.GET("/app/tasklist/default")
        val res: HttpResponse<*> = client!!.toBlocking().exchange<String, Any>(request)
        Assertions.assertEquals(200, res.status().code)
    }
}