package org.carlspring.strongbox.xml.parsers;

import org.carlspring.strongbox.security.jaas.Credentials;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.Users;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.*;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author mtodorov
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/META-INF/spring/strongbox-*-context.xml"})
public class UserParserTest
{

    public static final String TEST_CLASSES = "target/test-classes";

    public static final String CONFIGURATION_BASEDIR = TEST_CLASSES + "/etc/conf";

    public static final String XML_FILE = CONFIGURATION_BASEDIR + "/security-users.xml";

    public static final String XML_OUTPUT_FILE = CONFIGURATION_BASEDIR + "/security-users-saved.xml";

    private GenericParser<Users> parser = new GenericParser<>(Users.class);

    @Test
    public void testStoreUsers()
            throws IOException, JAXBException
    {
        Set<User> users = new LinkedHashSet<User>();
        users.add(createUser("admin", "password", "admin"));
        users.add(createUser("user", "password", "view", "read"));
        users.add(createUser("deployer", "password", "deploy", "read", "delete"));

        File outputFile = new File(XML_OUTPUT_FILE).getAbsoluteFile();

        System.out.println("Storing " + outputFile.getAbsolutePath() + "...");

        parser.store(new Users(users), outputFile.getCanonicalPath());

        assertTrue("Failed to store the produced XML!", outputFile.length() > 0);
    }

    @Test
    public void testParseUsers()
            throws IOException, JAXBException
    {
        File xmlFile = new File(XML_FILE);

        System.out.println("Parsing " + xmlFile.getAbsolutePath() + "...");

        //noinspection unchecked
        final Users users = parser.parse(xmlFile);

        assertTrue("Failed to parse any users!", users != null);
        assertFalse("Failed to parse any users!", users.getUsers().isEmpty());
    }

    private User createUser(String username, String password, String... roles)
    {
        User user = new User(username, new Credentials(password));

        if (roles != null)
        {
            List<String> userRoles = new ArrayList<String>();
            Collections.addAll(userRoles, roles);

            user.setRoles(userRoles);
        }

        return user;
    }

}
