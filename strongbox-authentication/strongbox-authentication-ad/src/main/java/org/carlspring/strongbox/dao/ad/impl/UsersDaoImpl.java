package org.carlspring.strongbox.dao.ad.impl;

import org.carlspring.ioc.InjectionException;
import org.carlspring.ioc.PropertiesResources;
import org.carlspring.ioc.PropertyValue;
import org.carlspring.ioc.PropertyValueInjector;
import org.carlspring.strongbox.dao.ad.UsersDao;
import org.carlspring.strongbox.jaas.User;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

import org.apache.log4j.Logger;

/**
 * @author mtodorov
 */
@PropertiesResources(resources = { "META-INF/properties/active-directory.properties" })
public class UsersDaoImpl
        implements UsersDao
{

    private static final Logger logger = Logger.getLogger(UsersDaoImpl.class);

    @PropertyValue(key = "ldap.host")
    private String host;

    @PropertyValue(key = "ldap.port")
    private int port;

    @PropertyValue(key = "ldap.protocol")
    private String protocol;

    @PropertyValue(key = "ldap.username")
    private String username;

    @PropertyValue(key = "ldap.password")
    private String password;

    @PropertyValue(key = "ldap.root.dn")
    private String rootDn;

    @PropertyValue(key = "ldap.timeout")
    private int timeout;


    public UsersDaoImpl()
    {
    }

    @Override
    public void initialize()
            throws InjectionException
    {
        PropertyValueInjector.inject(this);
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

    private static String[] userAttributes = { "distinguishedName",
                                               "cn",
                                               "name",
                                               "uid",
                                               "sn",
                                               "givenName",
                                               "memberOf",
                                               "sAMAccountName",
                                               "userPrincipalName" };

    /**
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public User findUser(String username,
                         String password)
            throws Exception
    {
        User user = null;

        // User user = null;
        DirContext ctx = null;
        NamingEnumeration results = null;

        try
        {
            // Step 1: Bind anonymously
            ctx = getContext();

            // Step 2: Search the directory
            // TODO: Make this configurable:
            String filter = "(&(objectClass=user)(sAMAccountName={0}))";

            String[] attrIDs = new String[]{ "sAMAccountName", // uid
                                             "cn",
                                             "givenName",      // first name
                                             "sn",             // last name
                                             "mail" };

            SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            ctls.setReturningAttributes(attrIDs);
            ctls.setReturningObjFlag(true);
            ctls.setTimeLimit(timeout);

            results = ctx.search(rootDn, filter, new String[]{ username }, ctls);

            if (results.hasMore())
            {
                SearchResult result = (SearchResult) results.next();
                rootDn = result.getNameInNamespace();

                logger.debug("Getting user " + username + " (dn: " + rootDn + ")...");

                Attribute attrUid = result.getAttributes().get("sAMAccountName");
                Attribute attrFirstName = result.getAttributes().get("givenName");
                Attribute attrLastName = result.getAttributes().get("sn");
                Attribute attrEmail = result.getAttributes().get("mail");

                // TODO: Make this less verbose
                logger.debug(" * sAMAccountName : " + attrUid.get());
                logger.debug(" * givenName      : " + attrFirstName.get());
                logger.debug(" * sn             : " + attrLastName.get());
                logger.debug(" * mail           : " + attrEmail.get());
                logger.debug("\n");

                user = new User();
                user.setUsername((String) attrUid.get());
                user.setFirstName((String) attrFirstName.get());
                user.setLastName((String) attrLastName.get());
                user.setEmail((String) attrEmail.get());
                user.setPassword(password);
            }

            if (rootDn == null || results.hasMore())
            {
                // uid not found or not unique
                throw new NamingException("Authentication failed for user " + user + ".");
            }

            // Step 3: Bind with found DN and given password
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, rootDn);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);

            // Perform a lookup in order to force a bind operation with JNDI
            ctx.lookup(rootDn);

            logger.debug("Authentication successful for user " + user + ".");
        }
        finally
        {
            if (ctx != null)
            {
                ctx.close();
            }

            if (results != null)
            {
                results.close();
            }
        }

        return user;
    }

    private InitialDirContext getContext()
            throws NamingException
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, protocol + "://" + host + ":" + port + "/");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, password);

        if (protocol.equalsIgnoreCase("ldaps"))
        {
            env.put(Context.SECURITY_PROTOCOL, "ssl");
        }

        return new InitialDirContext(env);
    }

    /**
     * NOTE: Active Directory does not support anonymous logins by default.
     *
     * @return
     * @throws NamingException
     */
    private InitialDirContext getContextAnonymously()
            throws NamingException
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, protocol + "://" + host + ":" + port + "/");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        if (protocol.equalsIgnoreCase("ldaps"))
        {
            env.put(Context.SECURITY_PROTOCOL, "ssl");
        }

        return new InitialDirContext(env);
    }

    private SearchControls getSearchControls(String[] attrIDs)
    {
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(attrIDs);
        controls.setReturningObjFlag(true);

        return controls;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

}
