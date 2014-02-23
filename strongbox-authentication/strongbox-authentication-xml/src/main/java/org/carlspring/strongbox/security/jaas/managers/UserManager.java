package org.carlspring.strongbox.security.jaas.managers;

import org.carlspring.strongbox.security.jaas.User;

import java.util.*;

import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
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

    public List<User> getUsersAsList()
    {
        return new ArrayList<User>(users.values());
    }

    public void add(User user)
    {
        users.put(user.getUsername(), user);
    }

    public User get(String username)
    {
        return users.get(username);
    }

    public boolean contains(String username)
    {
        return users.containsKey(username);
    }

    public boolean contains(User username)
    {
        return users.containsValue(username);
    }

    public User remove(String username)
    {
        return users.remove(username);
    }

}
