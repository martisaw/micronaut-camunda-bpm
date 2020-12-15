package info.novatec.micronaut.camunda.bpm.feature.test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class RestTest {

    @Inject
    @Client("/engine-rest")
    RxHttpClient client;

    @Test
    void engine() {
        HttpRequest<String> request = HttpRequest.GET("/engine");
        String body = client.toBlocking().retrieve(request);

        // CustomizedEngine because there is a Singleton replacing the DefaultEngine
        assertEquals("[{\"name\":\"CustomizedEngine\"}]", body);
    }
}