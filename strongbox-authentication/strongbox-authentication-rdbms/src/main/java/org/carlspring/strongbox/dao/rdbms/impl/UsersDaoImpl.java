package org.carlspring.strongbox.dao.rdbms.impl;

import org.carlspring.strongbox.dao.rdbms.UsersDao;
import org.carlspring.strongbox.jaas.Role;
import org.carlspring.strongbox.jaas.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author mtodorov
 */
public class UsersDaoImpl extends BaseDaoImpl
        implements UsersDao
{

    public static final String TABLE_NAME = "users";


    public UsersDaoImpl()
    {
        super();
        this.dataBean = User.class;
    }

    @Override
    public void createUser(User user)
            throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
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
        finally
        {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    @Override
    public User findUser(long userId)
            throws Exception
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
                user.setRoles(getRoles(user));
            }
        }
        finally
        {
            closeStatement(ps);
            closeConnection(connection);
        }

        return user;
    }

    @Override
    public User findUser(String username,
                         String password)
            throws Exception
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
                user.setRoles(getRoles(user));
            }
        }
        finally
        {
            closeStatement(ps);
            closeConnection(connection);
        }

        return user;
    }

    @Override
    public User findUser(String username)
            throws Exception
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
                user.setUserId(rs.getInt("userid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRoles(getRoles(user));
            }
        }
        finally
        {
            closeStatement(ps);
            closeConnection(connection);
        }

        return user;
    }

    @Override
    public void updateUser(User user)
            throws SQLException
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
        finally
        {
            closeStatement(ps);
            closeConnection(connection);
        }
    }

    private String getPasswordWithEncryptionPrefix(String password)
    {
        return (password.startsWith("MD5:") ? password : "MD5:" + password);
    }

    @Override
    public void removeUser(User user)
            throws SQLException
    {
        removeUserById(user.getUserId());
    }

    @Override
    public void removeUserById(long userId)
            throws SQLException
    {
        deleteById("userid", userId);
    }

    @Override
    public void assignRole(User user, String roleName)
            throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            final String sql = "INSERT INTO user_roles (userid, roleid) " +
                               "VALUES (?, (SELECT ROLEID FROM ROLES WHERE ROLE_NAME = ?))";

            System.out.println(sql);
            connection = getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, user.getUserId());
            preparedStatement.setString(2, roleName);

            preparedStatement.execute();
        }
        finally
        {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    @Override
    public void assignRole(User user, Role role)
            throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            final String sql = "INSERT INTO user_roles (userid, roleid) VALUES (?, ?)";

            System.out.println(sql);
            connection = getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, user.getUserId());
            preparedStatement.setLong(2, role.getRoleId());

            preparedStatement.execute();
        }
        finally
        {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    @Override
    public Set<Role> getRoles(User user)
            throws SQLException
    {
        Set<Role> roles = new LinkedHashSet<Role>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs;

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
        finally
        {
            closeStatement(ps);
            closeConnection(connection);
        }

        return roles;
    }

    @Override
    public void removeRole(User user, Role role)
            throws SQLException
    {
        deleteByWhereClause("userid = " +user.getUserId() +" AND roleid = " +role.getRoleId());
    }

    @Override
    public boolean hasRole(User user, String roleName)
            throws SQLException
    {
        boolean hasRole = false;

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs;

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
        finally
        {
            closeStatement(ps);
            closeConnection(connection);
        }

        return hasRole;
    }

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

}
