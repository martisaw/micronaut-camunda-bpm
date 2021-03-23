package info.novatec.micronaut.camunda.bpm.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

import javax.inject.Inject;

@MicronautTest
public class BugRecreationTest {

    @Inject
    RuntimeService runtimeService;

    @Test
    void crash() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("HelloWorld");

        // It tries to reuse an already used connection pool ? Is the static part a problem???

        assertThat(processInstance).isStarted();

        assertThat(processInstance).isWaitingAt("TimerEvent_Wait");
        execute(job());

        assertThat(processInstance).isEnded();
    }
}
