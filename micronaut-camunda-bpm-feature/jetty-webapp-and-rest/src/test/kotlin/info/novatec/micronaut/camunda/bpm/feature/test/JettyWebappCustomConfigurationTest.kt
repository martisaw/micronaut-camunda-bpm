package info.novatec.micronaut.camunda.bpm.feature.test

import info.novatec.micronaut.camunda.bpm.feature.Configuration
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.eclipse.jetty.server.Server
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@Requires(beans = [Server::class])
@MicronautTest(propertySources = ["classpath:applicationCustomConfiguration.yml"])
class JettyWebappCustomConfigurationTest {

    @Inject
    lateinit var configuration: Configuration

    @Inject
    @field:Client("/")
    lateinit var client: RxHttpClient

    @Test
    fun testConfiguration() {
        Assertions.assertEquals(false, configuration.webapps.isIndexRedirectEnabled)
        Assertions.assertEquals("/custom-path-webapps", configuration.webapps.contextPath)
        Assertions.assertEquals("/custom-path-engine", configuration.rest.contextPath)
    }

    @Test()
    fun redirectFail() {
        val request: HttpRequest<String> = HttpRequest.GET("/")

        Assertions.assertThrows(HttpClientResponseException::class.java) {
            client.toBlocking().exchange<String, Any>(request)
        }
    }

    @Test
    fun welcome() {
        val request: HttpRequest<String> = HttpRequest.GET(configuration.webapps.contextPath + "/app/welcome/default")
        val res: HttpResponse<*> = client.toBlocking().exchange<String, Any>(request)
        Assertions.assertEquals(200, res.status().code)
    }

    @Test
    fun admin() {
        val request: HttpRequest<String> = HttpRequest.GET(configuration.webapps.contextPath + "/app/admin/default")
        val res: HttpResponse<*> = client.toBlocking().exchange<String, Any>(request)
        Assertions.assertEquals(200, res.status().code)
    }

    @Test
    fun cockpit() {
        val request: HttpRequest<String> = HttpRequest.GET(configuration.webapps.contextPath + "/app/cockpit/default")
        val res: HttpResponse<*> = client.toBlocking().exchange<String, Any>(request)
        Assertions.assertEquals(200, res.status().code)
    }

    @Test
    fun tasklist() {
        val request: HttpRequest<String> = HttpRequest.GET(configuration.webapps.contextPath + "/app/tasklist/default")
        val res: HttpResponse<*> = client.toBlocking().exchange<String, Any>(request)
        Assertions.assertEquals(200, res.status().code)
    }
}
