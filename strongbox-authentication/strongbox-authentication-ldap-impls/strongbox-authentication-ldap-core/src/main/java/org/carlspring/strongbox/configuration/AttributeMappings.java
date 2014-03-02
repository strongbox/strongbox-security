package org.carlspring.strongbox.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author mtodorov
 */
public class AttributeMappings
{

    @XStreamAlias(value = "user-mapping")
    private UserMapping userMapping;

    @XStreamAlias(value = "group-mapping")
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
