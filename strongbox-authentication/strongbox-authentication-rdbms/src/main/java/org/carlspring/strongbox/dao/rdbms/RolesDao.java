package org.carlspring.strongbox.dao.rdbms;

import org.carlspring.strongbox.jaas.Role;

import java.sql.SQLException;

/**
 * @author mtodorov
 */
public interface RolesDao extends BaseDBDao
{

    void createRole(Role role)
            throws SQLException;

    Role findRole(long roleId)
            throws SQLException;

    Role findRole(String name)
            throws SQLException;

    void updateRole(Role role)
            throws SQLException;

    void removeRole(Role role)
            throws SQLException;

    void removeRoleById(long roleId)
            throws SQLException;

}
