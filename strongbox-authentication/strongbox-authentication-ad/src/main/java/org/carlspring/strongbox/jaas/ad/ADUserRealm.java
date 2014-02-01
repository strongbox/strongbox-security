package org.carlspring.strongbox.jaas.ad;

import org.carlspring.ioc.InjectionException;
import org.carlspring.strongbox.dao.ad.UsersDao;
import org.carlspring.strongbox.jaas.User;
import org.carlspring.strongbox.jaas.authentication.UserResolver;

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
    public User findUser(long userId)
            throws Exception
    {
        return getUsersDao().findUser(userId);
    }

    @Override
    public User findUser(String username)
            throws Exception
    {
        return getUsersDao().findUser(username);
    }

    @Override
    public User findUser(String username,
                         String password)
            throws Exception
    {
        return getUsersDao().findUser(username, password);
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
