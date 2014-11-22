package org.carlspring.strongbox.services.impl;

import org.carlspring.strongbox.configuration.AttributeMappings;
import org.carlspring.strongbox.configuration.LDAPConfiguration;
import org.carlspring.strongbox.configuration.LDAPConfigurationManager;
import org.carlspring.strongbox.services.LDAPConfigurationManagementService;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public class LDAPConfigurationManagementServiceImpl implements LDAPConfigurationManagementService
{

    @Autowired
    private LDAPConfigurationManager ldapConfigurationManager;


    @Override
    public void setConfiguration(LDAPConfiguration ldapConfiguration)
            throws IOException, JAXBException
    {
        //noinspection unchecked
        ldapConfigurationManager.setConfiguration(ldapConfiguration);
        ldapConfigurationManager.store();
    }

    @Override
    public LDAPConfiguration getConfiguration()
    {
        return ldapConfigurationManager.getConfiguration();
    }

    @Override
    public String getHost()
    {
        return ldapConfigurationManager.getConfiguration().getHost();
    }

    @Override
    public void setHost(String host)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setHost(host);
        ldapConfigurationManager.store();
    }

    @Override
    public int getPort()
    {
        return ldapConfigurationManager.getConfiguration().getPort();
    }

    @Override
    public void setPort(int port)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setPort(port);
        ldapConfigurationManager.store();
    }

    @Override
    public String getProtocol()
    {
        return ldapConfigurationManager.getConfiguration().getProtocol();
    }

    @Override
    public void setProtocol(String protocol)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setProtocol(protocol);
        ldapConfigurationManager.store();
    }

    @Override
    public String getUsername()
    {
        return ldapConfigurationManager.getConfiguration().getUsername();
    }

    @Override
    public void setUsername(String username)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setUsername(username);
        ldapConfigurationManager.store();
    }

    @Override
    public String getPassword()
    {
        return ldapConfigurationManager.getConfiguration().getPassword();
    }

    @Override
    public void setPassword(String password)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setPassword(password);
        ldapConfigurationManager.store();
    }

    @Override
    public String getAuthenticationType()
    {
        return ldapConfigurationManager.getConfiguration().getAuthenticationType();
    }

    @Override
    public void setAuthenticationType(String authenticationType)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setAuthenticationType(authenticationType);
        ldapConfigurationManager.store();
    }

    @Override
    public String getRootDn()
    {
        return ldapConfigurationManager.getConfiguration().getRootDn();
    }

    @Override
    public void setRootDn(String rootDn)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setRootDn(rootDn);
        ldapConfigurationManager.store();
    }

    @Override
    public int getTimeout()
    {
        return ldapConfigurationManager.getConfiguration().getTimeout();
    }

    @Override
    public void setTimeout(int timeout)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setTimeout(timeout);
        ldapConfigurationManager.store();
    }

    @Override
    public boolean shouldBindAnonymously()
    {
        return ldapConfigurationManager.getConfiguration().shouldBindAnonymously();
    }

    @Override
    public boolean shouldBindWithUsernameAndPassword()
    {
        return ldapConfigurationManager.getConfiguration().shouldBindWithUsernameAndPassword();
    }

    @Override
    public boolean shouldBindWithPrincipal()
    {
        return ldapConfigurationManager.getConfiguration().shouldBindWithPrincipal();
    }

    @Override
    public String getDomain()
    {
        return ldapConfigurationManager.getConfiguration().getDomain();
    }

    @Override
    public void setDomain(String domain)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setDomain(domain);
        ldapConfigurationManager.store();
    }

    @Override
    public String getLoginMode()
    {
        return ldapConfigurationManager.getConfiguration().getLoginMode();
    }

    @Override
    public void setLoginMode(String loginMode)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setLoginMode(loginMode);
        ldapConfigurationManager.store();
    }

    @Override
    public AttributeMappings getAttributeMappings()
    {
        return ldapConfigurationManager.getConfiguration().getAttributeMappings();
    }

    @Override
    public void setAttributeMappings(AttributeMappings attributeMappings)
            throws IOException, JAXBException
    {
        ldapConfigurationManager.getConfiguration().setAttributeMappings(attributeMappings);
        ldapConfigurationManager.store();
    }

}
