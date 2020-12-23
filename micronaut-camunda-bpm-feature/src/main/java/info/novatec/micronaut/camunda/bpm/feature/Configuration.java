package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.bind.annotation.Bindable;
import org.camunda.bpm.engine.ProcessEngineConfiguration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author Tobias Schäfer
 */
@ConfigurationProperties("camunda.bpm")
public interface Configuration {

    @NotBlank
    @Bindable(defaultValue = ProcessEngineConfiguration.HISTORY_AUTO)
    String getHistoryLevel();

    @NotNull
    Database getDatabase();

    @NotNull
    Telemetry getTelemetry();

    @NotNull
    AdminUser getAdminUser();

    @NotNull
    Webapps getWebapps();

    @NotNull
    Rest getRest();

    @ConfigurationProperties("database")
    interface Database {

        @NotBlank
        @Bindable(defaultValue = ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
        String getSchemaUpdate();
    }

    @ConfigurationProperties("telemetry")
    interface Telemetry {

        @Bindable(defaultValue = "true")
        boolean isTelemetryReporterActivate();

        @Bindable(defaultValue = "false")
        boolean isInitializeTelemetry();
    }

    @ConfigurationProperties("adminUser")
    interface AdminUser {

        @Bindable
        String getId();

        @Bindable
        String getPassword();

        @Bindable
        String getFirstname();

        @Bindable
        String getLastname();

        @Bindable
        Optional<String> getEmail();
    }

    @ConfigurationProperties("webapps")
    interface Webapps {

        @Bindable(defaultValue = "false")
        boolean isEnabled();

        @Bindable(defaultValue = "/camunda")
        String getContextPath();
    }

    @ConfigurationProperties("rest")
    interface Rest {

        @Bindable(defaultValue = "false")
        boolean isEnabled();

        @Bindable(defaultValue = "/engine-rest")
        String getContextPath();
    }

}
