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
    public InitialDirContext getContextAnonymously()
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

    public InitialDirContext getContextViaPrincipal(String username, String password)
            throws NamingException
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, getProtocol() + "://" + getHost() + ":" + getPort() + "/");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        // Check here for some explanations:
        // - https://weblogs.java.net/blog/kohsuke/archive/2008/06/more_active_dir.html
        // - http://docs.alfresco.com/4.2/index.jsp?topic=%2Fcom.alfresco.enterprise.doc%2Fconcepts%2Fauth-ldap-props.html
        String principal = username +"@" + getDomain();

        env.put(Context.SECURITY_PRINCIPAL, principal);
        env.put(Context.SECURITY_CREDENTIALS, password);

        if (getProtocol().equalsIgnoreCase("ldaps"))
        {
            env.put(Context.SECURITY_PROTOCOL, "ssl");
        }

        return new InitialDirContext(env);
    }

    public InitialDirContext getContext(String username, String password)
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

    public InitialDirContext getContext()
            throws NamingException
    {
        if (shouldBindWithUsernameAndPassword())
        {
            return getContext(getUsername(), getPassword());
        }
        else if (shouldBindWithPrincipal())
        {
            return getContextViaPrincipal(getUsername(), getPassword());
        }
        else
        {
            return getContextAnonymously();
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

    public boolean shouldBindWithUsernameAndPassword()
    {
        return getLdapConfiguration().shouldBindWithUsernameAndPassword();
    }

    public boolean shouldBindWithPrincipal()
    {
        return getLdapConfiguration().shouldBindWithPrincipal();
    }

    public abstract String getDomain();

}
