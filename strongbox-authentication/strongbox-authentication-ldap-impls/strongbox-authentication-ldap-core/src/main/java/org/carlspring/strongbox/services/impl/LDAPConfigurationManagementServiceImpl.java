package org.carlspring.strongbox.services.impl;

import org.carlspring.strongbox.configuration.LDAPConfiguration;
import org.carlspring.strongbox.configuration.LDAPConfigurationManager;
import org.carlspring.strongbox.services.LDAPConfigurationManagementService;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public class LDAPConfigurationManagementServiceImpl implements LDAPConfigurationManagementService
{

    @Autowired
    private LDAPConfigurationManager ldapConfigurationManager;


    @Override
    public void setConfiguration(LDAPConfiguration ldapConfiguration)
            throws IOException, JAXBException
    {
        //noinspection unchecked
        ldapConfigurationManager.setConfiguration(ldapConfiguration);
        ldapConfigurationManager.store();

    }

    @Override
    public LDAPConfiguration getConfiguration()
    {
        return ldapConfigurationManager.getConfiguration();
    }

}
