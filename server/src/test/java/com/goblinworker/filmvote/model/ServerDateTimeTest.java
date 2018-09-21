package com.goblinworker.filmvote.model;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;

public class ServerDateTimeTest {

    @Test
    public void getDateFormatTest() {
        DateFormat result = ServerDateTime.getDateFormat();
        Assert.assertNotNull(result);
    }

    @Test
    public void getTimeFormatTest() {
        DateFormat result = ServerDateTime.getTimeFormat();
        Assert.assertNotNull(result);
    }

    @Test
    public void getDateTimeFormatTest() {
        DateFormat result = ServerDateTime.getDateTimeFormat();
        Assert.assertNotNull(result);
    }

}
