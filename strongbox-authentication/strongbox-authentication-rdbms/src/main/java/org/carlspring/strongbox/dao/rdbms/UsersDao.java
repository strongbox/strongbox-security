package org.carlspring.strongbox.dao.rdbms;

import org.carlspring.strongbox.jaas.Role;
import org.carlspring.strongbox.jaas.User;
import org.carlspring.strongbox.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.jaas.authentication.UserResolver;
import org.carlspring.strongbox.jaas.authentication.UserStorage;

import java.sql.SQLException;
import java.util.Set;

/**
 * @author mtodorov
 */
public interface UsersDao extends BaseDBDao, UserResolver, UserStorage
{

    Set<Role> getRoles(User user)
            throws UserResolutionException;

    boolean hasRole(User user, String roleName)
            throws UserResolutionException;

}
