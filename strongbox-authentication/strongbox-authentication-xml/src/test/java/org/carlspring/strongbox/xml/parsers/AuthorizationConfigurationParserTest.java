package org.carlspring.strongbox.xml.parsers;

import org.carlspring.strongbox.configuration.AuthorizationConfiguration;
import org.carlspring.strongbox.security.jaas.Privilege;
import org.carlspring.strongbox.security.jaas.Role;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author mtodorov
 */
public class AuthorizationConfigurationParserTest
{

    public static final String TEST_CLASSES = "target/test-classes";

    public static final String CONFIGURATION_BASEDIR = TEST_CLASSES + "/etc/conf";

    public static final String XML_FILE = CONFIGURATION_BASEDIR + "/security-authorization.xml";

    public static final String XML_OUTPUT_FILE = CONFIGURATION_BASEDIR + "/security-authorization-saved.xml";

    private GenericParser<AuthorizationConfiguration> parser = new GenericParser<AuthorizationConfiguration>(AuthorizationConfiguration.class);


    @Test
    public void testParseAuthorizationConfiguration()
            throws IOException, JAXBException
    {
        File xmlFile = new File(XML_FILE);

        System.out.println("Parsing " + xmlFile.getAbsolutePath() + "...");

        //noinspection unchecked
        AuthorizationConfiguration configuration = parser.parse(xmlFile);

        assertTrue("Failed to parse the authorization configuration!", configuration != null);
        assertFalse("Failed to parse any roles!", configuration.getRoles() == null);
        assertFalse("Failed to parse any roles!", configuration.getRoles().isEmpty());
        assertFalse("Failed to parse any privileges!", configuration.getPrivileges() == null);
        assertFalse("Failed to parse any privileges!", configuration.getPrivileges().isEmpty());
    }

    @Test
    public void testStoreAuthorizationConfiguration()
            throws IOException, JAXBException
    {
        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role("admin", "Admin role"));
        roles.add(new Role("deployer", "Deployment role"));

        Role customRole = new Role("custom1", "Custom role");
        customRole.addRole("admin");
        customRole.addRole("deployer");
        customRole.addPrivilege("read");

        roles.add(customRole);

        List<Privilege> privileges = new ArrayList<Privilege>();
        privileges.add(new Privilege("read", "Grants read privilege"));
        privileges.add(new Privilege("deploy", "Grants deployment privilege"));

        AuthorizationConfiguration configuration = new AuthorizationConfiguration();
        configuration.setRoles(roles);
        configuration.setPrivileges(privileges);

        File outputFile = new File(XML_OUTPUT_FILE).getAbsoluteFile();

        System.out.println("Storing " + outputFile.getAbsolutePath() + "...");

        parser.store(configuration, outputFile.getCanonicalPath());

        assertTrue("Failed to store the produced XML!", outputFile.length() > 0);
    }

}
