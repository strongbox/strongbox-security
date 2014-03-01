package org.carlspring.strongbox.xml.managers;

import org.carlspring.strongbox.configuration.LDAPConfiguration;
import org.carlspring.strongbox.resource.ConfigurationResourceResolver;
import org.carlspring.strongbox.xml.parsers.LDAPConfigurationParser;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public class LDAPConfigurationManager
{

    private static final Logger logger = LoggerFactory.getLogger(LDAPConfigurationManager.class);

    private LDAPConfiguration configuration;

    @Autowired
    private ConfigurationResourceResolver configurationResourceResolver;

    @Autowired
    private LDAPConfigurationParser ldapConfigurationParser;


    public LDAPConfigurationManager()
    {
    }

    public void load()
            throws IOException
    {
        Resource resource = configurationResourceResolver.getConfigurationResource("etc/conf/security-.xml",
                                                                                   "security.users.xml",
                                                                                   "etc/conf/security-users.xml");

        logger.info("Loading Strongbox configuration from " + resource.toString() + "...");

        configuration = ldapConfigurationParser.parse(resource.getInputStream());
    }

    public LDAPConfiguration getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(LDAPConfiguration configuration)
    {
        this.configuration = configuration;
    }

    public LDAPConfigurationParser getLdapConfigurationParser()
    {
        return ldapConfigurationParser;
    }

    public void setLdapConfigurationParser(LDAPConfigurationParser ldapConfigurationParser)
    {
        this.ldapConfigurationParser = ldapConfigurationParser;
    }

    public ConfigurationResourceResolver getConfigurationResourceResolver()
    {
        return configurationResourceResolver;
    }

    public void setConfigurationResourceResolver(ConfigurationResourceResolver configurationResourceResolver)
    {
        this.configurationResourceResolver = configurationResourceResolver;
    }

}
