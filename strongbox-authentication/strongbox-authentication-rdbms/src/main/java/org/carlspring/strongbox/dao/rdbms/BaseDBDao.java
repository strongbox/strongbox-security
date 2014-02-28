package org.carlspring.strongbox.dao.rdbms;

import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author mtodorov
 */
public interface BaseDBDao
{

    String getTableName();

    Connection getConnection()
            throws SQLException;

    long count()
            throws UserResolutionException;

    long count(String whereClause)
            throws UserResolutionException;

}
