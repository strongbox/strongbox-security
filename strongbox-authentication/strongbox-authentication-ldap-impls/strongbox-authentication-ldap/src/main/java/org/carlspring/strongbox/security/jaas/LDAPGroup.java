package org.carlspring.strongbox.security.jaas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steve Todorov
 */
public class LDAPGroup
{

    private String name;

    private String description;

    private String groupDN;

    private LDAPGroup parentGroup = null;

    private List<String> members = new ArrayList<String>();


    public LDAPGroup()
    {
    }

    public LDAPGroup getParentGroup()
    {
        return parentGroup;
    }

    public void setParentGroup(LDAPGroup parentGroup)
    {
        this.parentGroup = parentGroup;
    }

    public List<String> getMembers()
    {
        return members;
    }

    public void setMembers(List<String> members)
    {
        this.members = members;
    }

    public boolean addMember(String member)
    {
        return members.add(member);
    }

    public boolean removeMember(String member)
    {
        return members.remove(member);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getGroupDN()
    {
        return groupDN;
    }

    public void setGroupDN(String groupDN)
    {
        this.groupDN = groupDN;
    }

}
