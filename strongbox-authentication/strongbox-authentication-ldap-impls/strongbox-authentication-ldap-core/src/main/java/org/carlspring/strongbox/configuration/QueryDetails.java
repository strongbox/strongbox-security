package org.carlspring.strongbox.configuration;

import javax.xml.bind.annotation.*;

/**
 * @author mtodorov
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryDetails
{

    @XmlElement(name = "base-dn")
    private String baseDn;

    @XmlElement(name = "object-class")
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
