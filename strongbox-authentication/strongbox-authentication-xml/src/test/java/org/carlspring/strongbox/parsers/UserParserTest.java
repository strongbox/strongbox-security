package org.carlspring.strongbox.parsers;

import org.carlspring.strongbox.jaas.Credentials;
import org.carlspring.strongbox.jaas.Role;
import org.carlspring.strongbox.jaas.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author mtodorov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/strongbox-*-context.xml"})
public class UserParserTest
{

    public static final String TEST_CLASSES = "target/test-classes";

    public static final String CONFIGURATION_BASEDIR = TEST_CLASSES + "/etc/conf";

    public static final String XML_FILE = CONFIGURATION_BASEDIR + "/security-users.xml";

    public static final String XML_OUTPUT_FILE = CONFIGURATION_BASEDIR + "/security-users-saved.xml";


    @Test
    public void testParseConfiguration()
            throws IOException
    {
        File xmlFile = new File(XML_FILE);

        UserParser parser = new UserParser();
        final XStream xstream = parser.getXStreamInstance();

        //noinspection unchecked
        final List<User> users = (List<User>) xstream.fromXML(xmlFile);

        assertTrue("Failed to parse any users!", users != null);
        assertTrue("Failed to parse any users!", users.isEmpty());
    }

    @Test
    public void testStoreConfiguration()
            throws IOException
    {
        List<User> users = new ArrayList<User>();
        users.add(createUser("admin", "password", "admin"));
        users.add(createUser("user", "password", "view", "read"));
        users.add(createUser("deployer", "password", "deploy", "read", "delete"));

        File outputFile = new File(XML_OUTPUT_FILE);

        UserParser parser = new UserParser();
        parser.store(users, outputFile.getCanonicalPath());

        assertTrue("Failed to store the produced XML!", outputFile.length() > 0);
    }

    private User createUser(String username, String password, String... roles)
    {
        User user = new User(username, new Credentials(password));

        if (roles != null)
        {
            Set<Role> userRoles = new LinkedHashSet<Role>();
            for (String r : roles)
            {
                Role role = new Role();
                role.setName(r);
                userRoles.add(role);
            }

            user.setRoles(userRoles);
        }

        return user;
    }

}
