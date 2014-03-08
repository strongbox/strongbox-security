package org.carlspring.strongbox.dao.rdbms.impl;

import org.carlspring.strongbox.dao.rdbms.UsersDao;
import org.carlspring.strongbox.security.jaas.Role;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.security.jaas.authentication.UserStorageException;
import org.carlspring.strongbox.security.jaas.util.RoleUtils;
import org.carlspring.strongbox.resource.ResourceCloser;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mtodorov
 */
public class UsersDaoImpl extends BaseDaoImpl
        implements UsersDao
{

    private static final Logger logger = LoggerFactory.getLogger(UsersDaoImpl.class);

    public static final String TABLE_NAME = "users";


    public UsersDaoImpl()
    {
        super();
        this.dataBean = User.class;
    }

    @Override
    public void createUser(User user)
            throws UserStorageException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            // TODO: This needs to be re-worked:
            final String sql = "INSERT INTO " + getTableName() + "(username, password, full_name, email) " +
                               "VALUES (?, ?, ?, ?)";

            connection = getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            // TODO: SB-84: Add option to prefix passwords with their encryption algorithm
            // TODO: Re-visit this at a later time
            // preparedStatement.setString(2, getPasswordWithEncryptionPrefix(user.getPassword()));
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFullName());
            preparedStatement.setString(4, user.getEmail());

            preparedStatement.execute();
        }
        catch (SQLException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(preparedStatement, logger);
            ResourceCloser.close(connection, logger);
        }
    }

    @Override
    public User findUser(String username,
                         String password)
            throws UserResolutionException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        User user = null;
        ResultSet rs;

        try
        {
            final String sql = "SELECT *" +
                               "  FROM " + getTableName() +
                               " WHERE username = ?" +
                               "   AND password = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            // TODO: SB-84: Add option to prefix passwords with their encryption algorithm
            // TODO: Re-visit this at a later time
            // ps.setString(2, getPasswordWithEncryptionPrefix(password));
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next())
            {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRoles(RoleUtils.toStringList(getRoles(user)));
            }
        }
        catch (SQLException e)
        {
            throw new UserResolutionException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(ps, logger);
            ResourceCloser.close(connection, logger);
        }

        return user;
    }

    @Override
    public User findUser(String username)
            throws UserResolutionException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        User user = null;
        ResultSet rs;

        try
        {
            final String sql = "SELECT *" +
                               "  FROM " + getTableName() +
                               " WHERE username = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next())
            {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRoles(RoleUtils.toStringList(getRoles(user)));
            }
        }
        catch (SQLException e)
        {
            throw new UserResolutionException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(ps, logger);
            ResourceCloser.close(connection, logger);
        }

        return user;
    }

    @Override
    public void updateUser(User user)
            throws UserStorageException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "UPDATE " + getTableName() +
                               "   SET password = ?" +
                               " WHERE username = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            // TODO: SB-84: Add option to prefix passwords with their encryption algorithm
            // TODO: Re-visit this at a later time
            // ps.setString(1, getPasswordWithEncryptionPrefix(user.getPassword()));
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getUsername());

            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(ps, logger);
            ResourceCloser.close(connection, logger);
        }
    }

    private String getPasswordWithEncryptionPrefix(String password)
    {
        return password.startsWith("MD5:") ? password : "MD5:" + password;
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
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            // TODO: Replace this with proper cascading via Hibernate, JPA, or something else:
            final String sql1 = "DELETE FROM user_roles" +
                                "  WHERE username = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql1);
            ps.setString(1, username);
            ps.executeUpdate();

            final String sql2 = "DELETE FROM " + getTableName() +
                              "  WHERE username = ?";

            ps = connection.prepareStatement(sql2);
            ps.setString(1, username);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(ps, logger);
            ResourceCloser.close(connection, logger);
        }
    }

    @Override
    public void assignRole(User user, Role role)
            throws UserStorageException
    {
        assignRole(user, role.getName());
    }

    @Override
    public void assignRole(User user, String roleName)
            throws UserStorageException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "INSERT INTO user_roles (username, role_name) " +
                               "VALUES (?, ?)";

            logger.debug(sql);

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, roleName);

            ps.execute();
        }
        catch (SQLException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(ps, logger);
            ResourceCloser.close(connection, logger);
        }
    }

    @Override
    public Set<Role> getRoles(User user)
            throws UserResolutionException
    {
        Set<Role> roles = new LinkedHashSet<Role>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            final String sql = "SELECT u.username, r.role_name, r.description " +
                               "  FROM " + getTableName() + " u, user_roles ur, access_roles r " +
                               " WHERE u.username = ur.username " +
                               "   AND r.role_name = ur.role_name" +
                               "   AND u.username = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());

            rs = ps.executeQuery();

            while (rs.next())
            {
                Role role = new Role();
                role.setName(rs.getString("role_name"));
                role.setDescription(rs.getString("description"));

                roles.add(role);
            }
        }
        catch (SQLException e)
        {
            throw new UserResolutionException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(rs, logger);
            ResourceCloser.close(ps, logger);
            ResourceCloser.close(connection, logger);
        }

        return roles;
    }

    @Override
    public void removeRole(User user, Role role)
            throws UserStorageException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "DELETE FROM user_roles" +
                              "  WHERE username = ? " +
                              "    AND role_name = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, role.getName());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(ps, logger);
            ResourceCloser.close(connection, logger);
        }
    }

    /**
     * TODO: This needs to be re-worked.
     *
     * @param username
     * @param roleName
     * @return
     * @throws SQLException
     */
    @Override
    public boolean hasRole(String username, String roleName)
            throws UserResolutionException
    {
        boolean hasRole = false;

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            final String sql = "SELECT COUNT(u.username) " +
                               "  FROM " + getTableName() + " u, user_roles ur, access_roles r" +
                               " WHERE u.username = ur.username " +
                               "   AND r.role_name = ur.role_name " +
                               "   AND u.username = ? " +
                               "   AND r.role_name = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, roleName);

            rs = ps.executeQuery();

            if (rs.next())
            {
                hasRole = rs.getLong(1) > 0;
            }
        }
        catch (SQLException e)
        {
            throw new UserResolutionException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(rs, logger);
            ResourceCloser.close(ps, logger);
            ResourceCloser.close(connection, logger);
        }

        return hasRole;
    }

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

}
