package org.carlspring.strongbox.dao.ldap.impl;

import org.carlspring.strongbox.dao.ldap.GroupsDao;
import org.carlspring.strongbox.security.jaas.Group;

import java.util.List;

/**
 * @author mtodorov
 */
public class GroupsDaoImpl implements GroupsDao
{

    @Override
    public List<Group> getUserGroups(String username)
    {
        return null;
    }

    @Override
    public List<String> getGroupNames(String username)
    {
        return null;
    }

    @Override
    public List<Group> getAllGroups(String baseDn)
    {
        return null;
    }

    @Override
    public List<Group> getAllGroups(String baseDn,
                                    String filter)
    {
        return null;
    }

    @Override
    public Group getGroup(String groupDn)
    {
        return null;
    }

}
