package org.carlspring.strongbox.security.jaas;

import org.carlspring.strongbox.security.jaas.rdbms.DatabaseCallbackHandler;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.junit.Test;
import static junit.framework.Assert.fail;

/**
 * @author mtodorov
 */
public class DatabaseLoginModuleACNTest
{

    private static final String USERNAME_VALID = "admin";

    private static final String PASSWORD_VALID = "password";

    private static final String USERNAME_INVALID = "admin1";

    private static final String PASSWORD_INVALID = "password123";


    @Test
    public void testValidLogin()
            throws LoginException
    {
        System.out.println("Testing login with valid credentials...");

        LoginContext lc = new LoginContext("strongbox", new DatabaseCallbackHandler(USERNAME_VALID, PASSWORD_VALID));

        // attempt authentication
        lc.login();

        // if we return with no exception, authentication succeeded
    }

    @Test
    public void testInvalidLogin()
            throws LoginException
    {
        System.out.println("Testing login with invalid credentials...");

        LoginContext lc = new LoginContext("strongbox", new DatabaseCallbackHandler(USERNAME_INVALID, PASSWORD_INVALID));

        // attempt authentication
        try
        {
            lc.login();

            // if we return with no exception, authentication succeeded

            fail("The test should have failed when using invalid credentials!");
        }
        catch (LoginException e)
        {
            // This is expected to happen.
        }
    }

}


