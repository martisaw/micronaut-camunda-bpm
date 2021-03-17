package info.novatec.external.task.worker.feature;

import io.micronaut.context.annotation.ConfigurationProperties;

import java.util.Optional;

@ConfigurationProperties("external-client")
public interface Configuration {

    String getBaseUrl();

    Optional<String> getWorkerId(); // should be unique

    // Interceptors? How do I add them?

    Optional<Integer> getMaxTasks(); //default ist 10

    Optional<Boolean> getUsePriority(); // default is true

    Optional<String> getDefaultSerializationFormat(); //default application/json

    Optional<String> getDateFormat();

    Optional<Long> getAsyncResponseTimeout();

    Optional<Long> getLockDuration(); // default is 20 000 milliseconds

    Optional<Boolean> getDisableAutoFetching(); // disables autofetching. useless for us?

    // BackoffStrategy?? How do I add this?

    Optional<Boolean> getDisableBackoffStragey(); // can produce heavy load..
}
