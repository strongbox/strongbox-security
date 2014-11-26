package org.carlspring.strongbox.xml.parsers;

import org.carlspring.strongbox.booters.ResourcesBooter;
import org.carlspring.strongbox.configuration.LDAPConfiguration;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertTrue;

/**
 * @author mtodorov
 */
public class LDAPConfigurationParserTest
{

    public static final String TEST_CLASSES = "target/test-classes";

    public static final String CONFIGURATION_BASEDIR = TEST_CLASSES + "/etc/conf";

    public static final String XML_FILE = CONFIGURATION_BASEDIR + "/security-authentication-ldap.xml";

    public static final String XML_OUTPUT_FILE = CONFIGURATION_BASEDIR + "/security-authentication-ldap-saved.xml";

    private GenericParser<LDAPConfiguration> parser = new GenericParser<LDAPConfiguration>(LDAPConfiguration.class);

    // This field is indeed used. It's execute() method is being invoked with a @PostConstruct.
    @SuppressWarnings("UnusedDeclaration")
    @Autowired
    private ResourcesBooter resourcesBooter;


    @Test
    public void testParseAuthenticationConfigurationLDAP()
            throws IOException, JAXBException
    {
        File xmlFile = new File(XML_FILE);

        System.out.println("Parsing " + xmlFile.getAbsolutePath() + "...");

        //noinspection unchecked
        LDAPConfiguration configuration = parser.parse(xmlFile);

        assertTrue("Failed to parse the authorization configuration!", configuration != null);
    }

    @Test
    public void testStoreAuthenticationConfigurationLDAP()
            throws IOException, JAXBException
    {
        LDAPConfiguration configuration = new LDAPConfiguration();
        configuration.setHost("localhost");
        configuration.setProtocol("ldaps");
        configuration.setPort(40636);
        configuration.setUsername("admin");
        configuration.setPassword("password");
        configuration.setRootDn("dc=carlspring,dc=com");
        configuration.setTimeout(60000);

        File outputFile = new File(XML_OUTPUT_FILE).getAbsoluteFile();

        System.out.println("Storing " + outputFile.getAbsolutePath() + "...");

        parser.store(configuration, outputFile.getCanonicalPath());

        assertTrue("Failed to store the produced XML!", outputFile.length() > 0);

        //noinspection ResultOfMethodCallIgnored
        outputFile.delete();
    }

}
