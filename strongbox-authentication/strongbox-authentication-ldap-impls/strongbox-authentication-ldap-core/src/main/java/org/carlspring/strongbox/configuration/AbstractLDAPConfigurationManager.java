package org.carlspring.strongbox.configuration;

import org.carlspring.strongbox.resource.ConfigurationResourceResolver;

import java.io.IOException;

import org.springframework.core.io.Resource;

/**
 * @author mtodorov
 */
public abstract class AbstractLDAPConfigurationManager
        extends AbstractConfigurationManager<GenericLDAPConfiguration>
{


    public AbstractLDAPConfigurationManager()
    {
        super(GenericLDAPConfiguration.class);
    }

    public AbstractLDAPConfigurationManager(Class clazz)
    {
        super(clazz);
    }

    @SuppressWarnings("unchecked")
    public GenericLDAPConfiguration getConfiguration()
    {
        return (GenericLDAPConfiguration) super.getConfiguration();
    }

    public abstract Resource getConfigurationResource()
            throws IOException;

    public abstract ConfigurationResourceResolver getConfigurationResourceResolver();

}
