package info.novatec.micronaut.camunda.bpm.feature.webapps;

import org.camunda.bpm.webapp.impl.engine.EngineRestApplication;
import org.glassfish.jersey.server.ResourceConfig;

public class EngineRestApp extends ResourceConfig {
    static EngineRestApplication engineRestApplication = new EngineRestApplication();
    public EngineRestApp() {
        registerClasses(engineRestApplication.getClasses());
        property("jersey.config.server.wadl.disableWadl", "true");
    }
}
