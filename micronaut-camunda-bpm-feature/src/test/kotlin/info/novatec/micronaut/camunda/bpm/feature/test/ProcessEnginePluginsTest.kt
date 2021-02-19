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
package info.novatec.micronaut.camunda.bpm.feature.test

import info.novatec.micronaut.camunda.bpm.feature.MnProcessEngineConfiguration
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import javax.inject.Inject

/**
 * @author Martin Sawilla
 */

@MicronautTest
class ProcessEnginePluginsTest {

    @Inject
    lateinit var processEngineConfiguration: MnProcessEngineConfiguration

    @Test
    fun `check that DoNothingPlugin is automatically loaded` () {
        val plugins =  processEngineConfiguration.processEnginePlugins
        assertTrue(plugins.isNotEmpty())
        assertTrue(plugins.size == 1)
        assertTrue(plugins[0].javaClass == DoNothingProcessEnginePlugin::class.java)
    }
}