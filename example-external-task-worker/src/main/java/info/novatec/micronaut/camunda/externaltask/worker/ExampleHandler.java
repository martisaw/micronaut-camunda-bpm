package info.novatec.micronaut.camunda.externaltask.worker;

import info.novatec.external.task.worker.feature.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * FIXME Declaring the @Singleton on the @ExternalTaskSubscription Annotation Interface does not work
 * Error: Caused by: io.micronaut.context.exceptions.NoSuchBeanException: No bean of type
 * [org.camunda.bpm.client.task.ExternalTaskHandler] exists. Make sure the bean is not disabled by bean
 * requirements (enable trace logging for 'io.micronaut.context.condition' to check) and if the bean is
 * enabled then ensure the class is declared a bean and annotation processing is enabled (for Java and
 * Kotlin the 'micronaut-inject-java' dependency should be configured as an annotation processor).
 */
@Singleton
@ExternalTaskSubscription(topic = "my-topic")
public class ExampleHandler implements ExternalTaskHandler {

    private static final Logger log = LoggerFactory.getLogger(ExampleHandler.class);

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        // Put your business logic here

        // Get a process variable
        Integer item = externalTask.getVariable("itemNumber");
        Integer value = externalTask.getVariable("itemValue");

        //Process the task
        Boolean approved = (value <= 500);
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", approved);


        // Complete the task
        externalTaskService.complete(externalTask, variables);
        log.info("Finished item " + item + " with Value " + value);
    }
}

