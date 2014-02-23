

# Description

This is the default built-in authentication and authorization realm.
While in theory it should be able to handle a couple of hundred users, if so many users
will be using the system, it make more sense to use a database or LDAP server as a store.


# Produced Resources

## etc/conf/security-roles.xml

This configuration files contains a list of privileges and the roles to which they have been assigned.

## etc/conf/security-users.xml

This configuration files contains a list of users and their assigned roles.

