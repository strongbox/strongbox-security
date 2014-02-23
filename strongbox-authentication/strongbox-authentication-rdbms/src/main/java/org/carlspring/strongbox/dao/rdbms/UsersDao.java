package org.carlspring.strongbox.dao.rdbms;

import org.carlspring.strongbox.security.jaas.Role;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.security.jaas.authentication.UserResolver;
import org.carlspring.strongbox.security.jaas.authentication.UserStorage;

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
