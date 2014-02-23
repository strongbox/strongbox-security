package org.carlspring.strongbox.security.jaas.managers;

import org.carlspring.strongbox.security.jaas.Privilege;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public class PrivilegeManager
{

    private final Map<String, Privilege> privileges = new LinkedHashMap<String, Privilege>();


    public PrivilegeManager()
    {
    }

    public Map<String, Privilege> getPrivileges()
    {
        return privileges;
    }

    public void add(String key, Privilege privilege)
    {
        privileges.put(key, privilege);
    }

    public Privilege get(String privilege)
    {
        return privileges.get(privilege);
    }

    public boolean contains(String privilege)
    {
        return privileges.containsKey(privilege);
    }

    public boolean contains(Privilege privilege)
    {
        return privileges.containsValue(privilege);
    }

    public Privilege remove(String privilege)
    {
        return privileges.remove(privilege);
    }

}
