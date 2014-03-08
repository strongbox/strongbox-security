package org.carlspring.strongbox.dao.ldap;

import org.carlspring.strongbox.security.jaas.Group;

import java.util.List;

/**
 * @author mtodorov
 */
public interface GroupsDao
{

    List<Group> getUserGroups(String username);

    List<String> getGroupNames(String username);

    List<Group> getAllGroups(String baseDn);

    List<Group> getAllGroups(String baseDn, String filter);

    Group getGroup(String groupDn);

}
