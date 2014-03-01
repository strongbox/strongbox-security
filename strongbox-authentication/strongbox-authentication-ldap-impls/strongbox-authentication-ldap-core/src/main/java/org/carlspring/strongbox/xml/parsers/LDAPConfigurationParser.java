package org.carlspring.strongbox.xml.parsers;

import org.carlspring.strongbox.configuration.AuthenticationConfiguration;
import org.carlspring.strongbox.configuration.LDAPConfiguration;

import com.thoughtworks.xstream.XStream;

/**
 * @author mtodorov
 */
public class LDAPConfigurationParser
        extends GenericParser<LDAPConfiguration>
{

    public XStream getXStreamInstance()
    {
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        xstream.alias("ldap-configuration", LDAPConfiguration.class);

        return xstream;
    }

}
