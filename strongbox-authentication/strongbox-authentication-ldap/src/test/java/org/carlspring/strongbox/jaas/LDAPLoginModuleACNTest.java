package org.carlspring.strongbox.jaas;

import org.carlspring.strongbox.jaas.authentication.AuthenticationCallbackHandler;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static junit.framework.Assert.fail;

/**
 * @author mtodorov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/strongbox-*-context.xml", "classpath*:/META-INF/spring/strongbox-*-context.xml"})
public class LDAPLoginModuleACNTest
{

    private static final String VALID_USER = "mtodorov";

    private static final String INVALID_USER = "strongboxx";

    private static final String PASSWORD_VALID = "password";

    private static final String INVALID_PASSWORD = "password123";


    @Test
    public void testValidLogin()
            throws LoginException
    {
        System.out.println("Testing login with valid credentials...");

        // if we return with no exception, authentication succeeded
        LoginContext lc = new LoginContext("strongbox", new AuthenticationCallbackHandler(VALID_USER, PASSWORD_VALID));
        lc.login();
    }

    /*
    // TODO: Re-visit this.
    @Test
    public void testCaching()
            throws LoginException
    {
        System.out.println("Testing login with cached credentials...");

        LoginContext lc = new LoginContext("strongbox", new AuthenticationCallbackHandler(VALID_USER_1, PASSWORD_VALID));
        lc.login();

        lc = new LoginContext("strongbox", new AuthenticationCallbackHandler(VALID_USER_2, PASSWORD_VALID));
        lc.login();

        // Try again
        lc = new LoginContext("strongbox", new AuthenticationCallbackHandler(VALID_USER_3, PASSWORD_VALID));
        lc.login();

        lc = new LoginContext("strongbox", new AuthenticationCallbackHandler(VALID_USER_1, PASSWORD_VALID));
        lc.login();

        // if we return with no exception, authentication succeeded
    }
    */

    @Test
    public void testInvalidLogin()
            throws LoginException
    {
        System.out.println("Testing login with invalid credentials...");

        LoginContext lc;

        try
        {
            lc = new LoginContext("strongbox", new AuthenticationCallbackHandler(INVALID_USER, PASSWORD_VALID));
            lc.login();

            fail("Logged in user with invalid login details!");
        }
        catch (LoginException e)
        {
            System.out.println("Aborted as expected.");
        }

        try
        {
            lc = new LoginContext("strongbox", new AuthenticationCallbackHandler(VALID_USER, INVALID_PASSWORD));
            lc.login();

            fail("Logged in user with invalid login details!");
        }
        catch (LoginException e)
        {
            System.out.println("Aborted as expected.");
        }
    }

}


