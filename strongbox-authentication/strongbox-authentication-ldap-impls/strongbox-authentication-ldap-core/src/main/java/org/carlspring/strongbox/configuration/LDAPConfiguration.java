package org.carlspring.strongbox.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mtodorov
 */
@XmlRootElement(name = "ldap-configuration")
@XmlAccessorType(XmlAccessType.FIELD)
public class LDAPConfiguration extends GenericLDAPConfiguration
{


    public LDAPConfiguration()
    {
        super();
    }

}
