package org.carlspring.strongbox.configuration;

import org.carlspring.strongbox.resource.ConfigurationResourceResolver;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
@Scope("singleton")
public class LDAPConfigurationManager extends AbstractConfigurationManager<LDAPConfiguration>
{

    @Autowired
    private ConfigurationResourceResolver configurationResourceResolver;


    public LDAPConfigurationManager()
    {
        super(LDAPConfiguration.class);
    }

    @SuppressWarnings("unchecked")
    public LDAPConfiguration getConfiguration()
    {
        return (LDAPConfiguration) super.getConfiguration();
    }

    public Resource getConfigurationResource()
            throws IOException
    {
        return configurationResourceResolver.getConfigurationResource("security.users.xml",
                                                                      "etc/conf/security-authentication-ldap.xml");
    }

}
