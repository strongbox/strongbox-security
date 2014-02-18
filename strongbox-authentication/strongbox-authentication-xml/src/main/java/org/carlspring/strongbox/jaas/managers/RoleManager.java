package org.carlspring.strongbox.jaas.managers;

import org.carlspring.strongbox.jaas.Role;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mtodorov
 */
public class RoleManager
{

    private final Map<String, Role> roles = new LinkedHashMap<String, Role>();


    public RoleManager()
    {
    }

    public Map<String, Role> getRoles()
    {
        return roles;
    }

    public void add(String key, Role role)
    {
        roles.put(key, role);
    }

    public Role get(String role)
    {
        return roles.get(role);
    }

    public boolean contains(String role)
    {
        return roles.containsKey(role);
    }

    public boolean contains(Role role)
    {
        return roles.containsValue(role);
    }

    public Role remove(String role)
    {
        return roles.remove(role);
    }
    
}
