package org.carlspring.strongbox.configuration;

import javax.xml.bind.annotation.*;

/**
 * @author mtodorov
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupMapping
{

    /**
     * The group's ID attribute. (Usually something like cn).
     */
    @XmlElement(name = "groupId")
    private String groupId;

    /**
     * The group's member attribute. (Usually something like uniqueMember).
     */
    @XmlElement(name = "groupMember")
    private String groupMember;

    @XmlElement(name = "query")
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
