package org.carlspring.strongbox.dao.impl;

import org.carlspring.strongbox.dao.RolesDao;
import org.carlspring.strongbox.dto.Role;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author mtodorov
 */
public class RolesDaoImplTest
{


    @Test
    public void testCreateRole()
            throws Exception
    {
        Role role = new Role();
        role.setName("Observe");
        role.setDescription("An observation role");

        RolesDao rolesDao = new RolesDaoImpl();

        final long countOld = rolesDao.count();

        rolesDao.createRole(role);

        final long countNew = rolesDao.count();

        assertTrue("Failed to create role '" + role.getName() + "'!", countOld < countNew);

        Role createdRole = rolesDao.findRole("Observe");
        System.out.println("roleid: " + createdRole.getRoleId());
    }

    @Test
    public void testGetRole()
            throws SQLException
    {
        String roleName = "Observe";

        RolesDao rolesDao = new RolesDaoImpl();
        final Role role = rolesDao.findRole(roleName);

        assertNotNull("Failed to lookup role!", role);
        assertTrue("Failed to lookup role!", role.getRoleId() > 0);
    }

    @Test
    public void testUpdateRole()
            throws SQLException
    {
        String roleName = "Observe";

        RolesDao rolesDao = new RolesDaoImpl();

        final Role role = rolesDao.findRole(roleName);
        final String description = "Permission to observe objects";

        role.setDescription(description);

        rolesDao.updateRole(role);

        final Role updatedRole = rolesDao.findRole(roleName);

        assertEquals("Failed to update the role!", description, updatedRole.getDescription());
    }

    @Test
    public void testDeleteRole()
            throws SQLException
    {
        String roleName = "Observe";

        RolesDao rolesDao = new RolesDaoImpl();
        final Role role = rolesDao.findRole(roleName);
        rolesDao.removeRoleById(role.getRoleId());
    }

    @Test
    public void testCount()
            throws Exception
    {
        RolesDao rolesDao = new RolesDaoImpl();
        final long count = rolesDao.count();

        assertTrue("Failed to get the number of available roles!", count > 0);

        System.out.println("Number of roles in database: " + count);
    }

}
