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
public class UserMapping
{

    @XmlElement(name = "uid")
    private String uid;

    @XmlElement(name = "password")
    private String password;

    @XmlElement(name = "full-name")
    private String fullName;

    @XmlElement(name = "email")
    private String email;

    @XmlElement(name = "filter")
    private String filter;

    @XmlElement(name = "query")
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
