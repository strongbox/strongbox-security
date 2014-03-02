package org.carlspring.strongbox.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author mtodorov
 */
public class UserMapping
{

    @XStreamAlias(value = "uid")
    private String uid;

    @XStreamAlias(value = "password")
    private String password;

    @XStreamAlias(value = "fullName")
    private String fullName;

    @XStreamAlias(value = "email")
    private String email;

    @XStreamAlias(value = "filter")
    private String filter;

    @XStreamAlias(value = "query")
    private QueryDetails query;


    public UserMapping()
    {
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFilter()
    {
        return filter;
    }

    public void setFilter(String filter)
    {
        this.filter = filter;
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
