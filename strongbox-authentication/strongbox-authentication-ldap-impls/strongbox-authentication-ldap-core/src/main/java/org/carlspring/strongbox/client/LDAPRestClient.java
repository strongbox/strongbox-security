package org.carlspring.strongbox.client;

import org.carlspring.strongbox.configuration.AttributeMappings;
import org.carlspring.strongbox.configuration.LDAPConfiguration;
import org.carlspring.strongbox.xml.parsers.GenericParser;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mtodorov
 */
public class LDAPRestClient extends RestClient
{

    private static final Logger logger = LoggerFactory.getLogger(LDAPRestClient.class);


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

    public String getLdapHost()
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/host");

        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public int setLdapHost(String host)
            throws IOException, JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/host/" + host);

        Response response = resource.request(MediaType.TEXT_PLAIN)
                                    .put(Entity.entity(host, MediaType.TEXT_PLAIN));

        return response.getStatus();
    }

    public int setLdapPort(int port)
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getContextBaseUrl() + "/configuration/ldap/port/" + port);

        Response response = resource.request(MediaType.TEXT_PLAIN)
                                    .put(Entity.entity(port, MediaType.TEXT_PLAIN));

        return response.getStatus();
    }

    public int getLdapPort()
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getContextBaseUrl() + "/configuration/ldap/port");

        return resource.request(MediaType.TEXT_PLAIN).get(Integer.class);
    }

    public String getLdapProtocol()
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/protocol");

        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public int setLdapProtocol(String protocol)
            throws IOException, JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/protocol/" + protocol);

        Response response = resource.request(MediaType.TEXT_PLAIN)
                                    .put(Entity.entity(protocol, MediaType.TEXT_PLAIN));

        return response.getStatus();
    }

    public String getLdapUsername()
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/username");

        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public int setLdapUsername(String username)
            throws IOException, JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/username/" + username);

        Response response = resource.request(MediaType.TEXT_PLAIN)
                                    .put(Entity.entity(username, MediaType.TEXT_PLAIN));

        return response.getStatus();
    }

    public int setLdapPassword(String password)
            throws IOException, JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/password/" + password);

        Response response = resource.request(MediaType.TEXT_PLAIN)
                                    .put(Entity.entity(password, MediaType.TEXT_PLAIN));

        return response.getStatus();
    }

    public String getAuthenticationType()
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/authentication-type");

        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public int setAuthenticationType(String authenticationType)
            throws IOException, JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/authentication-type/" + authenticationType);

        Response response = resource.request(MediaType.TEXT_PLAIN)
                                    .put(Entity.entity(authenticationType, MediaType.TEXT_PLAIN));

        return response.getStatus();
    }

    public String getRootDn()
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/root-dn");

        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public int setRootDn(String rootDn)
            throws IOException, JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/root-dn/" + rootDn);

        Response response = resource.request(MediaType.TEXT_PLAIN)
                                    .put(Entity.entity(rootDn, MediaType.TEXT_PLAIN));

        return response.getStatus();
    }

    public int getTimeout()
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/timeout");

        return Integer.parseInt(resource.request(MediaType.TEXT_PLAIN).get(String.class));
    }

    public int setTimeout(int timeout)
            throws IOException, JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/timeout/" + timeout);

        Response response = resource.request(MediaType.TEXT_PLAIN).put(Entity.entity(timeout, MediaType.TEXT_PLAIN));

        return response.getStatus();
    }

    public String getDomain()
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/domain");

        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public int setDomain(String domain)
            throws IOException, JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/domain/" + domain);

        Response response = resource.request(MediaType.TEXT_PLAIN).put(Entity.entity(domain, MediaType.TEXT_PLAIN));

        return response.getStatus();
    }

    public String getLoginMode()
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/login-mode");

        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public int setLoginMode(String loginMode)
            throws IOException, JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/login-mode/" + loginMode);

        Response response = resource.request(MediaType.TEXT_PLAIN).put(Entity.entity(loginMode, MediaType.TEXT_PLAIN));

        return response.getStatus();
    }

    public AttributeMappings getAttributeMappings()
            throws JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/attribute-mappings");

        final Response response = resource.request(MediaType.APPLICATION_XML).get();

        AttributeMappings attributeMappings = null;
        if (response.getStatus() == 200)
        {
            final String xml = response.readEntity(String.class);

            System.out.println(xml);

            final ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

            GenericParser<AttributeMappings> parser = new GenericParser<>(AttributeMappings.class);

            attributeMappings = parser.parse(bais);
        }

        return attributeMappings;
    }

    public int setAttributeMappings(AttributeMappings attributeMappings)
            throws IOException, JAXBException
    {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target(getConfigurationBaseUrl() + "/attribute-mappings");

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        GenericParser<AttributeMappings> parser = new GenericParser<>(AttributeMappings.class);
        parser.store(attributeMappings, baos);

        System.out.println(baos.toString());

        Response response = resource.request(MediaType.APPLICATION_XML)
                                    .put(Entity.entity(baos.toString("UTF-8"), MediaType.APPLICATION_XML));

        return response.getStatus();
    }

    /**
     * This method needs to be overridden by any LDAP-related implementations.
     * 
     * @return
     */
    protected String getConfigurationBaseUrl()
    {
        return getContextBaseUrl() + "/configuration/ldap";
    }

}
