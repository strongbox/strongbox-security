package org.carlspring.strongbox.xml.parsers;

import org.carlspring.strongbox.configuration.LDAPConfiguration;

import java.io.File;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import org.junit.Test;
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


    @Test
    public void testParseAuthenticationConfigurationLDAP()
            throws IOException
    {
        File xmlFile = new File(XML_FILE);

        System.out.println("Parsing " + xmlFile.getAbsolutePath() + "...");

        LDAPConfigurationParser parser = new LDAPConfigurationParser();
        final XStream xstream = parser.getXStreamInstance();

        //noinspection unchecked
        LDAPConfiguration configuration = (LDAPConfiguration) xstream.fromXML(xmlFile);

        assertTrue("Failed to parse the authorization configuration!", configuration != null);
    }

    @Test
    public void testStoreAuthenticationConfigurationLDAP()
            throws IOException
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

        LDAPConfigurationParser parser = new LDAPConfigurationParser();
        parser.store(configuration, outputFile.getCanonicalPath());

        assertTrue("Failed to store the produced XML!", outputFile.length() > 0);
    }

}
