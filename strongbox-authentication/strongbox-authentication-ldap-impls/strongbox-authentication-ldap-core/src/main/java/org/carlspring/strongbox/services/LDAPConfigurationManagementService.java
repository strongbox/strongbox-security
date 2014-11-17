package org.carlspring.strongbox.services;

import org.carlspring.strongbox.configuration.LDAPConfiguration;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * @author mtodorov
 */
public interface LDAPConfigurationManagementService
{

    void setConfiguration(LDAPConfiguration ldapConfiguration)
            throws IOException, JAXBException;

    LDAPConfiguration getConfiguration();

}
