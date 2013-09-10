package org.carlspring.strongbox.dao.ldap.impl;

import org.carlspring.strongbox.dao.ldap.UsersDao;
import org.carlspring.strongbox.jaas.Credentials;
import org.carlspring.strongbox.jaas.User;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Hashtable;

import org.apache.log4j.Logger;

/**
 * @author mtodorov
 */
public class UsersDaoImpl
        implements UsersDao
{

    private static final Logger logger = Logger.getLogger(UsersDaoImpl.class);


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

    /**
     * @param uid
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public User findUser(String uid, String password)
            throws Exception
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // TODO: Make this configurable:
        env.put(Context.PROVIDER_URL, "ldap://carlspring.org:10389/");
        // TODO: Make this configurable:
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        User user = null;
        DirContext ctx = null;
        NamingEnumeration enm = null;

        try
        {
            // Step 1: Bind anonymously
            ctx = new InitialDirContext(env);

            // Step 2: Search the directory
            // TODO: Make this configurable:
            String base = "o=carlspringCorp";
            // TODO: Make this configurable:
            String filter = "(&(objectClass=inetOrgPerson)(uid={0}))";

            SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            ctls.setReturningAttributes(new String[0]);
            ctls.setReturningObjFlag(true);

            enm = ctx.search(base, filter, new String[]{uid}, ctls);

            String dn = null;

            if (enm.hasMore())
            {
                SearchResult result = (SearchResult) enm.next();
                dn = result.getNameInNamespace();

                logger.debug("dn: " + dn);
            }

            if (dn == null || enm.hasMore())
            {
                // uid not found or not unique
                throw new NamingException("Authentication failed.");
            }

            // Step 3: Bind with found DN and given password
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, dn);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);

            // Perform a lookup in order to force a bind operation with JNDI
            ctx.lookup(dn);

            user = new User();
            user.setUsername(uid);
            user.setCredentials(new Credentials(password));

            logger.debug("Authentication successful.");
        }
        finally
        {
            if (ctx != null)
            {
                ctx.close();
            }

            if (enm != null)
            {
                enm.close();
            }
        }

        return user;
    }

}
