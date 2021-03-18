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
package info.novatec.external.task.worker.feature.test

import info.novatec.external.task.worker.feature.ExternalWorkerBuilder
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.camunda.bpm.client.topic.impl.TopicSubscriptionManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class ExternalWorkerBuilderTest {

    @Inject
    lateinit var topicSubscriptionManager: TopicSubscriptionManager

    @Inject
    lateinit var applicationContext: ApplicationContext

    @Test
    fun `do nothing test` () {
        println("hello")
    }

    @Requires(beans = [ExternalWorkerBuilder::class])
    @Test
    fun `happy path`() {

       Assertions.assertEquals("my-topic", topicSubscriptionManager.subscriptions[0])

        val x = applicationContext.findBean(ExternalWorkerBuilder::class.java)

        // I wont get TopicSubscriptionManager

        Assertions.assertEquals(null, x.get())

    /*topicSubscriptionManager.subscriptions.forEach{
            println(it)
        }
        println(topicSubscriptionManager.engineClient.baseUrl)
        */


        // Do I need an example application?
        // Configure it and make client listen to it

        // I could check that @ExternalSubscriptionBean is present and works
        // I can check that the connection to the server does not throw any errors? -> base url correct?

        // baseUrl, Topic should be present.. Maybe I can inject the BEAN for the topic? and get the config values??
    }

    // TODO implement tests that check the configuration possibilities
    // Well I probably need a Camunda BPM Platform Server the client can connect to?
    // What exactly do I want to check?
}