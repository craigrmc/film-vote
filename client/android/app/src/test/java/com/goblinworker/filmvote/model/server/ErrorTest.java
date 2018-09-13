package com.goblinworker.filmvote.model.server;

import org.junit.Assert;
import org.junit.Test;

public class ErrorTest {

    @Test
    public void isValidTestBaseError() {
        Error error = new Error();

        boolean result = error.isValid();

        Assert.assertFalse(result);
    }

    @Test
    public void isValidTestFullError() {
        Error error = makeError();

        boolean result = error.isValid();

        Assert.assertTrue(result);
    }

    public Error makeError() {

        Error error = new Error();
        error.setTimestamp("2018-08-29T19:36:18.384+0000");
        error.setStatus(500);
        error.setError("Internal Server Error");
        error.setMessage("club does not exist");
        error.setPath("/mobile/v1/signIn/Club%20RC/User%20RC");

        return error;
    }

}
