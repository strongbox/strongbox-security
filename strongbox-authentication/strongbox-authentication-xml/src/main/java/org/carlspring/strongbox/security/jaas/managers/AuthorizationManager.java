package org.carlspring.strongbox.security.jaas.managers;

import org.carlspring.strongbox.configuration.AuthorizationConfiguration;
import org.carlspring.strongbox.resource.ConfigurationResourceResolver;
import org.carlspring.strongbox.security.jaas.Privilege;
import org.carlspring.strongbox.security.jaas.Role;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.util.PrivilegeUtils;
import org.carlspring.strongbox.security.jaas.util.RoleUtils;
import org.carlspring.strongbox.xml.parsers.AuthorizationConfigurationParser;
import org.carlspring.strongbox.xml.parsers.UserParser;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
@Scope ("singleton")
public class AuthorizationManager implements ConfigurationManager
{

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationManager.class);

    @Autowired
    private ConfigurationResourceResolver configurationResourceResolver;

    private UserManager userManager = new UserManager();

    private PrivilegeManager privilegeManager = new PrivilegeManager();

    private RoleManager roleManager = new RoleManager();

    @Autowired
    private UserParser userParser;

    @Autowired
    private AuthorizationConfigurationParser authorizationConfigurationParser;


    public AuthorizationManager()
    {
    }

    @Override
    public void load()
            throws IOException
    {
        loadAuthorization();
        loadUsers();
    }

    private void loadUsers()
            throws IOException
    {
        Resource resource = configurationResourceResolver.getConfigurationResource("etc/conf/security-users.xml",
                                                                                   "security.users.xml",
                                                                                   "etc/conf/security-users.xml");

        logger.info("Loading Strongbox configuration from " + resource.toString() + "...");

        //noinspection unchecked
        List<User> users = (List<User>) userParser.parse(resource.getInputStream());
        for (User user : users)
        {
            userManager.add(user);
        }
    }

    private void loadAuthorization()
            throws IOException
    {
        AuthorizationConfigurationParser parser = new AuthorizationConfigurationParser();

        Resource resource = configurationResourceResolver.getConfigurationResource("etc/conf/security-authorization.xml",
                                                                                   "security.authorization.xml",
                                                                                   "etc/conf/security-authorization.xml");

        logger.info("Loading Strongbox configuration from " + resource.toString() + "...");

        AuthorizationConfiguration configuration = parser.parse(resource.getInputStream());
        for (Privilege privilege : configuration.getPrivileges())
        {
            privilegeManager.add(privilege.getName(), privilege);
        }

        for (Role role : configuration.getRoles())
        {
            roleManager.add(role.getName(), role);
        }
    }

    @Override
    public void store()
            throws IOException
    {
        storeUsers();
        storeAuthorization();
    }

    private void storeUsers()
            throws IOException
    {
        Resource resource = configurationResourceResolver.getConfigurationResource("etc/conf/security-users.xml",
                                                                                   "security.users.xml",
                                                                                   "etc/conf/security-users.xml");

        userParser.store(userManager.getUsersAsList(), resource.getFile().getAbsoluteFile());
    }

    private void storeAuthorization()
            throws IOException
    {
        Resource resource = configurationResourceResolver.getConfigurationResource("etc/conf/security-authorization.xml",
                                                                                   "security.authorization.xml",
                                                                                   "etc/conf/security-authorization.xml");

        AuthorizationConfiguration configuration = new AuthorizationConfiguration();
        configuration.setPrivileges(PrivilegeUtils.toList(privilegeManager.getPrivileges().values()));
        configuration.setRoles(RoleUtils.toList(roleManager.getRoles().values()));

        authorizationConfigurationParser.store(configuration, resource.getFile().getAbsoluteFile());
    }

    public UserManager getUserManager()
    {
        return userManager;
    }

    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }

    public PrivilegeManager getPrivilegeManager()
    {
        return privilegeManager;
    }

    public void setPrivilegeManager(PrivilegeManager privilegeManager)
    {
        this.privilegeManager = privilegeManager;
    }

    public RoleManager getRoleManager()
    {
        return roleManager;
    }

    public void setRoleManager(RoleManager roleManager)
    {
        this.roleManager = roleManager;
    }

    public ConfigurationResourceResolver getConfigurationResourceResolver()
    {
        return configurationResourceResolver;
    }

    public void setConfigurationResourceResolver(ConfigurationResourceResolver configurationResourceResolver)
    {
        this.configurationResourceResolver = configurationResourceResolver;
    }

}
