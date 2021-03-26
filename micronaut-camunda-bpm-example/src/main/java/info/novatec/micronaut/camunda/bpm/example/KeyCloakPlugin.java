package info.novatec.micronaut.camunda.bpm.example;

import io.micronaut.context.annotation.ConfigurationProperties;
import org.camunda.bpm.extension.keycloak.plugin.KeycloakIdentityProviderPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
@ConfigurationProperties("plugin.identity.keycloak")
public class KeyCloakPlugin extends KeycloakIdentityProviderPlugin {
    private static final Logger log = LoggerFactory.getLogger(KeyCloakPlugin.class);

    public KeyCloakPlugin() {
        // Start Keycloak beforehand:
        // Use Docker to start a keycloak instance
        // docker run -p 8080:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -e DB_VENDOR="h2" quay.io/keycloak/keycloak:12.0.4

        log.info("KeyCloakPlugin started");

    }
}
