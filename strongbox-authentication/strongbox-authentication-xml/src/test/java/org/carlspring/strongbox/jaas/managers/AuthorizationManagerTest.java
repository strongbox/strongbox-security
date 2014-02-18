package org.carlspring.strongbox.jaas.managers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * @author mtodorov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/strongbox-*-context.xml", "classpath*:/META-INF/spring/strongbox-*-context.xml"})
public class AuthorizationManagerTest
{

    @Autowired
    private AuthorizationManager authorizationManager;


    @Test
    public void testLoad()
            throws Exception
    {
        assertTrue("Failed to load privileges!", authorizationManager.getPrivilegeManager().getPrivileges().size() > 0);
        assertTrue("Failed to load roles!", authorizationManager.getRoleManager().getRoles().size() > 0);
        assertTrue("Failed to load users!", authorizationManager.getUserManager().getUsers().size() > 0);

        System.out.println("Privileges: " + authorizationManager.getPrivilegeManager().getPrivileges().size());
        System.out.println("Roles:      " + authorizationManager.getRoleManager().getRoles().size());
        System.out.println("Users:      " + authorizationManager.getUserManager().getUsers().size());
    }

}
