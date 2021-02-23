package info.novatec.micronaut.camunda.bpm.example;

import org.camunda.bpm.identity.impl.ldap.plugin.LdapIdentityProviderPlugin;

import javax.inject.Singleton;

/**
 * @author Luc Weinbrecht
 * @author Martin Sawilla
 *
 * Using an open online LDAP to provide an example
 * https://www.forumsys.com/tutorials/integration-how-to/ldap/online-ldap-test-server/
 * Log in e.g. with einstein + password
 *
 * Important: Remove the admin user in application.yml
 */

@Singleton
public class LdapPluginConfiguration extends LdapIdentityProviderPlugin {

    public LdapPluginConfiguration() {
        super.setServerUrl("ldap://ldap.forumsys.com:389");
        super.setManagerDn("cn=read-only-admin,dc=example,dc=com");
        super.setManagerPassword("password");
        super.setBaseDn("dc=example,dc=com");
    }
}
