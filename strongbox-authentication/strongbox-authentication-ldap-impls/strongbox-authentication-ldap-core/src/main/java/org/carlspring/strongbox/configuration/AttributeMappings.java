package org.carlspring.strongbox.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mtodorov
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeMappings
{

    @XmlElement(name = "user-mapping")
    private UserMapping userMapping;

    @XmlElement(name = "group-mapping")
    private GroupMapping groupMapping;


    public AttributeMappings()
    {
    }

    public UserMapping getUserMapping()
    {
        return userMapping;
    }

    public void setUserMapping(UserMapping userMapping)
    {
        this.userMapping = userMapping;
    }

    public GroupMapping getGroupMapping()
    {
        return groupMapping;
    }

    public void setGroupMapping(GroupMapping groupMapping)
    {
        this.groupMapping = groupMapping;
    }

}
