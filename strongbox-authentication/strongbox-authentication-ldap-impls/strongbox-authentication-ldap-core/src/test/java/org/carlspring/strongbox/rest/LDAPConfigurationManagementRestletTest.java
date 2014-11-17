package org.carlspring.strongbox.rest;

import org.carlspring.strongbox.client.RestClient;
import org.carlspring.strongbox.configuration.LDAPConfiguration;
import org.carlspring.strongbox.resource.ConfigurationResourceResolver;
import org.carlspring.strongbox.xml.parsers.GenericParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

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


    @Test
    public void testSetAndGetConfiguration()
            throws IOException, JAXBException
    {
        LDAPConfiguration configuration = loadLDAPConfiguration();
        configuration.setTimeout(60000);

        final int response = client.setLDAPConfiguration(configuration);

        assertEquals("Failed to retrieve configuration!", 200, response);

        final LDAPConfiguration c = client.getLDAPConfiguration();

        assertNotNull("Failed to upload LDAP configuration!", c.getHost());
        assertEquals("Failed to upload LDAP configuration!", "localhost", c.getHost());
        assertEquals("Failed to upload LDAP configuration!", 60000, c.getTimeout());
    }

    class LDAPRestClient extends RestClient
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

    }

    private LDAPConfiguration loadLDAPConfiguration()
            throws IOException, JAXBException
    {
        Resource resource = configurationResourceResolver.getConfigurationResource("security.users.xml",
                                                                                   "etc/conf/security-authentication-ldap.xml");

        GenericParser<LDAPConfiguration> parser = new GenericParser<>(LDAPConfiguration.class);

        //noinspection UnnecessaryLocalVariable
        LDAPConfiguration ldapConfiguration = parser.parse(resource.getInputStream());

        return ldapConfiguration;
    }

}
