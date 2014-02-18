package org.carlspring.strongbox.jaas.managers;

import org.carlspring.strongbox.jaas.User;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mtodorov
 */
public class UserManager
{

    private final Map<String, User> users = new LinkedHashMap<String, User>();


    public UserManager()
    {
    }

    public Map<String, User> getUsers()
    {
        return users;
    }

    public void add(String key, User user)
    {
        users.put(key, user);
    }

    public User get(String user)
    {
        return users.get(user);
    }

    public boolean contains(String user)
    {
        return users.containsKey(user);
    }

    public boolean contains(User user)
    {
        return users.containsValue(user);
    }

    public User remove(String user)
    {
        return users.remove(user);
    }

}
