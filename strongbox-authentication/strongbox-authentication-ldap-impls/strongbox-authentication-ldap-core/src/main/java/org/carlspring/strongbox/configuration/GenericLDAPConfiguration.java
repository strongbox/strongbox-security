package org.carlspring.strongbox.configuration;

import javax.xml.bind.annotation.*;

/**
 * @author mtodorov
 */
public class GenericLDAPConfiguration
        extends ServerConfiguration
{

    @XmlElement
    private String host;

    @XmlElement
    private int port;

    @XmlElement
    private String protocol;

    @XmlElement
    private String username;

    @XmlElement
    private String password;

    @XmlElement(name = "root-dn")
    private String rootDn;

    /**
     * The connection time limit. If set to 0, will wait indefinitely,
     * until the results are retrieved.
     */
    @XmlElement
    private int timeout = 30000;

    @XmlElement
    private String domain;

    /**
     * The supported login modes are:
     * - anonymous bind (anonymous)
     * - username/password (username)
     * - principal/password (principal)
     */
    @XmlElement(name = "login-mode")
    private String loginMode;

    @XmlElement(name = "attribute-mappings")
    private AttributeMappings attributeMappings;

    @XmlElement(name = "authentication-type")
    private String authenticationType;


    public GenericLDAPConfiguration()
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

    public String getAuthenticationType()
    {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType)
    {
        this.authenticationType = authenticationType;
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
