package info.novatec.micronaut.camunda.bpm.example;

import info.novatec.micronaut.camunda.bpm.feature.rest.RestApp;
import io.micronaut.runtime.Micronaut;
import io.netty.channel.Channel;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);

        //Netty HTTP container does not support deployment on context paths other than root path ("/"). Non-root context path is ignored during deployment.
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig rest = new RestApp();
        Channel server = NettyHttpContainerProvider.createServer(baseUri, rest, false);

    }
}