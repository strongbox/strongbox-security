package org.carlspring.strongbox.dao.rdbms.impl;

import org.carlspring.ioc.InjectionException;
import org.carlspring.ioc.PropertiesResources;
import org.carlspring.ioc.PropertyValue;
import org.carlspring.ioc.PropertyValueInjector;
import org.carlspring.strongbox.dao.rdbms.BaseDBDao;
import org.carlspring.strongbox.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.jaas.authentication.UserStorageException;
import org.carlspring.strongbox.resource.ResourceCloser;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mtodorov
 */
@PropertiesResources(resources = { "META-INF/properties/jdbc.properties" })
public abstract class BaseDaoImpl
        implements BaseDBDao
{

    private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    public static final int JNDI_UNKNOWN = 0;
    public static final int JNDI_AVAILABLE = 1;
    public static final int JNDI_NOT_AVAILABLE = 2;

    int JNDI_STATUS = JNDI_UNKNOWN;

    Class dataBean;

    @PropertyValue (key = "jdbc.jndi.name")
    private String datasourceName;

    @PropertyValue (key = "jdbc.driver")
    private String driver;

    @PropertyValue (key = "jdbc.url")
    private String jdbcURL;

    @PropertyValue (key = "jdbc.username")
    private String username;

    @PropertyValue (key = "jdbc.password")
    private String password;


    public BaseDaoImpl()
    {
        try
        {
            PropertyValueInjector.inject(this);
        }
        catch (InjectionException e)
        {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Connection getConnection()
            throws SQLException
    {
        Connection connection = null;

        if (JNDI_STATUS == JNDI_UNKNOWN)
        {
            try
            {
                connection = getJNDIConnection();

                if (connection != null)
                {
                    JNDI_STATUS = JNDI_AVAILABLE;
                }
                else
                {
                    connection = getDirectConnection();
                }
            }
            catch (SQLException e)
            {
                JNDI_STATUS = JNDI_NOT_AVAILABLE;

                connection = getDirectConnection();
            }
        }
        else if (JNDI_STATUS == JNDI_AVAILABLE)
        {
            connection = getDirectConnection();
        }

        return connection;
    }

    private Connection getDirectConnection()
    {
        Connection connection = null;
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcURL, username, password);
        }
        catch (ClassNotFoundException e)
        {
            logger.error(e.getMessage(), e);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }

        return connection;
    }

    public Connection getJNDIConnection()
            throws SQLException
    {
        DataSource ds;
        Connection conn;
        try
        {
            InitialContext jndiContext = new InitialContext();
            ds = (DataSource) jndiContext.lookup(datasourceName);
            conn = ds.getConnection();
        }
        catch (javax.naming.NamingException ne)
        {
            return getDirectConnection();
        }
        catch (SQLException e)
        {
            logger.error(e.getMessage(), e);
            throw new SQLException(e);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new SQLException(e);
        }

        return conn;
    }

    public long count()
            throws UserResolutionException
    {
        return count(null);
    }

    @Override
    public long count(String whereClause)
            throws UserResolutionException
    {
        long count = 0;

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;


        final String tableName = getTableName();
        final String sql = "SELECT COUNT(*)" +
                           "  FROM " + tableName + (whereClause != null ?
                           " WHERE " + whereClause : "");

        try
        {
            connection = getConnection();
            stmt = connection.createStatement();

            rs = stmt.executeQuery(sql);
            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            logger.error(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(rs, logger);
            ResourceCloser.close(stmt, logger);
            ResourceCloser.close(connection, logger);
        }

        return count;
    }

    @Override
    public void deleteById(String fieldIdName, long fieldIdValue)
            throws SQLException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "DELETE FROM " + getTableName() +
                              "  WHERE " + fieldIdName + " = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setLong(1, fieldIdValue);
            ps.executeUpdate();
        }
        finally
        {
            ResourceCloser.close(ps, logger);
            ResourceCloser.close(connection, logger);
        }
    }

    @Override
    public void deleteByWhereClause(String whereClause)
            throws SQLException
    {
        Connection connection = null;
        Statement stmt = null;

        try
        {
            final String sql = "DELETE FROM " + getTableName() +
                              "  WHERE " + whereClause ;

            connection = getConnection();

            stmt = connection.prepareStatement(sql);
            stmt.execute(sql);
        }
        finally
        {
            ResourceCloser.close(stmt, logger);
            ResourceCloser.close(connection, logger);
        }
    }

}
