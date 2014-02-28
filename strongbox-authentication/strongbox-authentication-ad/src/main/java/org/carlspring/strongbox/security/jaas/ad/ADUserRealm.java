package org.carlspring.strongbox.security.jaas.ad;

import org.carlspring.ioc.InjectionException;
import org.carlspring.strongbox.dao.ad.UsersDao;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.security.jaas.authentication.UserResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public class ADUserRealm
        implements UserResolver
{

    @Autowired
    private UsersDao usersDao;


    @Override
    public User findUser(String username)
            throws UserResolutionException
    {
        User user = null;
        try
        {
            user = getUsersDao().findUser(username);
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
            user = getUsersDao().findUser(username, password);
        }
        catch (InjectionException e)
        {
            throw new UserResolutionException(e.getMessage(), e);
        }

        return user;
    }

    public UsersDao getUsersDao()
            throws InjectionException
    {
        return usersDao;
    }

    public void setUsersDao(UsersDao usersDao)
    {
        this.usersDao = usersDao;
    }

}
