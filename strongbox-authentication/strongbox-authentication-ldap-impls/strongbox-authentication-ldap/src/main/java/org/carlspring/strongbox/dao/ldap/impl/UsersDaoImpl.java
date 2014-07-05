package org.carlspring.strongbox.dao.ldap.impl;

import org.carlspring.strongbox.configuration.LDAPConfiguration;
import org.carlspring.strongbox.configuration.UserMapping;
import org.carlspring.strongbox.dao.ldap.UsersDao;
import org.carlspring.strongbox.resource.ConfigurationResourceResolver;
import org.carlspring.strongbox.resource.ResourceCloser;
import org.carlspring.strongbox.security.jaas.Group;
import org.carlspring.strongbox.security.jaas.LDAPGroup;
import org.carlspring.strongbox.security.jaas.User;
import org.carlspring.strongbox.security.jaas.authentication.NotSupportedException;
import org.carlspring.strongbox.security.jaas.authentication.UserResolutionException;
import org.carlspring.strongbox.visitors.ParentGroupVisitor;
import org.carlspring.strongbox.xml.parsers.LDAPConfigurationParser;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author mtodorov
 */
@Component
public class UsersDaoImpl extends BaseLdapDaoImpl
    implements UsersDao
{

    private static final Logger logger = LoggerFactory.getLogger(UsersDaoImpl.class);

    @Autowired
    private ConfigurationResourceResolver configurationResourceResolver;

    @Autowired
    private LDAPConfigurationParser ldapConfigurationParser;

    private LDAPConfiguration ldapConfiguration;


    public UsersDaoImpl()
    {
    }

    @Override
    public User findUser(String username)
            throws UserResolutionException
    {
        return null;
    }

    @Override
    public User findUser(String uid, String password)
            throws UserResolutionException
    {
        User user = null;
        DirContext ctx = null;
        NamingEnumeration results = null;

        // logger.debug("cn:/ " + cn);

        try
        {
            // Step 1: Bind anonymously
            ctx = getContext();

            // Step 2: Search the directory
            final UserMapping userMapping = ldapConfiguration.getAttributeMappings().getUserMapping();

            String filter;
            if (userMapping.getFilter() != null && !userMapping.getFilter().trim().equals(""))
            {
                filter = userMapping.getFilter();
            }
            else
            {
                filter = "(&(objectClass=" + userMapping.getQuery().getObjectClass() + ")" +
                         "(" + userMapping.getUid() + "={0})" +
                         "(" + userMapping.getPassword() + "={1}))";
            }

            String[] attrIDs = new String[]{ "ou",
                                             userMapping.getUid(),       // username
                                             userMapping.getFullName(),  // common name (full name)
                                             userMapping.getEmail() };

            SearchControls controls = getSearchControls(attrIDs);
            controls.setReturningAttributes(attrIDs);
            controls.setTimeLimit(getTimeout());

            results = ctx.search(getRootDn(), filter, new String[]{ uid, password }, controls);

            String fullName = null;
            String email = null;

            String userDn = null;

            Attributes attributes;
            if (results.hasMore())
            {
                // Fetch user details
                SearchResult result = (SearchResult) results.next();
                userDn = result.getNameInNamespace();

                logger.debug("dn: " + userDn);

                attributes = result.getAttributes();

                Attribute attrUId = attributes.get(userMapping.getUid());
                Attribute attrFullName = attributes.get(userMapping.getFullName());
                Attribute attrEmail = attributes.get(userMapping.getEmail());

                uid = (String) attrUId.get();
                // Null-proof these:
                fullName = attrFullName != null ? (String) attrFullName.get() : null;
                email = attrEmail != null ? (String) attrEmail.get() : null;

                // Get user groups
                Set<Group> groupsRaw = getUserGroups(userDn, ctx);
                Set<String> groups = new LinkedHashSet<String>();

                for(Group group : groupsRaw)
                {
                    groups.add(group.getName());
                }

                List<String> roles = new ArrayList<String>();
                roles.addAll(groups);

                logger.debug(" * uid:            " + attrUId.get());
                logger.debug(" * full name:      " + fullName);
                logger.debug(" * e-mail:         " + email);
                logger.debug(" * groups:         " + groups);
                logger.debug("\n");

                user = new User();
                user.setUsername(uid);
                user.setFullName(fullName);
                user.setRoles(roles);
            }

            // if (dn == null || results.hasMore())
            // {
            //     // uid not found or not unique
            //     throw new NamingException("Authentication failed.");
            // }

            // Step 3: Bind with found DN and given password
            DirContext bindCtx = getContext(userDn, password);

            logger.debug("Authentication successful for user " + user + ".");
        }
        catch (NamingException e)
        {
            throw new UserResolutionException(e.getMessage(), e);
        }
        finally
        {
            ResourceCloser.close(ctx, logger);
            ResourceCloser.close(results, logger);
        }

        return user;
    }

    /**
     * Retrieve a list of all groups the user is assigned to by selecting all groups where
     * the 'uniqueMember' attribute equals to the user's DN.
     *
     * @param userDn
     * @param ctx
     * @return
     * @throws NamingException
     */
    private Set<Group> getUserGroups(String userDn, DirContext ctx)
            throws NamingException
    {
        Set<Group> groups = new LinkedHashSet<Group>();

        String getGroupsQuery = "(&(objectclass=groupOfUniqueNames)(uniqueMember={0}))";
        String[] attrIDs = new String[]{ "ou",
                                         "cn",  // common name (full name)
                                         "description" };

        SearchControls controls = getSearchControls(attrIDs);
        controls.setReturningAttributes(attrIDs);
        controls.setTimeLimit(getTimeout());

        // Search for groups specifically in the Groups entry - we don't want to get all groupOfUniqueNames
        // stored in the ldap server. We need to get only the specific groups for the strogbox application.
        NamingEnumeration results = ctx.search("ou=Groups," + getRootDn(), getGroupsQuery, new String[]{userDn}, controls);

        if(results.hasMore())
        {
            // Handle the direct groups
            Set<LDAPGroup> directGroups = new LinkedHashSet<LDAPGroup>();

            // Get the specific group and work our way to the parent (reverse traversing)
            while(results.hasMore())
            {
                SearchResult result = (SearchResult) results.next();
                String groupDn = result.getNameInNamespace();

                Attributes attributes = result.getAttributes();

                Attribute attrOu = attributes.get("ou");
                Attribute attrDescription = attributes.get("description");

                String name = attrOu != null ? (String) attrOu.get() : null;
                String description = attrDescription != null ? (String) attrDescription.get() : null;

                // Get parent groups
                LDAPGroup parentGroup = getParentGroup(groupDn, ctx);

                // Create the group and add it's parent groups.
                LDAPGroup group = new LDAPGroup();
                group.setName(name);
                group.setDescription(description);
                group.setGroupDN(groupDn);
                group.setParent(parentGroup);

                directGroups.add(group);
            }

            // Reverse the tree
            for (Group group : directGroups)
            {
                ParentGroupVisitor visitor = new ParentGroupVisitor();
                try
                {
                    visitor.visit(group, groups);
                }
                catch (NotSupportedException e)
                {
                    e.printStackTrace();
                    break;
                }
            }
        }

        return groups;
    }


    /**
     * Traverse the DIT and get all parent groups starting after groupDN up the DIT tree.
     *
     * @param groupDn
     * @param ctx
     * @return
     * @throws NamingException
     */
    private LDAPGroup getParentGroup(String groupDn, DirContext ctx)
            throws NamingException
    {
        String parentDn = getParentDn(groupDn);

        String filter = "(objectClass=groupOfUniqueNames)";
        String[] attrIDs = new String[]{ "ou",
                                         "cn",        // common name (full name)
                                         "description" };

        SearchControls controls = getSearchControls(attrIDs);
        controls.setCountLimit(1);
        controls.setSearchScope(SearchControls.OBJECT_SCOPE);
        controls.setReturningAttributes(attrIDs);
        controls.setTimeLimit(getTimeout());

        NamingEnumeration results = ctx.search(parentDn, filter, new String[]{ }, controls);

        if (results.hasMore())
        {
            // System.out.println("Parent group: "+parentDn);

            SearchResult result = (SearchResult) results.next();

            Attributes attributes = result.getAttributes();

            Attribute attrOu = attributes.get("ou");
            Attribute attrDescription = attributes.get("description");

            String name = attrOu != null ? (String) attrOu.get() : null;
            String description = attrDescription != null ? (String) attrDescription.get() : null;

            LDAPGroup group = new LDAPGroup();

            group.setName(name);
            group.setDescription(description);
            group.setGroupDN(groupDn);

            // Check for parent
            LDAPGroup parentGroup = getParentGroup(parentDn, ctx);

            if(parentGroup != null)
            {
                group.setParent(parentGroup);
            }

            return group;

        }

        return null;
    }

    /**
     * Parses a DN and returns the first parent.
     *
     * Example: ou=Developers,ou=Employees,ou=Groups,dc=carlspring,dc=com
     * Returns: ou=Employees,ou=Groups,dc=carlspring,dc=com
     *
     * @param dn
     * @return
     */
    private String getParentDn(String dn)
    {
        // Remove the first OU from the string.
        List<String> parentDNRaw = Arrays.asList(dn.split("(\\s*)?,(\\s*)?"));

        String parent = "";

        for (int i = 1; i < parentDNRaw.size(); i++)
        {
            parent += parentDNRaw.get(i);

            if (parentDNRaw.size() > 1 && i < (parentDNRaw.size() - 1))
            {
                parent += ",";
            }
        }

        return parent;
    }

    public void load()
            throws IOException
    {
        Resource resource = configurationResourceResolver.getConfigurationResource(ConfigurationResourceResolver.getBasedir() +
                                                                                   "/etc/conf/security-authentication-ldap.xml",
                                                                                   "security.users.xml",
                                                                                   "etc/conf/security-authentication-ldap.xml");

        logger.info("Loading Strongbox configuration from " + resource.toString() + "...");

        ldapConfiguration = ldapConfigurationParser.parse(resource.getInputStream());
    }

    public LDAPConfiguration getLdapConfiguration()
    {
        return ldapConfiguration;
    }

    public void setLdapConfiguration(LDAPConfiguration ldapConfiguration)
    {
        this.ldapConfiguration = ldapConfiguration;
    }

    @Override
    public String getDomain()
    {
        return ldapConfiguration.getDomain();
    }

}
