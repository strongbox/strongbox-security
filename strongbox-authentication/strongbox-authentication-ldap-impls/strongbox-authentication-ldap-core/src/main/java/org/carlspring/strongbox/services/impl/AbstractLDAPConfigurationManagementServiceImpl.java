package org.carlspring.strongbox.services.impl;

import org.carlspring.strongbox.configuration.AbstractLDAPConfigurationManager;
import org.carlspring.strongbox.configuration.AttributeMappings;
import org.carlspring.strongbox.configuration.GenericLDAPConfiguration;
import org.carlspring.strongbox.services.GenericLDAPConfigurationManagementService;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * @author mtodorov
 */
public abstract class AbstractLDAPConfigurationManagementServiceImpl
        implements GenericLDAPConfigurationManagementService<GenericLDAPConfiguration>
{


    @Override
    public void setConfiguration(GenericLDAPConfiguration ldapConfiguration)
            throws IOException, JAXBException
    {
        //noinspection unchecked
        getConfigurationManager().setConfiguration(ldapConfiguration);
        getConfigurationManager().store();
    }

    @Override
    public GenericLDAPConfiguration getConfiguration()
    {
        return getConfigurationManager().getConfiguration();
    }

    @Override
    public String getHost()
    {
        return getConfigurationManager().getConfiguration().getHost();
    }

    @Override
    public void setHost(String host)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setHost(host);
        getConfigurationManager().store();
    }

    @Override
    public int getPort()
    {
        return getConfigurationManager().getConfiguration().getPort();
    }

    @Override
    public void setPort(int port)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setPort(port);
        getConfigurationManager().store();
    }

    @Override
    public String getProtocol()
    {
        return getConfigurationManager().getConfiguration().getProtocol();
    }

    @Override
    public void setProtocol(String protocol)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setProtocol(protocol);
        getConfigurationManager().store();
    }

    @Override
    public String getUsername()
    {
        return getConfigurationManager().getConfiguration().getUsername();
    }

    @Override
    public void setUsername(String username)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setUsername(username);
        getConfigurationManager().store();
    }

    @Override
    public String getPassword()
    {
        return getConfigurationManager().getConfiguration().getPassword();
    }

    @Override
    public void setPassword(String password)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setPassword(password);
        getConfigurationManager().store();
    }

    @Override
    public String getAuthenticationType()
    {
        return getConfigurationManager().getConfiguration().getAuthenticationType();
    }

    @Override
    public void setAuthenticationType(String authenticationType)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setAuthenticationType(authenticationType);
        getConfigurationManager().store();
    }

    @Override
    public String getRootDn()
    {
        return getConfigurationManager().getConfiguration().getRootDn();
    }

    @Override
    public void setRootDn(String rootDn)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setRootDn(rootDn);
        getConfigurationManager().store();
    }

    @Override
    public int getTimeout()
    {
        return getConfigurationManager().getConfiguration().getTimeout();
    }

    @Override
    public void setTimeout(int timeout)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setTimeout(timeout);
        getConfigurationManager().store();
    }

    @Override
    public boolean shouldBindAnonymously()
    {
        return getConfigurationManager().getConfiguration().shouldBindAnonymously();
    }

    @Override
    public boolean shouldBindWithUsernameAndPassword()
    {
        return getConfigurationManager().getConfiguration().shouldBindWithUsernameAndPassword();
    }

    @Override
    public boolean shouldBindWithPrincipal()
    {
        return getConfigurationManager().getConfiguration().shouldBindWithPrincipal();
    }

    @Override
    public String getDomain()
    {
        return getConfigurationManager().getConfiguration().getDomain();
    }

    @Override
    public void setDomain(String domain)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setDomain(domain);
        getConfigurationManager().store();
    }

    @Override
    public String getLoginMode()
    {
        return getConfigurationManager().getConfiguration().getLoginMode();
    }

    @Override
    public void setLoginMode(String loginMode)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setLoginMode(loginMode);
        getConfigurationManager().store();
    }

    @Override
    public AttributeMappings getAttributeMappings()
    {
        return getConfigurationManager().getConfiguration().getAttributeMappings();
    }

    @Override
    public void setAttributeMappings(AttributeMappings attributeMappings)
            throws IOException, JAXBException
    {
        getConfigurationManager().getConfiguration().setAttributeMappings(attributeMappings);
        getConfigurationManager().store();
    }
    
    public abstract AbstractLDAPConfigurationManager getConfigurationManager();
    
}
