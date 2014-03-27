package org.carlspring.strongbox.security.jaas.ad;

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
public class ADUserRealm
        implements UserResolver
{

    @Autowired
    private UsersDao usersDaoAd;


    @Override
    public User findUser(String username)
            throws UserResolutionException
    {
        User user = getUsersDaoAd().findUser(username);
        return user;
    }

    @Override
    public User findUser(String username,
                         String password)
            throws UserResolutionException
    {
        User user = getUsersDaoAd().findUser(username, password);
        return user;
    }

    public UsersDao getUsersDaoAd()
    {
        return usersDaoAd;
    }

    public void setUsersDaoAd(UsersDao usersDaoAd)
    {
        this.usersDaoAd = usersDaoAd;
    }

}
