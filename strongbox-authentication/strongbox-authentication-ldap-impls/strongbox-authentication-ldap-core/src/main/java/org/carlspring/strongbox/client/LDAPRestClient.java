package org.carlspring.strongbox.client;

import org.carlspring.strongbox.configuration.LDAPConfiguration;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * @author mtodorov
 */
public class LDAPRestClient extends AbstractLDAPRestClient
{


    public int setLDAPConfiguration(LDAPConfiguration configuration)
            throws IOException, JAXBException
    {
        return setServerConfiguration(configuration, "/configuration/ldap/xml", LDAPConfiguration.class);
    }

    public LDAPConfiguration getLDAPConfiguration()
            throws IOException, JAXBException
    {
        return (LDAPConfiguration) getServerConfiguration("/configuration/ldap/xml", LDAPConfiguration.class);
    }

    public String getConfigurationBaseUrl()
    {
        return getContextBaseUrl() + "/configuration/ldap";
    }

}
