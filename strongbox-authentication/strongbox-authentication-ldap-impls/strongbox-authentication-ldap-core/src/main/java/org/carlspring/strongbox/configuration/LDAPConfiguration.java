package org.carlspring.strongbox.configuration;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author mtodorov
 */
public class LDAPConfiguration implements Serializable
{

    @XStreamAlias(value = "host")
    private String host;

    @XStreamAlias(value = "port")
    private int port;

    @XStreamAlias(value = "protocol")
    private String protocol;

    @XStreamAlias(value = "username")
    private String username;

    @XStreamAlias(value = "password")
    private String password;

    @XStreamAlias(value = "rootDn")
    private String rootDn;

    /**
     * The connection time limit. If set to 0, will wait indefinitely,
     * until the results are retrieved.
     */
    @XStreamAlias(value = "timeout")
    private int timeout = 30000;

    @XStreamAlias(value = "domain")
    private String domain;

    /**
     * The supported login modes are:
     * - anonymous bind (anonymous)
     * - username/password (username)
     * - principal/password (principal)
     */
    @XStreamAlias(value = "loginMode")
    private String loginMode;

    @XStreamAlias(value = "attribute-mappings")
    private AttributeMappings attributeMappings;


    public LDAPConfiguration()
    {
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

    public String getRootDn()
    {
        return rootDn;
    }

    public void setRootDn(String rootDn)
    {
        this.rootDn = rootDn;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

    public boolean shouldBindAnonymously()
    {
        return loginMode == null || loginMode.equalsIgnoreCase("anonymous");
    }

    public boolean shouldBindWithUsernameAndPassword()
    {
        return loginMode != null && loginMode.equalsIgnoreCase("username");
    }

    public boolean shouldBindWithPrincipal()
    {
        return loginMode != null && loginMode.equalsIgnoreCase("principal") && domain != null;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public String getLoginMode()
    {
        return loginMode;
    }

    public void setLoginMode(String loginMode)
    {
        this.loginMode = loginMode;
    }

    public AttributeMappings getAttributeMappings()
    {
        return attributeMappings;
    }

    public void setAttributeMappings(AttributeMappings attributeMappings)
    {
        this.attributeMappings = attributeMappings;
    }

}
