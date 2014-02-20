package org.carlspring.strongbox.dao.rdbms;

import org.carlspring.strongbox.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.jaas.authentication.UserStorage;
import org.carlspring.strongbox.jaas.authentication.UserStorageException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    void deleteById(String fieldIdName, long fieldIdValue)
            throws SQLException;

    void deleteByWhereClause(String whereClause)
            throws SQLException;

}
