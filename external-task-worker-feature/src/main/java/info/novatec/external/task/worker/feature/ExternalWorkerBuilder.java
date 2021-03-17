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

    protected final ExternalTaskHandler externalTaskHandler;
    protected final ExternalTaskSubscription externalTaskSubscription;
    protected final Configuration configuration;

    // TODO Support multiple Subscriptions -> At the moment I only inject one Handler
    public ExternalWorkerBuilder(ExternalTaskHandler externalTaskHandler, Configuration configuration) {
        this.externalTaskHandler = externalTaskHandler;
        this.externalTaskSubscription = this.externalTaskHandler.getClass().getAnnotation(ExternalTaskSubscription.class);
        this.configuration = configuration;
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        ExternalTaskClient client = buildClient();

        client.subscribe(externalTaskSubscription.topic())
                .handler(this.externalTaskHandler)
                .open();
        // I can also do a factory and return multiple TopicSubscriptions (Prototype)
    }

    protected ExternalTaskClient buildClient() {
        ExternalTaskClientBuilder clientBuilder = ExternalTaskClient.create();

        clientBuilder.baseUrl(configuration.getBaseUrl());

        if (configuration.getWorkerId().isPresent()) {
            clientBuilder.workerId(configuration.getWorkerId().get());
        }
        if (configuration.getMaxTasks().isPresent()) {
            clientBuilder.maxTasks(configuration.getMaxTasks().get());
        }
        if (configuration.getUsePriority().isPresent()) {
            clientBuilder.usePriority(configuration.getUsePriority().get());
        }
        if (configuration.getDefaultSerializationFormat().isPresent()) {
            clientBuilder.defaultSerializationFormat(configuration.getDefaultSerializationFormat().get());
        }
        if (configuration.getDateFormat().isPresent()) {
            clientBuilder.dateFormat(configuration.getDateFormat().get());
        }
        if (configuration.getAsyncResponseTimeout().isPresent()) {
            clientBuilder.asyncResponseTimeout(configuration.getAsyncResponseTimeout().get());
        }
        if (configuration.getLockDuration().isPresent()) {
            clientBuilder.lockDuration(configuration.getLockDuration().get());
        }
        if (configuration.getDisableAutoFetching().isPresent() && configuration.getDisableAutoFetching().get()) {
            // TODO Is this really useful? Does the user even has the possibility to manually start this? I don't think so.
            clientBuilder.disableAutoFetching();
        }
        if (configuration.getDisableBackoffStragey().isPresent() && configuration.getDisableBackoffStragey().get()) {
            clientBuilder.disableBackoffStrategy();
        }

        return clientBuilder.build();
    }
}
