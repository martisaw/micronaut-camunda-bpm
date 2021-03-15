package info.novatec.micronaut.camunda.externaltask.worker;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton // FIXME Declaring the @Singleton on the @ExternalTaskSubscription Annotation Interface does not work
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

