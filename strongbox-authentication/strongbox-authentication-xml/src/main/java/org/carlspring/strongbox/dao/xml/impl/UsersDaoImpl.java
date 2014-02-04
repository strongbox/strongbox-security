package org.carlspring.strongbox.dao.xml.impl;

import org.carlspring.strongbox.dao.xml.UsersDao;
import org.carlspring.strongbox.jaas.Role;
import org.carlspring.strongbox.jaas.User;

import java.sql.SQLException;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author mtodorov
 */
public class UsersDaoImpl
        implements UsersDao
{

    private static final Logger logger = Logger.getLogger(UsersDaoImpl.class);


    public UsersDaoImpl()
    {
    }

    @Override
    public void createUser(User user)
            throws SQLException
    {

    }

    @Override
    public void updateUser(User user)
            throws SQLException
    {

    }

    @Override
    public void removeUser(User user)
            throws SQLException
    {

    }

    @Override
    public void removeUserById(long userId)
            throws SQLException
    {

    }

    @Override
    public void assignRole(User user,
                           Role role)
            throws SQLException
    {

    }

    @Override
    public void assignRole(User user, String roleName)
            throws SQLException
    {

    }

    @Override
    public Set<Role> getRoles(User user)
            throws SQLException
    {
        return null;
    }

    @Override
    public void removeRole(User user, Role role)
            throws SQLException
    {

    }

    @Override
    public boolean hasRole(User user, String roleName)
            throws SQLException
    {
        return false;
    }

    @Override
    public User findUser(long userId)
            throws Exception
    {
        return null;
    }

    @Override
    public User findUser(String username)
            throws Exception
    {
        return null;
    }

    @Override
    public User findUser(String username, String password)
            throws Exception
    {
        return null;
    }
}
