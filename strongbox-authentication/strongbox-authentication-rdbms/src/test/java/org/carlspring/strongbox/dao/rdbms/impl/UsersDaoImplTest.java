package org.carlspring.strongbox.dao.rdbms.impl;

import org.carlspring.strongbox.dao.rdbms.UsersDao;
import org.carlspring.strongbox.jaas.User;
import org.carlspring.strongbox.util.encryption.EncryptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * @author mtodorov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/*-context.xml"})
public class UsersDaoImplTest
{

    public static final String USERNAME = "test_" + System.currentTimeMillis();

    public static final String PASSWORD = EncryptionUtils.encryptWithMD5("password");


    @Test
    public void testCreateUser()
            throws Exception
    {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        UsersDao usersDao = new UsersDaoImpl();

        final long countOld = usersDao.count();

        usersDao.createUser(user);

        final long countNew = usersDao.count();

        assertTrue("Failed to create user '" + USERNAME + "'!", countOld < countNew);
    }

    @Test
    public void testGetUser()
            throws Exception
    {
        UsersDao usersDao = new UsersDaoImpl();
        final User user = usersDao.findUser(USERNAME, PASSWORD);

        assertNotNull("Failed to lookup user!", user);
        assertTrue("Failed to lookup user!", user.getUserId() > 0);
    }

    @Test
    public void testUpdateUser()
            throws Exception
    {
        UsersDao usersDao = new UsersDaoImpl();

        final User user = usersDao.findUser(USERNAME);
        final String changedPassword = "MD5:" + EncryptionUtils.encryptWithMD5("newpassword");

        user.setPassword(changedPassword);

        usersDao.updateUser(user);

        final User updatedUser = usersDao.findUser(USERNAME, changedPassword);

        assertEquals("Failed to update the user!", changedPassword, updatedUser.getPassword());
    }

    @Test
    public void testCount()
            throws SQLException
    {
        UsersDao usersDao = new UsersDaoImpl();
        final long count = usersDao.count();

        assertTrue("Failed to get the number of available users!", count > 0);

        System.out.println("Number of users in database: " + count);
    }

    @Test
    public void testAssignRole()
            throws Exception
    {
        UsersDao usersDao = new UsersDaoImpl();
        User user = usersDao.findUser(USERNAME);

        String roleName = "ADMINISTRATOR";

        assertFalse("This user is already an administrator!", usersDao.hasRole(user, roleName));

        usersDao.assignRole(user, roleName);

        assertTrue("Failed to assign role 'ADMINISTRATOR' to user '" + USERNAME + "'",
                   usersDao.hasRole(user, roleName));
    }

    @Test
    public void testHasRole()
            throws Exception
    {
        UsersDao usersDao = new UsersDaoImpl();
        final User user = usersDao.findUser(USERNAME);

        assertTrue("Failed to get the number of available users!", usersDao.hasRole(user, "ADMINISTRATOR"));

        System.out.println(user);
    }

    @Test
    public void testDeleteUser()
            throws Exception
    {
        UsersDao usersDao = new UsersDaoImpl();
        final User user = usersDao.findUser(USERNAME);
        usersDao.removeUserById(user.getUserId());
    }

}
