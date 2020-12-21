package info.novatec.micronaut.camunda.bpm.feature.webapps;

import org.camunda.bpm.cockpit.impl.web.CockpitApplication;
import org.glassfish.jersey.server.ResourceConfig;

public class CockpitApp extends ResourceConfig {
    static CockpitApplication cockpitApplication = new CockpitApplication();
    public CockpitApp() {
        registerClasses(cockpitApplication.getClasses());
        property("jersey.config.server.wadl.disableWadl", "true");
    }
}
