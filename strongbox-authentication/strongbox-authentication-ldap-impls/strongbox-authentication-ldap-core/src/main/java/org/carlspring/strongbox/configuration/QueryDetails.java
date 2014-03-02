package org.carlspring.strongbox.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author mtodorov
 */
public class QueryDetails
{

    @XStreamAlias(value = "baseDn")
    private String baseDn;

    @XStreamAlias(value = "objectClass")
    private String objectClass;


    public QueryDetails()
    {
    }

    public String getBaseDn()
    {
        return baseDn;
    }

    public void setBaseDn(String baseDn)
    {
        this.baseDn = baseDn;
    }

    public String getObjectClass()
    {
        return objectClass;
    }

    public void setObjectClass(String objectClass)
    {
        this.objectClass = objectClass;
    }

}
