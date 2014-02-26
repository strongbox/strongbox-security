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
            final String sql = "INSERT INTO users (userid, username, password) VALUES (DEFAULT, ?, ?)";

            connection = getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            // TODO: SB-84: Add option to prefix passwords with their encryption algorithm
            // TODO: Re-visit this at a later time
            // preparedStatement.setString(2, getPasswordWithEncryptionPrefix(user.getPassword()));
            preparedStatement.setString(2, user.getPassword());

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

    /**
     * TODO: This needs to be removed
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public User findUser(long userId)
            throws UserResolutionException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        User user = null;
        ResultSet rs;

        try
        {
            final String sql = "SELECT *" +
                               "  FROM users" +
                               " WHERE userid = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setLong(1, userId);
            rs = ps.executeQuery();

            if (rs.next())
            {
                user = new User();
                user.setUserId(rs.getLong("userid"));
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
                               "  FROM users" +
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
                user.setUserId(rs.getInt("userid"));
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
                               "  FROM users" +
                               " WHERE username = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next())
            {
                user = new User();
                // TODO: This needs to be removed:
                user.setUserId(rs.getInt("userid"));
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
            final String sql = "UPDATE users" +
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
            final String sql = "DELETE FROM " + getTableName() +
                              "  WHERE username = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
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
    public void assignRole(User user, String roleName)
            throws UserStorageException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "INSERT INTO user_roles (userid, roleid) " +
                               "VALUES (?, (SELECT ROLEID FROM ROLES WHERE ROLE_NAME = ?))";

            logger.debug(sql);

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setLong(1, user.getUserId());
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
    public void assignRole(User user, Role role)
            throws UserStorageException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "INSERT INTO user_roles (userid, roleid) VALUES (?, ?)";

            logger.debug(sql);

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setLong(1, user.getUserId());
            ps.setLong(2, role.getRoleId());

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
            final String sql = "SELECT u.userid, r.roleid, r.role_name, r.description " +
                               "  FROM users u, user_roles ur, roles r " +
                               " WHERE u.userid = ur.userid " +
                               "   AND r.roleid = ur.roleid " +
                               "   AND u.userid = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setLong(1, user.getUserId());

            rs = ps.executeQuery();

            while (rs.next())
            {
                Role role = new Role();

                role.setRoleId(rs.getLong("roleid"));
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
        try
        {
            // TODO: This needs to be re-worked.
            deleteByWhereClause("userid = " +user.getUserId() +" AND roleid = " +role.getRoleId());
        }
        catch (SQLException e)
        {
            throw new UserStorageException(e.getMessage(), e);
        }
    }

    /**
     * TODO: This needs to be re-worked.
     *
     * @param user
     * @param roleName
     * @return
     * @throws SQLException
     */
    @Override
    public boolean hasRole(User user, String roleName)
            throws UserResolutionException
    {
        boolean hasRole = false;

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            final String sql = "SELECT COUNT(u.userid) " +
                               "  FROM users u, user_roles ur, roles r" +
                               " WHERE u.userid = ur.userid " +
                               "   AND r.roleid = ur.roleid " +
                               "   AND u.userid = ? " +
                               "   AND r.role_name = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setLong(1, user.getUserId());
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
