package org.carlspring.strongbox.dao.xml.impl;

import org.carlspring.strongbox.dao.xml.UsersDao;
import org.carlspring.strongbox.resource.ConfigurationResourceResolver;
import org.carlspring.strongbox.security.jaas.Role;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.Users;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.security.jaas.authentication.UserStorageException;
import org.carlspring.strongbox.security.jaas.managers.UserManager;
import org.carlspring.strongbox.xml.parsers.GenericParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public class UsersDaoImpl
        implements UsersDao
{

    private static final Logger logger = LoggerFactory.getLogger(UsersDaoImpl.class);

    @Autowired
    private ConfigurationResourceResolver configurationResourceResolver;

    @Autowired
    private UserManager userManager;

    private GenericParser<Users> userParser = new GenericParser<>(Users.class);


    public UsersDaoImpl()
    {
    }

    @Override
    public void createUser(User user)
            throws UserStorageException
    {
        //noinspection TryWithIdenticalCatches
        try
        {
            userManager.add(user);

            userParser.store(new Users(userManager.getUsersAsSet()), getUsersConfigurationFile().getFile());
        }
        catch (IOException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
        catch (JAXBException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
    }

    @Override
    public void updateUser(User user)
            throws UserStorageException
    {
        // In effect, this is the same operation here, as the user is stored in a map,
        // which needs to be serialized all over again.
        createUser(user);
    }

    @Override
    public void removeUser(User user)
            throws UserStorageException
    {
        removeUser(user.getUsername());
    }

    @Override
    public void removeUser(String username)
            throws UserStorageException
    {
        if (userManager.contains(username))
        {
            //noinspection TryWithIdenticalCatches
            try
            {
                userManager.remove(username);
                userParser.store(new Users(userManager.getUsersAsSet()), getUsersConfigurationFile().getFile());
            }
            catch (IOException e)
            {
                throw new UserStorageException(e.getMessage(), e);
            }
            catch (JAXBException e)
            {
                throw new UserStorageException(e.getMessage(), e);
            }
        }
        else
        {
            logger.error("Failed to remove user " + username + " as the user was not found in store.");
        }
    }

    @Override
    public void assignRole(User user,
                           Role role)
            throws UserStorageException
    {
        assignRole(user, role.getName());
    }

    @Override
    public void assignRole(User user,
                           String roleName)
            throws UserStorageException
    {
        //noinspection TryWithIdenticalCatches
        try
        {
            user.addRole(roleName);
            userManager.add(user);
            userParser.store(new Users(userManager.getUsersAsSet()), getUsersConfigurationFile().getFile());
        }
        catch (IOException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
        catch (JAXBException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
    }

    @Override
    public void removeRole(User user,
                           Role role)
            throws UserStorageException
    {
        removeRole(user, role.getName());
    }

    @Override
    public void removeRole(User user,
                           String roleName)
            throws UserStorageException
    {
        //noinspection TryWithIdenticalCatches
        try
        {
            user.removeRole(roleName);
            userParser.store(new Users(userManager.getUsersAsSet()), getUsersConfigurationFile().getFile());
        }
        catch (IOException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
        catch (JAXBException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
    }

    @Override
    public User findUser(String username)
            throws UserResolutionException
    {
        return userManager.get(username);
    }

    @Override
    public User findUser(String username,
                         String password)
            throws UserResolutionException
    {
        final User user = userManager.get(username);
        if (user.getPassword().equals(password))
        {
            return user;
        }

        return null;
    }

    @Override
    public long count()
            throws UserResolutionException
    {
        return userManager.getUsers().size();
    }

    @Override
    public boolean contains(String username)
            throws UserResolutionException
    {
        return userManager.contains(username);
    }

    public Resource getUsersConfigurationFile()
            throws IOException
    {
        return configurationResourceResolver.getConfigurationResource("security.users.xml",
                                                                      "etc/conf/security-users.xml");
    }

}
