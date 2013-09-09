package org.carlspring.strongbox.jaas;

import org.carlspring.strongbox.dao.ldap.impl.UsersDaoImpl;
import org.carlspring.strongbox.jaas.authentication.AuthenticationCallbackHandler;

import javax.naming.AuthenticationException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * @author mtodorov
 */
public class LDAPLoginModuleACNTest
{

    private static final String VALID_DN_STRING_1 = "cn=Strong Box,ou=people,dc=carlspring,dc=org";

    private static final String VALID_DN_STRING_2 = "cn=Steve Todorov,ou=people,dc=carlspring,dc=org";

    private static final String VALID_DN_STRING_3 = "cn=Martin Todorov,ou=people,dc=carlspring,dc=org";

    private static final String INVALID_DN_STRING_1 = "cn=Strong Box1,ou=people,dc=carlspring,dc=org";

    private static final String USER_PASSWORD_VALID = "password";

    @Test
    public void testValidLogin()
            throws LoginException
    {
        System.out.println("\n\nTesting login with valid credentials...\n");

        UsersDaoImpl user = new UsersDaoImpl();
        try {
            User ldapuser1 = user.findUser(VALID_DN_STRING_1, USER_PASSWORD_VALID);
            User ldapuser2 = user.findUser(VALID_DN_STRING_2, USER_PASSWORD_VALID);
            User ldapuser3 = user.findUser(VALID_DN_STRING_3, USER_PASSWORD_VALID);

            assertEquals("Invalid LDAP user was retrieved!", "strongbox",  ldapuser1.getUsername());
            assertEquals("Invalid LDAP user was retrieved!", "stevetodorov", ldapuser2.getUsername());
            assertEquals("Invalid LDAP user was retrieved!", "martintodorov", ldapuser3.getUsername());
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testCaching()
//            throws LoginException
//    {
//        /*
//        System.out.println("Testing login with cached credentials...");
//
//        LoginContext lc = new LoginContext("strongbox", new AuthenticationCallbackHandler(USERNAME_VALID, PASSWORD_VALID));
//        lc.login();
//
//        lc = new LoginContext("strongbox", new AuthenticationCallbackHandler("testuser", PASSWORD_VALID));
//        lc.login();
//
//        // Try again
//        lc = new LoginContext("strongbox", new AuthenticationCallbackHandler(USERNAME_VALID, PASSWORD_VALID));
//        lc.login();
//
//        lc = new LoginContext("strongbox", new AuthenticationCallbackHandler("testuser", PASSWORD_VALID));
//        lc.login();
//
//        // if we return with no exception, authentication succeeded
//        */
//    }
//
    @Test
    public void testInvalidLogin()
            throws LoginException
    {
        System.out.println("\n\nTesting login with invalid credentials...");

        // attempt authentication
        UsersDaoImpl user = new UsersDaoImpl();
        try {
            User ldapuser1 = user.findUser(INVALID_DN_STRING_1, USER_PASSWORD_VALID);
            fail("The test should have failed when using invalid credentials!");
        }
        catch (AuthenticationException e) {
            //
        }
    }

}


