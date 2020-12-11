package info.novatec.micronaut.camunda.bpm.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.reactivex.Flowable;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class WebappsTest {
    @Inject
    @Client("/camunda")
    RxHttpClient client;

    @Test
    void welcome() {
        HttpRequest<String> request = HttpRequest.GET("/app/welcome/default");
        HttpResponse res = client.toBlocking().exchange(request);

        assertEquals(200, res.status().getCode());
    }

    @Test
    void admin() {
        HttpRequest<String> request = HttpRequest.GET("/app/admin/default");
        HttpResponse res = client.toBlocking().exchange(request);

        assertEquals(200, res.status().getCode());
    }

    @Test
    void cockpit() {
        HttpRequest<String> request = HttpRequest.GET("/app/cockpit/default");
        HttpResponse res = client.toBlocking().exchange(request);

        assertEquals(200, res.status().getCode());
    }
    @Test
    void tasklist() {
        HttpRequest<String> request = HttpRequest.GET("/app/tasklist/default");
        HttpResponse res = client.toBlocking().exchange(request);

        assertEquals(200, res.status().getCode());
    }
}
