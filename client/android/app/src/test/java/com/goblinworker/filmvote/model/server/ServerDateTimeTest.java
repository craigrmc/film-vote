package com.goblinworker.filmvote.model.server;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class ServerDateTimeTest {

    private static final String TEST_DATE = "2005-11-28";
    private static final String TEST_TIME = "23:15:05";

    /**
     * Epoch timestamp:  0 ms
     * <p>
     * Human time (GMT): Thursday, January 1, 1970 12:00:00 AM
     * <p>
     * Human time (CST): Wednesday, December 31, 1969 6:00:00 PM GMT-06:00
     *
     * @return ServerDateTime
     */
    private static ServerDateTime makeDateTime1() {
        return new ServerDateTime(0L);
    }

    /**
     * Epoch timestamp:  1133136000000 ms
     * <p>
     * Human time (GMT): Monday, November 28, 2005 12:00:00 AM
     * <p>
     * Human time (CST): Sunday, November 27, 2005 6:00:00 PM GMT-06:00
     *
     * @return ServerDateTime
     */
    private static ServerDateTime makeDateTime2() {
        return new ServerDateTime(TEST_DATE, null);
    }

    /**
     * Epoch timestamp:  1133219715000 ms
     * <p>
     * Human time (GMT): Monday, November 28, 2005 11:15:15 PM
     * <p>
     * Human time (CST): Monday, November 28, 2005 5:15:15 PM GMT-06:00
     *
     * @return ServerDateTime
     */
    private static ServerDateTime makeDateTime3() {
        return new ServerDateTime(TEST_DATE, TEST_TIME);
    }

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

    @Test
    public void getTimeStampTestBaseObject() {
        ServerDateTime serverDateTime = new ServerDateTime(null, null);

        String result = serverDateTime.getTimeStamp();

        Assert.assertNull(result);
    }

    @Test
    public void getTimeStampTestNullDate() {
        ServerDateTime serverDateTime = new ServerDateTime(null, TEST_TIME);

        String result = serverDateTime.getTimeStamp();

        Assert.assertNull(result);
    }

    @Test
    public void getTimeStampTestEmptyDate() {
        ServerDateTime serverDateTime = new ServerDateTime("", TEST_TIME);

        String result = serverDateTime.getTimeStamp();

        Assert.assertNotNull(result);
    }

    @Test
    public void getTimeStampTestJunkDate() {
        ServerDateTime serverDateTime = new ServerDateTime("junk", TEST_TIME);

        String result = serverDateTime.getTimeStamp();

        Assert.assertNotNull(result);
    }

    @Test
    public void getTimeStampTestNullTime() {
        ServerDateTime serverDateTime = new ServerDateTime(TEST_DATE, null);

        String result = serverDateTime.getTimeStamp();

        Assert.assertNull(result);
    }

    @Test
    public void getTimeStampTestEmptyTime() {
        ServerDateTime serverDateTime = new ServerDateTime(TEST_DATE, "");

        String result = serverDateTime.getTimeStamp();

        Assert.assertNotNull(result);
    }

    @Test
    public void getTimeStampTestJunkTime() {
        ServerDateTime serverDateTime = new ServerDateTime(TEST_DATE, "junk");

        String result = serverDateTime.getTimeStamp();

        Assert.assertNotNull(result);
    }

    @Test
    public void getTimeStampTestFullObject() {
        ServerDateTime serverDateTime = makeDateTime3();

        String result = serverDateTime.getTimeStamp();

        Assert.assertNotNull(result);
        Assert.assertEquals("2005-11-28T23:15:05+00:00", result);
    }

    @Test
    public void getClientDateTestBaseObject() {
        ServerDateTime serverDateTime = new ServerDateTime(null, null);

        String result = serverDateTime.getClientDate();

        Assert.assertNull(result);
    }

    @Test
    public void getClientDateTestFullObject1() {
        ServerDateTime serverDateTime = makeDateTime1();

        String result = serverDateTime.getClientDate();

        Assert.assertNotNull(result);
    }

    @Test
    public void getClientDateTestFullObject2() {
        ServerDateTime serverDateTime = makeDateTime2();

        String result = serverDateTime.getClientDate();

        Assert.assertNotNull(result);
    }

    @Test
    public void getClientDateTestFullObject3() {
        ServerDateTime serverDateTime = makeDateTime3();

        String result = serverDateTime.getClientDate();

        Assert.assertNotNull(result);
    }

    @Test
    public void getClientTimeTestBaseObject() {
        ServerDateTime serverDateTime = new ServerDateTime(null, null);

        String result = serverDateTime.getClientTime();

        Assert.assertNull(result);
    }

    @Test
    public void getClientTimeTestFullObject1() {
        ServerDateTime serverDateTime = makeDateTime1();

        String result = serverDateTime.getClientTime();

        Assert.assertNotNull(result);
    }

    @Test
    public void getClientTimeTestFullObject2() {
        ServerDateTime serverDateTime = makeDateTime2();

        String result = serverDateTime.getClientTime();

        Assert.assertNull(result);
    }

    @Test
    public void getClientTimeTestFullObject3() {
        ServerDateTime serverDateTime = makeDateTime3();

        String result = serverDateTime.getClientTime();

        Assert.assertNotNull(result);
    }

    @Test
    public void getDisplayDateBaseObject() {
        ServerDateTime serverDateTime = new ServerDateTime(null, null);

        String result = serverDateTime.getDisplayDate();

        Assert.assertNull(result);
    }

    @Test
    public void getDisplayDateFullObject1() {
        ServerDateTime serverDateTime = makeDateTime1();

        String result = serverDateTime.getDisplayDate();

        Assert.assertNotNull(result);
    }

    @Test
    public void getDisplayDateFullObject2() {
        ServerDateTime serverDateTime = makeDateTime2();

        String result = serverDateTime.getDisplayDate();

        Assert.assertNotNull(result);
    }

    @Test
    public void getDisplayDateFullObject3() {
        ServerDateTime serverDateTime = makeDateTime3();

        String result = serverDateTime.getDisplayDate();

        Assert.assertNotNull(result);
    }

    @Test
    public void getDisplayTimeBaseObject() {
        ServerDateTime serverDateTime = new ServerDateTime(null, null);

        String result = serverDateTime.getDisplayTime();

        Assert.assertNull(result);
    }

    @Test
    public void getDisplayTimeFullObject1() {
        ServerDateTime serverDateTime = makeDateTime1();

        String result = serverDateTime.getDisplayTime();

        Assert.assertNotNull(result);
    }

    @Test
    public void getDisplayTimeFullObject2() {
        ServerDateTime serverDateTime = makeDateTime2();

        String result = serverDateTime.getDisplayTime();

        Assert.assertNull(result);
    }

    @Test
    public void getDisplayTimeFullObject3() {
        ServerDateTime serverDateTime = makeDateTime3();

        String result = serverDateTime.getDisplayTime();

        Assert.assertNotNull(result);
    }

    @Test(expected = NullPointerException.class)
    public void getJavaDateTestBaseObject() throws ParseException {
        ServerDateTime serverDateTime = new ServerDateTime(null, null);

        Date result = serverDateTime.getJavaDate();

        Assert.assertNull(result);
    }

    @Test(expected = ParseException.class)
    public void getJavaDateTestJunkObject() throws ParseException {
        ServerDateTime serverDateTime = new ServerDateTime("junk", "junk");

        Date result = serverDateTime.getJavaDate();

        Assert.assertNull(result);
    }

    @Test
    public void getJavaDateTestFullObject1() throws ParseException {
        ServerDateTime serverDateTime = makeDateTime1();

        Date result = serverDateTime.getJavaDate();

        Assert.assertNotNull(result);
    }

    @Test
    public void getJavaDateTestFullObject2() throws ParseException {
        ServerDateTime serverDateTime = makeDateTime2();

        Date result = serverDateTime.getJavaDate();

        Assert.assertNotNull(result);
    }

    @Test
    public void getJavaDateTestFullObject3() throws ParseException {
        ServerDateTime serverDateTime = makeDateTime3();

        Date result = serverDateTime.getJavaDate();

        Assert.assertNotNull(result);
    }

}
