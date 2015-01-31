package org.carlspring.strongbox.dao.jdbc;

import org.carlspring.strongbox.security.jaas.Role;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.security.jaas.authentication.UserStorageException;

/**
 * @author mtodorov
 */
public interface RolesDao
        extends BaseDBDao
{

    void createRole(Role role)
            throws UserStorageException;

    Role findRole(String name)
            throws UserResolutionException;

    void updateRole(Role role)
            throws UserStorageException;

    void removeRole(Role role)
            throws UserStorageException;

    void removeRole(String name)
            throws UserStorageException;

}
