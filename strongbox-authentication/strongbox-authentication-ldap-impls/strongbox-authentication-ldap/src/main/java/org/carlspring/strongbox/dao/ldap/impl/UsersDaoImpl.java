package org.carlspring.strongbox.dao.ldap.impl;

import org.carlspring.strongbox.configuration.LDAPConfiguration;
import org.carlspring.strongbox.resource.ConfigurationResourceResolver;
import org.carlspring.strongbox.resource.ResourceCloser;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.xml.parsers.LDAPConfigurationParser;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;


/**
 * @author mtodorov
 */
public class UsersDaoImpl extends AbstractUsersDaoImpl
{

    private static final Logger logger = LoggerFactory.getLogger(UsersDaoImpl.class);

    @Autowired
    private ConfigurationResourceResolver configurationResourceResolver;

    @Autowired
    private LDAPConfigurationParser ldapConfigurationParser;

    private LDAPConfiguration ldapConfiguration;


    public UsersDaoImpl()
    {
    }

    @Override
    public User findUser(String username)
            throws UserResolutionException
    {
        return null;
    }

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
            controls.setTimeLimit(getTimeout());

            results = ctx.search(getRootDn(), filter, new String[]{ uid, password }, controls);

            // String uid = null;
            String firstName = null;
            String lastName = null;
            String email = null;

            String rootDn = null;

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

    public void load()
            throws IOException
    {
        Resource resource = configurationResourceResolver.getConfigurationResource("etc/conf/security-authentication-ldap.xml",
                                                                                   "security.users.xml",
                                                                                   "etc/conf/security-authentication-ldap.xml");

        logger.info("Loading Strongbox configuration from " + resource.toString() + "...");

        ldapConfiguration = ldapConfigurationParser.parse(resource.getInputStream());
    }

    public LDAPConfiguration getLdapConfiguration()
    {
        return ldapConfiguration;
    }

    public void setLdapConfiguration(LDAPConfiguration ldapConfiguration)
    {
        this.ldapConfiguration = ldapConfiguration;
    }
}
