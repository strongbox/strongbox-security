package org.carlspring.strongbox.security.jaas;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steve Todorov
 */
public class LDAPGroup implements Group
{

    private String name;

    private String description;

    private String groupDN;

    private LDAPGroup parent = null;

    private List<String> members = new ArrayList<String>();


    public LDAPGroup()
    {
    }

    @Override
    public LDAPGroup getParent()
    {
        return parent;
    }

    public void setParent(LDAPGroup parent)
    {
        this.parent = parent;
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

    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
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
