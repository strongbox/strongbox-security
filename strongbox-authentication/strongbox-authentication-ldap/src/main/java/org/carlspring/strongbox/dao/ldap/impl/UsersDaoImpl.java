package org.carlspring.strongbox.dao.ldap.impl;

import org.carlspring.strongbox.dao.ldap.UsersDao;
import org.carlspring.strongbox.jaas.User;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

/**
 * @author mtodorov
 */
public class UsersDaoImpl
        implements UsersDao
{


    public UsersDaoImpl()
    {
    }

    @Override
    public User findUser(long userId)
            throws Exception
    {
        return null;
    }

    @Override
    public User findUser(String username)
            throws Exception
    {
        return null;
    }

    /**
     *
     * @param DNString - doesn't have a strict string format (the default is considered to be: cn=Firstname Lastname,ou=people,dc=Domain,dc=com
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public User findUser(String DNString, String password)
            throws AuthenticationException
    {
        Hashtable env = new Hashtable();
        User user = null;
        try{

            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://carlspring.org:10389/");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, DNString);
            env.put(Context.SECURITY_CREDENTIALS, password);

            // Create the initial context
            DirContext ctx = new InitialDirContext(env);

            // Ask for all attributes of the object
            Attributes attrs = ctx.getAttributes(DNString);

            // Get attributes from LDAP/ApacheDS
            Hashtable<String, String> userData = new Hashtable<String, String>();
            userData.put("uid", String.valueOf(attrs.get("uid").get()));
            userData.put("cn", String.valueOf(attrs.get("cn").get()));
            userData.put("mail", String.valueOf(attrs.get("mail").get()));

            System.out.println("User \""+String.valueOf(attrs.get("uid").get())+"\" attributes: " +
                    "\n\t uid: "+userData.get("uid")+
                    "\n\t cn: "+userData.get("cn")+
                    "\n\t email: "+userData.get("mail"));

            // Populate a User object to use it in the application..
            user = new User();
            user.setUsername(String.valueOf(attrs.get("uid").get()));
        } catch (NamingException e){
            throw new AuthenticationException();
        }
        return user;
    }

}
