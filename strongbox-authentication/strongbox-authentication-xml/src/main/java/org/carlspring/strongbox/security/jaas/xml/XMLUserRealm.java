package org.carlspring.strongbox.security.jaas.xml;

import org.carlspring.strongbox.dao.xml.UsersDao;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.security.jaas.authentication.UserResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public class XMLUserRealm
        implements UserResolver
{

    @Autowired
    private UsersDao usersDaoXml;


    @Override
    public User findUser(String username)
            throws UserResolutionException
    {
        return getUsersDaoXml().findUser(username);
    }

    @Override
    public User findUser(String username,
                         String password)
            throws UserResolutionException
    {
        return getUsersDaoXml().findUser(username, password);
    }

    public UsersDao getUsersDaoXml()
    {
        return usersDaoXml;
    }

    public void setUsersDaoXml(UsersDao usersDaoXml)
    {
        this.usersDaoXml = usersDaoXml;
    }

}
