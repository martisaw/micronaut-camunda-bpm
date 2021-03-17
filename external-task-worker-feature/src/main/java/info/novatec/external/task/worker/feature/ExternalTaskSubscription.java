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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Martin Sawilla
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
public @interface ExternalTaskSubscription {

    String topicName();

    long lockDuration() default 20000;

    String[] variables() default "";

    boolean  localVariables() default false;

    String businessKey() default "";

    String processDefinitionId() default "";

    String[] processDefinitionIdIn() default "";

    String processDefinitionKey() default "";

    String[] processDefinitionKeyIn() default "";

    String processDefinitionVersionTag() default "";

    boolean withoutTenantId() default false;

    String[] tenantIdIn() default "";

    boolean includeExtensionProperties() default false;

}
