package info.novatec.micronaut.camunda.bpm.feature.webapps;

import org.camunda.bpm.tasklist.impl.web.TasklistApplication;
import org.glassfish.jersey.server.ResourceConfig;

public class TasklistApp extends ResourceConfig {
    static TasklistApplication tasklistApplication = new TasklistApplication();
    public TasklistApp() {
        registerClasses(tasklistApplication.getClasses());
    }
}
