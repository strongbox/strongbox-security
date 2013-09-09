package org.carlspring.strongbox.jaas.ldap;

//import org.carlspring.strongbox.dao.UsersDao;
//import org.carlspring.strongbox.dao.impl.UsersDaoImpl;
import org.carlspring.strongbox.jaas.User;
import org.carlspring.strongbox.jaas.authentication.UserResolver;

/**
 * @author mtodorov
 */
public class LDAPUserRealm
        implements UserResolver
{

    // TODO: Inject this via IoC somehow at some point instead:
//    private UsersDao usersDao = new UsersDaoImpl();


    @Override
    public User findUser(long userId)
            throws Exception
    {
        return null;
        // return usersDao.findUser(userId);
    }

    @Override
    public User findUser(String username)
            throws Exception
    {
        return null;
//        return usersDao.findUser(username);
    }

    @Override
    public User findUser(String username,
                         String password)
            throws Exception
    {
        return null;
//        return usersDao.findUser(username, password);
    }

    /*
    public UsersDao getUsersDao()
    {
        return usersDao;
    }

    public void setUsersDao(UsersDao usersDao)
    {
        this.usersDao = usersDao;
    }
    */

}
