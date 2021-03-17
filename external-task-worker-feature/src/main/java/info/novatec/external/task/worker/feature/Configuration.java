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

import io.micronaut.context.annotation.ConfigurationProperties;

import java.util.Optional;

/**
 * @author Martin Sawilla
 */
@ConfigurationProperties("external-client")
public interface Configuration {

    String getBaseUrl();

    // Most of the properties have default values that are set by camunda external task client

    Optional<String> getWorkerId();

    Optional<Integer> getMaxTasks();

    Optional<Boolean> getUsePriority();

    Optional<String> getDefaultSerializationFormat();

    Optional<String> getDateFormat();

    Optional<Long> getAsyncResponseTimeout();

    Optional<Long> getLockDuration();

    Optional<Boolean> getDisableAutoFetching(); // TODO Is this useful?

    Optional<Boolean> getDisableBackoffStragey();
}
