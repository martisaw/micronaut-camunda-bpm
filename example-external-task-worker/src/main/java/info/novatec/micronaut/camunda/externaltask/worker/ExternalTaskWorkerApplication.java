/*
 * Copyright 2020-2021 original authors
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
package info.novatec.micronaut.camunda.externaltask.worker;

import io.micronaut.runtime.Micronaut;

// TODO Spring Boot Starter also allows the configuration of the baseUrl etc. here
public class ExternalTaskWorkerApplication {

    public static void main(String[] args) {
        Micronaut.run(ExternalTaskWorkerApplication.class, args);
    }
}
