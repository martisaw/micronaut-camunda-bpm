package info.novatec.external.task.worker.feature;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("external-client")
public interface Configuration {

    String getBaseUrl();
}
