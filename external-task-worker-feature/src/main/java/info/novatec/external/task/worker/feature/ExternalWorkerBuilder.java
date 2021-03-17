/*
 * Copyright 2021 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.novatec.external.task.worker.feature;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.ExternalTaskClientBuilder;
import org.camunda.bpm.client.backoff.BackoffStrategy;
import org.camunda.bpm.client.interceptor.ClientRequestInterceptor;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * @author Martin Sawilla
 */
@Singleton
public class ExternalWorkerBuilder implements ApplicationEventListener<ServerStartupEvent> {

    protected static final Logger log = LoggerFactory.getLogger(ExternalWorkerBuilder.class);

    protected final ExternalTaskHandler externalTaskHandler;
    protected final ExternalTaskSubscription externalTaskSubscription;
    protected final Configuration configuration;
    protected final ClientRequestInterceptor clientRequestInterceptor;
    protected final BackoffStrategy backoffStrategy;

    // TODO Support multiple Subscriptions -> At the moment I only inject one Handler, BackoffStrategy and Interceptor
    public ExternalWorkerBuilder(ExternalTaskHandler externalTaskHandler,
                                 Configuration configuration,
                                 ClientRequestInterceptor clientRequestInterceptor,
                                 BackoffStrategy backoffStrategy) {
        this.externalTaskHandler = externalTaskHandler;
        this.externalTaskSubscription = this.externalTaskHandler.getClass().getAnnotation(ExternalTaskSubscription.class);
        this.configuration = configuration;
        this.clientRequestInterceptor = clientRequestInterceptor;
        this.backoffStrategy = backoffStrategy;
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        ExternalTaskClient client = buildClient();

        client.subscribe(externalTaskSubscription.topic())
                .handler(this.externalTaskHandler)
                .open();
        // Idea for multiple Subscriptions: I can also do a factory and return multiple TopicSubscriptions (Prototype)
        // Potential problem: When do they get initialized?
    }

    protected ExternalTaskClient buildClient() {
        // TODO Test all of this!
        ExternalTaskClientBuilder clientBuilder = ExternalTaskClient.create();

        clientBuilder.baseUrl(configuration.getBaseUrl());

        configuration.getWorkerId().ifPresent(it -> clientBuilder.workerId(it));

        if (clientRequestInterceptor != null) {
            clientBuilder.addInterceptor(clientRequestInterceptor);
        }

        configuration.getMaxTasks().ifPresent(it -> clientBuilder.maxTasks(it));

        configuration.getUsePriority().ifPresent(it -> clientBuilder.usePriority(it));

        configuration.getDefaultSerializationFormat().ifPresent(it -> clientBuilder.defaultSerializationFormat(it));

        configuration.getDateFormat().ifPresent(it -> clientBuilder.dateFormat(it));

        configuration.getAsyncResponseTimeout().ifPresent(it -> clientBuilder.asyncResponseTimeout(it));

        configuration.getLockDuration().ifPresent(it -> clientBuilder.lockDuration(it));

        configuration.getDisableAutoFetching().ifPresent(it -> {
            if(it) {
                // TODO Is this really useful? Does the user even has the possibility to manually start this? I don't think so.
                clientBuilder.disableAutoFetching();
            }
        });

        if (backoffStrategy != null) {
            clientBuilder.backoffStrategy(backoffStrategy);
        }

        configuration.getDisableBackoffStragey().ifPresent(it -> {
            if(it) {
                clientBuilder.disableBackoffStrategy();
            }
        });

        return clientBuilder.build();
    }
}
