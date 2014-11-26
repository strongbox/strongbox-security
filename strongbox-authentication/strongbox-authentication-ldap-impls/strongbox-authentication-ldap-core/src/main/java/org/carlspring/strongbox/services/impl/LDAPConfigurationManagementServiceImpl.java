package org.carlspring.strongbox.services.impl;

import org.carlspring.strongbox.configuration.LDAPConfigurationManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public class LDAPConfigurationManagementServiceImpl extends AbstractLDAPConfigurationManagementServiceImpl
{

    @Autowired
    private LDAPConfigurationManager ldapConfigurationManager;


    @Override
    public LDAPConfigurationManager getConfigurationManager()
    {
        return ldapConfigurationManager;
    }

}
