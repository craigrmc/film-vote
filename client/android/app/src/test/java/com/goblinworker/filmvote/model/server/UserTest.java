package com.goblinworker.filmvote.model.server;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void isValidTestBaseUser() {
        User user = new User();

        boolean result = user.isValid();

        Assert.assertFalse(result);
    }

    @Test
    public void isValidTestFullUser() {
        User user = makeUser();

        boolean result = user.isValid();

        Assert.assertTrue(result);
    }

    private User makeUser() {

        User user = new User();
        user.setClubName("Club Name");
        user.setUserName("User Name");

        return user;
    }

}
