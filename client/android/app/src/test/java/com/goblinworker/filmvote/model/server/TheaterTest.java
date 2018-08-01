package com.goblinworker.filmvote.model.server;

import org.junit.Assert;
import org.junit.Test;

public class TheaterTest {

    @Test
    public void getInfoTestNewTheater() {
        Theater theater = new Theater();

        String result = theater.getInfo();
        Assert.assertEquals("", result);
    }

    @Test
    public void getInfoTestFullTheater() {
        Theater theater = makeTheater();

        String result = theater.getInfo();
        Assert.assertEquals("location\n" +
                "phone\n" +
                "address\n" +
                "city\n" +
                "state\n" +
                "zipcode\n", result);
    }

    private Theater makeTheater() {

        Theater theater = new Theater();
        theater.setName("name");
        theater.setLocation("location");
        theater.setPhone("phone");
        theater.setAddress("address");
        theater.setCity("city");
        theater.setState("state");
        theater.setZipcode("zipcode");

        return theater;
    }

}
