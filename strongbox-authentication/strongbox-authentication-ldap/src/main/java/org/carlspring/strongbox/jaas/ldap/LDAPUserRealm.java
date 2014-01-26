package org.carlspring.strongbox.jaas.ldap;

import org.carlspring.ioc.InjectionException;
import org.carlspring.strongbox.dao.ldap.UsersDao;
import org.carlspring.strongbox.dao.ldap.impl.UsersDaoImpl;
import org.carlspring.strongbox.jaas.User;
import org.carlspring.strongbox.jaas.authentication.UserResolver;

/**
 * @author mtodorov
 */
public class LDAPUserRealm
        implements UserResolver
{

    // TODO: Inject this via IoC somehow at some point instead:
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
        // TODO: Replace with IoC
        if (usersDao == null)
        {
            usersDao = new UsersDaoImpl();
            usersDao.initialize();
        }

        return usersDao;
    }

    public void setUsersDao(UsersDao usersDao)
    {
        this.usersDao = usersDao;
    }

}
