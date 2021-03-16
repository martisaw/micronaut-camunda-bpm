package info.novatec.external.task.worker.feature;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.ExternalTaskClientBuilder;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class ExternalWorkerBuilder implements ApplicationEventListener<ServerStartupEvent> {

    protected static final Logger log = LoggerFactory.getLogger(ExternalWorkerBuilder.class);

    protected ExternalTaskHandler externalTaskHandler;
    protected ExternalTaskSubscription externalTaskSubscription;
    protected Configuration configuration;

    // TODO Support multiple Subscriptions -> At the moment I only inject one Handler
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
        // I can also do a factory and return multiple TopicSubscriptions (Prototype)
    }
}
