package org.carlspring.strongbox.dao.rdbms.impl;

import org.carlspring.strongbox.dao.rdbms.RolesDao;
import org.carlspring.strongbox.security.jaas.Role;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.security.jaas.authentication.UserStorageException;
import org.carlspring.strongbox.resource.ResourceCloser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mtodorov
 */
public class RolesDaoImpl
        extends BaseDaoImpl
        implements RolesDao
{

    private static final Logger logger = LoggerFactory.getLogger(RolesDaoImpl.class);

    public static final String TABLE_NAME = "roles";


    public RolesDaoImpl()
    {
        super();

        this.dataBean = Role.class;
    }

    @Override
    public void createRole(Role role)
            throws UserStorageException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "INSERT INTO roles (role_name, description) VALUES (?, ?)";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, role.getName());
            ps.setString(2, role.getDescription());

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
    public Role findRole(String name)
            throws UserResolutionException
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
                role.setName(rs.getString("ROLE_NAME"));
                role.setDescription(rs.getString("DESCRIPTION"));
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

        return role;
    }

    @Override
    public void updateRole(Role role)
            throws UserStorageException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "UPDATE roles" +
                               "   SET description = ?" +
                               " WHERE role_name = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setString(1, role.getDescription());
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

    @Override
    public void removeRole(Role role)
            throws UserStorageException
    {
        // TODO; Re-implement
    }

    @Override
    public void removeRole(String name)
            throws UserStorageException
    {
        // TODO; Re-implement
    }

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

}
