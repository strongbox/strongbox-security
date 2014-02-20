package org.carlspring.strongbox.dao.rdbms.impl;

import org.carlspring.strongbox.dao.rdbms.UsersDao;
import org.carlspring.strongbox.jaas.User;
import org.carlspring.strongbox.util.encryption.EncryptionUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

/**
 * @author mtodorov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/strongbox-*-context.xml", "classpath*:/META-INF/spring/strongbox-*-context.xml"})
public class UsersDaoImplTest
{

    public static final String USERNAME = "test_" + System.currentTimeMillis();

    public static final String PASSWORD = EncryptionUtils.encryptWithMD5("password");

    @Autowired
    private UsersDao usersDao;


    @Test
    public void testCreateAndUpdateUser()
            throws Exception
    {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        final long countOld = usersDao.count();

        usersDao.createUser(user);

        final long countNew = usersDao.count();

        assertTrue("Failed to create user '" + USERNAME + "'!", countOld < countNew);

        // Update the user

        // TODO: SB-84: Add option to prefix passwords with their encryption algorithm
        // TODO: Re-visit this at a later time
        // final String changedPassword = "MD5:" + EncryptionUtils.encryptWithMD5("newpassword");
        final String changedPassword = EncryptionUtils.encryptWithMD5("newpassword");

        user.setPassword(changedPassword);

        usersDao.updateUser(user);

        final User updatedUser = usersDao.findUser(USERNAME, changedPassword);

        assertEquals("Failed to update the user!", changedPassword, updatedUser.getPassword());

        user = updatedUser;

        // Test roles
        String roleName = "ADMINISTRATOR";
        assertFalse("This user is already an administrator!", usersDao.hasRole(user, roleName));

        usersDao.assignRole(user, roleName);

        assertTrue("Failed to assign role 'ADMINISTRATOR' to user '" + USERNAME + "'",
                   usersDao.hasRole(user, roleName));

        // Delete the user
        usersDao.removeUserById(user.getUserId());
    }

}
