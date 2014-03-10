package org.carlspring.strongbox.security.jaas.ldap;

import org.carlspring.ioc.InjectionException;
import org.carlspring.strongbox.dao.ldap.UsersDao;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.security.jaas.authentication.UserResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public class LDAPUserRealm
        implements UserResolver
{

    @Autowired
    private UsersDao usersDaoLdap;


    @Override
    public User findUser(String username)
            throws UserResolutionException
    {
        User user = null;
        try
        {
            user = getUsersDaoLdap().findUser(username);
        }
        catch (InjectionException e)
        {
            throw new UserResolutionException(e.getMessage(), e);
        }

        return user;
    }

    @Override
    public User findUser(String username,
                         String password)
            throws UserResolutionException
    {
        User user = null;
        try
        {
            user = getUsersDaoLdap().findUser(username, password);
        }
        catch (InjectionException e)
        {
            throw new UserResolutionException(e.getMessage(), e);
        }

        return user;
    }

    public UsersDao getUsersDaoLdap()
            throws InjectionException
    {
        return usersDaoLdap;
    }

    public void setUsersDaoLdap(UsersDao usersDaoLdap)
    {
        this.usersDaoLdap = usersDaoLdap;
    }

}
