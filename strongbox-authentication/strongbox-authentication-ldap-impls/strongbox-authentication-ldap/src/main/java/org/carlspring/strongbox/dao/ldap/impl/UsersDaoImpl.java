package org.carlspring.strongbox.dao.ldap.impl;

import org.carlspring.ioc.InjectionException;
import org.carlspring.ioc.PropertiesResources;
import org.carlspring.ioc.PropertyValue;
import org.carlspring.ioc.PropertyValueInjector;
import org.carlspring.strongbox.dao.ldap.UsersDao;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.resource.ResourceCloser;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mtodorov
 */
@PropertiesResources(resources = { "META-INF/properties/ldap.properties" })
public class UsersDaoImpl
        implements UsersDao
{

    private static final Logger logger = LoggerFactory.getLogger(UsersDaoImpl.class);

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
    public User findUser(String username)
            throws UserResolutionException
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
            throws UserResolutionException
    {
        User user = null;
        DirContext ctx = null;
        NamingEnumeration results = null;

        // logger.debug("cn:/ " + cn);

        try
        {
            // Step 1: Bind anonymously
            ctx = getContext();

            // Step 2: Search the directory
            // TODO: Make this configurable:

            // String filter = "(&(objectClass=user)(uid={0}))";
            String filter = "(&(objectClass=inetOrgPerson)(uid={0})(userPassword={1}))";

            String[] attrIDs = new String[]{ "ou",
                                             "uid",       // username
                                             "cn",        // common name (full name)
                                             "givenName", // first name
                                             "sn",        // last name
                                             "mail" };

            SearchControls controls = getSearchControls(attrIDs);
            controls.setReturningAttributes(attrIDs);
            controls.setTimeLimit(timeout);

            results = ctx.search(rootDn, filter, new String[]{ uid, password }, controls);

            // String uid = null;
            String firstName = null;
            String lastName = null;
            String email = null;

            Attributes attributes = null;
            if (results.hasMore())
            {
                SearchResult result = (SearchResult) results.next();
                rootDn = result.getNameInNamespace();

                logger.debug("dn: " + rootDn);

                attributes = result.getAttributes();

                Attribute attrUId = attributes.get("uid");
                Attribute attrFirstName = attributes.get("givenName");
                Attribute attrLastName = attributes.get("sn");
                Attribute attrEmail = attributes.get("mail");

                uid = (String) attrUId.get();
                // Null-proof these:
                firstName = attrFirstName != null ? (String) attrFirstName.get() : null;
                lastName = attrLastName != null ? (String) attrLastName.get() : null;
                email = attrEmail != null ? (String) attrEmail.get() : null;

                logger.debug(" * uid:            " + attrUId.get());
                logger.debug(" * firstName:      " + firstName);
                logger.debug(" * lastName:       " + lastName);
                logger.debug(" * email :         " + email);
                logger.debug("\n");

                user = new User();
                user.setUsername(uid);
                // user.setCredentials(new Credentials(password));

            }

            // if (dn == null || results.hasMore())
            // {
            //     // uid not found or not unique
            //     throw new NamingException("Authentication failed.");
            // }

            // Step 3: Bind with found DN and given password
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, rootDn);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);

            // Perform a lookup in order to force a bind operation with JNDI
            ctx.lookup(rootDn);

            logger.debug("Authentication successful for user " + user + ".");
        }
        catch (NamingException e)
        {
            throw new UserResolutionException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(ctx, logger);
            ResourceCloser.close(results, logger);
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
