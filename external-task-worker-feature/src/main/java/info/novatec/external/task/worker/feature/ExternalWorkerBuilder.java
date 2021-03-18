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

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.ExternalTaskClientBuilder;
import org.camunda.bpm.client.backoff.BackoffStrategy;
import org.camunda.bpm.client.interceptor.ClientRequestInterceptor;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.List;

/**
 * @author Martin Sawilla
 */
@Requires(beans = ExternalTaskHandler.class)
@Singleton
public class ExternalWorkerBuilder implements ApplicationEventListener<ServerStartupEvent> {

    protected static final Logger log = LoggerFactory.getLogger(ExternalWorkerBuilder.class);

    protected final ExternalTaskHandler externalTaskHandler;
    protected final ExternalTaskSubscription externalTaskSubscription;
    protected final Configuration configuration;
    protected ClientRequestInterceptor clientRequestInterceptor = null;
    protected BackoffStrategy backoffStrategy = null;

    // TODO Support multiple Subscriptions -> At the moment I only inject one Handler, BackoffStrategy and Interceptor
    public ExternalWorkerBuilder(ExternalTaskHandler externalTaskHandler,
                                 Configuration configuration,
                                 // This list can be empty. If I directly inject the ClientRequestInterceptor I'll get an Error it it's missing.
                                 List<ClientRequestInterceptor> clientRequestInterceptor,
                                 List<BackoffStrategy> backoffStrategy) throws Exception {
        this.externalTaskHandler = externalTaskHandler;
        this.externalTaskSubscription = this.externalTaskHandler.getClass().getAnnotation(ExternalTaskSubscription.class);
        this.configuration = configuration;
        if (clientRequestInterceptor.size() == 1) {
            this.clientRequestInterceptor = clientRequestInterceptor.get(0);
        } else if(clientRequestInterceptor.size() > 1) {
            throw new Exception("To many ClientRequestInterceptors present. Please provide only one.");
        }
        if (backoffStrategy.size() == 1) {
            this.backoffStrategy = backoffStrategy.get(0);
        } else if (backoffStrategy.size() > 1){
            throw new Exception("To many BackoffStrategies present. Please provide only one");
        }
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {

        ExternalTaskClient client = buildClient();

        buildTopicSubscription(client);

        // Idea for multiple Subscriptions: I can also do a factory and return multiple TopicSubscriptions (Prototype)
        // Potential problem: When do they get initialized?
        // How do I inject the right BackoffStrategy and the right RequestInterceptors?
    }

    protected ExternalTaskClient buildClient() {

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

    protected void buildTopicSubscription(ExternalTaskClient client) {

        TopicSubscriptionBuilder topicSubscriptionBuilder = client.subscribe(externalTaskSubscription.topicName());

        topicSubscriptionBuilder.handler(externalTaskHandler);

        topicSubscriptionBuilder.lockDuration(externalTaskSubscription.lockDuration());

        if(!externalTaskSubscription.variables()[0].equals("")) {
            topicSubscriptionBuilder.variables(externalTaskSubscription.variables());
        }

        topicSubscriptionBuilder.localVariables(externalTaskSubscription.localVariables());

        if(!externalTaskSubscription.businessKey().equals("")) {
            topicSubscriptionBuilder.businessKey(externalTaskSubscription.businessKey());
        }

        if(!externalTaskSubscription.processDefinitionId().equals("")) {
            topicSubscriptionBuilder.processDefinitionId(externalTaskSubscription.processDefinitionId());
        }

        if(!externalTaskSubscription.processDefinitionIdIn()[0].equals("")) {
            topicSubscriptionBuilder.processDefinitionIdIn(externalTaskSubscription.processDefinitionIdIn());
        }

        if(!externalTaskSubscription.processDefinitionKey().equals("")) {
            topicSubscriptionBuilder.processDefinitionKey(externalTaskSubscription.processDefinitionKey());
        }

        if(!externalTaskSubscription.processDefinitionKeyIn()[0].equals("")) {
            topicSubscriptionBuilder.processDefinitionKeyIn(externalTaskSubscription.processDefinitionKeyIn());
        }

        if(!externalTaskSubscription.processDefinitionVersionTag().equals("")) {
            topicSubscriptionBuilder.processDefinitionVersionTag(externalTaskSubscription.processDefinitionVersionTag());
        }

        if(externalTaskSubscription.withoutTenantId()) {
            topicSubscriptionBuilder.withoutTenantId();
        }

        if(!externalTaskSubscription.tenantIdIn()[0].equals("")) {
            topicSubscriptionBuilder.tenantIdIn(externalTaskSubscription.tenantIdIn());
        }

        topicSubscriptionBuilder.includeExtensionProperties(externalTaskSubscription.includeExtensionProperties());

        topicSubscriptionBuilder.open();
    }
}
