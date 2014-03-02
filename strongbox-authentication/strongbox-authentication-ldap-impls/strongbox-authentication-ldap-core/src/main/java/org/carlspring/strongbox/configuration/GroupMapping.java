package org.carlspring.strongbox.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author mtodorov
 */
public class GroupMapping
{

    /**
     * The group's ID attribute. (Usually something like cn).
     */
    @XStreamAlias(value = "groupId")
    private String groupId;

    /**
     * The group's member attribute. (Usually something like uniqueMember).
     */
    @XStreamAlias(value = "groupMember")
    private String groupMember;

    @XStreamAlias(value = "query")
    private QueryDetails query;


    public GroupMapping()
    {
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupMember()
    {
        return groupMember;
    }

    public void setGroupMember(String groupMember)
    {
        this.groupMember = groupMember;
    }

    public QueryDetails getQuery()
    {
        return query;
    }

    public void setQuery(QueryDetails query)
    {
        this.query = query;
    }

}
