package info.novatec.micronaut.camunda.bpm.example;

import info.novatec.micronaut.camunda.bpm.feature.rest.RestApp;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.servlet.undertow.UndertowServer;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.*;

/**
 * Undertow Customizer to start the REST API as a servlet.
 *
 * @author Martin Sawilla
 */
@Singleton
public class UndertowServerCustomizer implements BeanCreatedEventListener<Undertow.Builder> {

    private static final Logger log = LoggerFactory.getLogger(UndertowServerCustomizer.class);

    public static final String MYAPPCONTEXT = "/";

    @Override
    public Undertow.Builder onCreated(BeanCreatedEvent<Undertow.Builder> event) {
        Undertow.Builder undertowBuilder = event.getBean();
        try {
            //REST
            //maybe see http://www.thedevpiece.com/building-microservices-undertow-cdi-jaxrs/
            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(UndertowServer.class.getClassLoader())
                    .setContextPath(MYAPPCONTEXT)
                    .setDeploymentName("rest.war")
                    .addServlets(
                            servlet("rest", ServletContainer.class)
                                    //Default: -1 == Lazy Initialization; >= 0 == On Startup Initialized; https://docs.oracle.com/javaee/6/api/javax/servlet/ServletRegistration.Dynamic.html#setLoadOnStartup(int),
                                    .setLoadOnStartup(1)
                                    .addInitParam("javax.ws.rs.Application", RestApp.class.getName())
                                    .addMapping("/engine-rest/*")
                    );

            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();
            HttpHandler servletHandler = manager.start();
            PathHandler path = Handlers.path(Handlers.redirect(MYAPPCONTEXT)).addPrefixPath(MYAPPCONTEXT, servletHandler);
            undertowBuilder.setHandler(path);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return undertowBuilder;
    }
}
