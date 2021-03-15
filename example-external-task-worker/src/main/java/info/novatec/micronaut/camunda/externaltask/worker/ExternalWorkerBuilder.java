package info.novatec.micronaut.camunda.externaltask.worker;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.ExternalTaskClientBuilder;
import org.camunda.bpm.client.task.ExternalTaskHandler;

import javax.inject.Singleton;

@Singleton
public class ExternalWorkerBuilder implements ApplicationEventListener<ServerStartupEvent> {

    protected ExternalTaskHandler externalTaskHandler;
    protected ExternalTaskSubscription externalTaskSubscription;
    protected Configuration configuration;

    public ExternalWorkerBuilder(ExternalTaskHandler externalTaskHandler, Configuration configuration) {
        this.externalTaskHandler = externalTaskHandler;
        this.externalTaskSubscription = this.externalTaskHandler.getClass().getAnnotation(ExternalTaskSubscription.class);
        this.configuration = configuration;
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        ExternalTaskClientBuilder clientBuilder = ExternalTaskClient.create();

        clientBuilder.baseUrl(configuration.getBaseUrl());
        clientBuilder.asyncResponseTimeout(1000);

        ExternalTaskClient client = clientBuilder.build();

        client.subscribe(externalTaskSubscription.topic())
                .handler(this.externalTaskHandler)
                .open();

    }
}
