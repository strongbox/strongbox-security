package org.carlspring.strongbox.rest;

import org.carlspring.strongbox.configuration.LDAPConfiguration;
import org.carlspring.strongbox.services.GenericLDAPConfigurationManagementService;

import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
@Path("/configuration/ldap")
public class LDAPConfigurationManagementRestlet
        extends AbstractLDAPConfigurationManagementRestlet<LDAPConfiguration>
{

    private static final Logger logger = LoggerFactory.getLogger(LDAPConfigurationManagementRestlet.class);

    @Autowired
    private GenericLDAPConfigurationManagementService ldapConfigurationManagementService;


    public GenericLDAPConfigurationManagementService getConfigurationManagementService()
    {
        return ldapConfigurationManagementService;
    }

    @Override
    public Logger getLogger()
    {
        return logger;
    }

}
