package org.carlspring.strongbox.dao.ldap;

import org.carlspring.ioc.InjectionException;
import org.carlspring.strongbox.jaas.authentication.UserResolver;

/**
 * @author mtodorov
 */
public interface UsersDao extends UserResolver
{

    void initialize() throws InjectionException;

}
