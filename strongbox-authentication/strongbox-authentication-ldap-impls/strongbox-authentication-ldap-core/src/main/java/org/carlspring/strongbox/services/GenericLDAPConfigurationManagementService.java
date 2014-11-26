package org.carlspring.strongbox.services;

import org.carlspring.strongbox.configuration.AttributeMappings;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * @author mtodorov
 */
public interface GenericLDAPConfigurationManagementService<T>
{

    void setConfiguration(T ldapConfiguration)
            throws IOException, JAXBException;

    T getConfiguration();

    public String getHost();

    public void setHost(String host)
            throws IOException, JAXBException;

    public int getPort();

    public void setPort(int port)
            throws IOException, JAXBException;

    public String getProtocol();

    public void setProtocol(String protocol)
            throws IOException, JAXBException;

    public String getUsername();

    public void setUsername(String username)
            throws IOException, JAXBException;

    public String getPassword();

    public void setPassword(String password)
            throws IOException, JAXBException;

    public String getAuthenticationType();

    public void setAuthenticationType(String authenticationType)
            throws IOException, JAXBException;

    public String getRootDn();

    public void setRootDn(String rootDn)
            throws IOException, JAXBException;

    public int getTimeout();

    public void setTimeout(int timeout)
            throws IOException, JAXBException;

    public boolean shouldBindAnonymously();

    public boolean shouldBindWithUsernameAndPassword();

    public boolean shouldBindWithPrincipal();

    public String getDomain();

    public void setDomain(String domain)
            throws IOException, JAXBException;

    public String getLoginMode();

    public void setLoginMode(String loginMode)
            throws IOException, JAXBException;

    public AttributeMappings getAttributeMappings();

    public void setAttributeMappings(AttributeMappings attributeMappings)
            throws IOException, JAXBException;

}
