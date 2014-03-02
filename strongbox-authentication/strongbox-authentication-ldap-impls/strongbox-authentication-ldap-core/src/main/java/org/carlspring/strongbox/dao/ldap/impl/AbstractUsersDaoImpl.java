package org.carlspring.strongbox.dao.ldap.impl;

import org.carlspring.strongbox.configuration.LDAPConfiguration;
import org.carlspring.strongbox.dao.ldap.UsersDao;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public abstract class AbstractUsersDaoImpl
        implements UsersDao
{

    private static final Logger logger = LoggerFactory.getLogger(AbstractUsersDaoImpl.class);


    public AbstractUsersDaoImpl()
    {
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
        env.put(Context.PROVIDER_URL, getProtocol() + "://" + getHost() + ":" + getPort() + "/");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        if (getProtocol().equalsIgnoreCase("ldaps"))
        {
            env.put(Context.SECURITY_PROTOCOL, "ssl");
        }

        return new InitialDirContext(env);
    }

    private InitialDirContext getContext(String username, String password)
            throws NamingException
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, getProtocol() + "://" + getHost() + ":" + getPort() + "/");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, password);

        if (getProtocol().equalsIgnoreCase("ldaps"))
        {
            env.put(Context.SECURITY_PROTOCOL, "ssl");
        }

        return new InitialDirContext(env);
    }

    protected InitialDirContext getContext()
            throws NamingException
    {
        if (shouldBindAnonymously())
        {
            return getContextAnonymously();
        }
        else
        {
            return getContext(getUsername(), getPassword());
        }
    }

    protected SearchControls getSearchControls(String[] attrIDs)
    {
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(attrIDs);
        controls.setReturningObjFlag(true);

        return controls;
    }

    public String getHost()
    {
        return getLdapConfiguration().getHost();
    }

    public int getPort()
    {
        return getLdapConfiguration().getPort();
    }

    public String getProtocol()
    {
        return getLdapConfiguration().getProtocol();
    }

    public String getUsername()
    {
        return getLdapConfiguration().getUsername();
    }

    public String getPassword()
    {
        return getLdapConfiguration().getPassword();
    }

    public int getTimeout()
    {
        return getLdapConfiguration().getTimeout();
    }

    public String getRootDn()
    {
        return getLdapConfiguration().getRootDn();
    }

    public abstract LDAPConfiguration getLdapConfiguration();

    public abstract void setLdapConfiguration(LDAPConfiguration ldapConfiguration);

    public boolean shouldBindAnonymously()
    {
        return getLdapConfiguration().shouldBindAnonymously();
    }

}
