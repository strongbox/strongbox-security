package org.carlspring.strongbox.rest;

import org.carlspring.strongbox.client.LDAPRestClient;
import org.carlspring.strongbox.configuration.*;
import org.carlspring.strongbox.resource.ConfigurationResourceResolver;
import org.carlspring.strongbox.xml.parsers.GenericParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author mtodorov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/strongbox-*-context.xml",
                                    "classpath*:/META-INF/spring/strongbox-*-context.xml" })
public class LDAPConfigurationManagementRestletTest
{

    @Autowired
    private ConfigurationResourceResolver configurationResourceResolver;

    private LDAPRestClient client = new LDAPRestClient();

    GenericParser<LDAPConfiguration> parser = new GenericParser<>(LDAPConfiguration.class);

    LDAPConfiguration ldapConfiguration;

    Resource resource;


    @Before
    public void setUp()
            throws Exception
    {
        resource = configurationResourceResolver.getConfigurationResource("security.users.xml",
                                                                          "etc/conf/security-authentication-ldap.xml");

        ldapConfiguration = parser.parse(resource.getInputStream());
    }

    @After
    public void tearDown()
            throws Exception
    {
        parser.store(ldapConfiguration, resource.getFile());
    }

    @Test
    public void testSetAndGetConfiguration()
            throws IOException, JAXBException
    {
        LDAPConfiguration configuration = ldapConfiguration;
        configuration.setTimeout(60000);

        final int response = client.setLDAPConfiguration(configuration);

        assertEquals("Failed to retrieve configuration!", 200, response);

        final LDAPConfiguration c = client.getLDAPConfiguration();

        assertNotNull("Failed to upload LDAP configuration!", c.getHost());
        assertEquals("Failed to upload LDAP configuration!", "localhost", c.getHost());
        assertEquals("Failed to upload LDAP configuration!", 60000, c.getTimeout());
    }

    @Test
    public void testSetAndGetLdapHost()
            throws IOException, JAXBException
    {
        int status = client.setLdapHost("127.0.0.1");

        assertEquals("Failed to set LDAP host!", 200, status);

        final String ldapHost = client.getLdapHost();

        assertEquals("Failed to get LDAP host!", "127.0.0.1", ldapHost);
    }

    @Test
    public void testSetAndGetLdapPort()
    {
        int status = client.setLdapPort(41636);

        assertEquals("Failed to set LDAP port!", 200, status);

        final int port = client.getLdapPort();

        assertEquals("Failed to get LDAP port!", 41636, port);
    }

    @Test
    public void testSetAndGetLdapProtocol()
            throws IOException, JAXBException
    {
        int status = client.setLdapProtocol("ldaps");

        assertEquals("Failed to set LDAP protocol!", 200, status);

        final String ldapProtocol = client.getLdapProtocol();

        assertEquals("Failed to get LDAP protocol!", "ldaps", ldapProtocol);
    }

    @Test
    public void testSetAndGetLdapUsername()
            throws IOException, JAXBException
    {
        int status = client.setLdapUsername("ldap-username");

        assertEquals("Failed to set LDAP username!", 200, status);

        final String ldapUsername = client.getLdapUsername();

        assertEquals("Failed to get LDAP username!", "ldap-username", ldapUsername);
    }

    @Test
    public void testSetLdapPassword()
            throws IOException, JAXBException
    {
        int status = client.setLdapPassword("ldap-password");

        assertEquals("Failed to set LDAP password!", 200, status);
    }

    @Test
    public void testSetAndGetAuthenticationType()
            throws IOException, JAXBException
    {
        int status = client.setAuthenticationType("none");

        assertEquals("Failed to set LDAP authentication type!", 200, status);

        final String authenticationType = client.getAuthenticationType();

        assertEquals("Failed to get LDAP authentication type!", "none", authenticationType);
    }

    @Test
    public void testSetAndGetRootDn()
            throws IOException, JAXBException
    {
        int status = client.setRootDn("dc=dev,dc=carlspring,dc=com");

        assertEquals("Failed to set LDAP authentication type!", 200, status);

        final String rootDn = client.getRootDn();

        assertEquals("Failed to get LDAP authentication type!", "dc=dev,dc=carlspring,dc=com", rootDn);
    }

    @Test
    public void testSetAndGetTimeout()
            throws IOException, JAXBException
    {
        int status = client.setTimeout(360000);

        assertEquals("Failed to set LDAP timeout!", 200, status);

        final int timeout = client.getTimeout();

        assertEquals("Failed to get LDAP timeout!", 360000, timeout);
    }

    @Test
    public void testSetAndGetDomain()
            throws IOException, JAXBException
    {
        int status = client.setDomain("intranet");

        assertEquals("Failed to set LDAP domain!", 200, status);

        final String domain = client.getDomain();

        assertEquals("Failed to get LDAP domain!", "intranet", domain);
    }

    @Test
    public void testSetAndGetLoginMode()
            throws IOException, JAXBException
    {
        int status = client.setLoginMode("principal");

        assertEquals("Failed to set LDAP login mode!", 200, status);

        final String loginMode = client.getLoginMode();

        assertEquals("Failed to get LDAP login mode!", "principal", loginMode);
    }

    @Test
    public void testSetAndGetAttributeMappings()
            throws IOException, JAXBException
    {
        UserMapping userMapping = new UserMapping();
        userMapping.setFullName("cnx");

        GroupMapping groupMapping = new GroupMapping();
        groupMapping.setGroupId("cnx");

        AttributeMappings attributeMappings = new AttributeMappings();
        attributeMappings.setUserMapping(userMapping);
        attributeMappings.setGroupMapping(groupMapping);

        int status = client.setAttributeMappings(attributeMappings);

        assertEquals("Failed to set LDAP attribute mappings!", 200, status);

        final AttributeMappings am = client.getAttributeMappings();

        assertNotNull("Failed to get LDAP attribute mappings!", am);
        assertEquals("Failed to get LDAP attribute mappings!", "cnx", am.getUserMapping().getFullName());
        assertEquals("Failed to get LDAP attribute mappings!", "cnx", am.getGroupMapping().getGroupId());
    }

}
