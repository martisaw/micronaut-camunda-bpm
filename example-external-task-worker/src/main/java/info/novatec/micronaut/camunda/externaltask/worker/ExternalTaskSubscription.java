package info.novatec.micronaut.camunda.externaltask.worker;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
public @interface ExternalTaskSubscription {

    String topic();

    String lockDuration() default "";
}
