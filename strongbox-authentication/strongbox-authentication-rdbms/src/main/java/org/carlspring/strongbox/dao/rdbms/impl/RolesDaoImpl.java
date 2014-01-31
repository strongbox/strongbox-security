package org.carlspring.strongbox.dao.rdbms.impl;

import org.carlspring.strongbox.dao.rdbms.RolesDao;
import org.carlspring.strongbox.jaas.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mtodorov
 */
public class RolesDaoImpl
        extends BaseDaoImpl
        implements RolesDao
{

    public static final String TABLE_NAME = "roles";


    public RolesDaoImpl()
    {
        super();

        this.dataBean = Role.class;
    }

    @Override
    public void createRole(Role role)
            throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            final String sql = "INSERT INTO roles (roleid, role_name, description) VALUES (DEFAULT, ?, ?)";

            connection = getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setString(2, role.getDescription());

            preparedStatement.execute();
        }
        finally
        {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    @Override
    public Role findRole(long roleId)
            throws SQLException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        Role role = null;
        ResultSet rs;

        try
        {
            final String sql = "SELECT * FROM roles WHERE roleid = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setLong(1, roleId);
            rs = ps.executeQuery();

            if (rs.next())
            {
                role = new Role();
                role.setRoleId(rs.getLong("roleid"));
                role.setName(rs.getString("role_name"));
                role.setDescription(rs.getString("description"));
            }
        }
        finally
        {
            closeStatement(ps);
            closeConnection(connection);
        }

        return role;
    }

    @Override
    public Role findRole(String name)
            throws SQLException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        Role role = null;
        ResultSet rs;

        try
        {
            final String sql = "SELECT * FROM roles WHERE role_name = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next())
            {
                role = new Role();
                role.setRoleId(rs.getLong("ROLEID"));
                role.setName(rs.getString("ROLE_NAME"));
                role.setDescription(rs.getString("DESCRIPTION"));
            }
        }
        finally
        {
            closeStatement(ps);
            closeConnection(connection);
        }

        return role;
    }

    @Override
    public void updateRole(Role role)
            throws SQLException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "UPDATE roles" +
                               "   SET role_name = ?," +
                               "       description = ?" +
                               " WHERE roleid = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, role.getName());
            ps.setString(2, role.getDescription());
            ps.setLong(3, role.getRoleId());
            ps.executeUpdate();
        }
        finally
        {
            closeStatement(ps);
            closeConnection(connection);
        }
    }

    @Override
    public void removeRole(Role role)
            throws SQLException
    {
        removeRoleById(role.getRoleId());
    }

    @Override
    public void removeRoleById(long roleId)
            throws SQLException
    {
        deleteById("roleid", roleId);
    }

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

}
