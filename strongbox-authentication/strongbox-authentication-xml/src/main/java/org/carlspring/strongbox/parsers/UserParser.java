package org.carlspring.strongbox.parsers;

import org.carlspring.strongbox.configuration.Configuration;
import org.carlspring.strongbox.jaas.User;
import org.carlspring.strongbox.xml.parsers.GenericParser;

import java.util.List;

import com.thoughtworks.xstream.XStream;

/**
 * @author mtodorov
 */
public class UserParser
        extends GenericParser<Configuration>
{

    public XStream getXStreamInstance()
    {
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        xstream.alias("user", User.class);
        xstream.alias("users", List.class);

        return xstream;
    }

}
