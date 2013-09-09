package org.carlspring.strongbox.dao.ldap.impl;

import org.carlspring.strongbox.dao.ldap.UsersDao;
import org.carlspring.strongbox.jaas.User;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Hashtable;

/**
 * @author mtodorov
 */
public class UsersDaoImpl
        implements UsersDao
{


    public UsersDaoImpl()
    {
    }

    @Override
    public User findUser(long userId)
            throws Exception
    {
        return null;
    }

    @Override
    public User findUser(String username)
            throws Exception
    {
        return null;
    }

    @Override
    public User findUser(String username, String password)
            throws Exception
    {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389/");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        DirContext ctx = null;

        try
        {
            // Step 1: Bind anonymously
            ctx = new InitialDirContext(env);

            // cn=Strong Box,ou=people,dc=carlspring,dc=org
            // uid=carlspring,ou=users,ou=system

            // Step 2: Search the directory
            String base = "o=strongbox";
            String filter = "(&(objectClass=inetOrgPerson)(uid={0}))";

            SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            ctls.setReturningAttributes(new String[0]);
            ctls.setReturningObjFlag(true);

            NamingEnumeration enm = ctx.search(base, filter, new String[]{username}, ctls);

            String dn = null;

            if (enm.hasMore())
            {
                SearchResult result = (SearchResult) enm.next();
                dn = result.getNameInNamespace();

                System.out.println("dn: " + dn);
            }

            if (dn == null || enm.hasMore())
            {
                // uid not found or not unique
                throw new NamingException("Authentication failed!");
            }

            // Step 3: Bind with found DN and given password
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, dn);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            // Perform a lookup in order to force a bind operation with JNDI
            ctx.lookup(dn);

            System.out.println("Authentication successful!");

            enm.close();
        }
        catch (NamingException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            ctx.close();
        }

        return null;
    }

}
