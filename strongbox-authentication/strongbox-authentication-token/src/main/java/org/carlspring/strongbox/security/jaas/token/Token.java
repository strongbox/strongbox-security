package org.carlspring.strongbox.security.jaas.token;

import java.util.Date;

/**
 * @author mtodorov
 */
public class Token
{

    private String username;

    private String password;

    private Date generationDate;

    private Date expirationDate;


    public Token()
    {
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Date getGenerationDate()
    {
        return generationDate;
    }

    public void setGenerationDate(Date generationDate)
    {
        this.generationDate = generationDate;
    }

    public Date getExpirationDate()
    {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate)
    {
        this.expirationDate = expirationDate;
    }

}
