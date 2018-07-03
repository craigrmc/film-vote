package com.goblinworker.filmvote.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ClubTest {

    @InjectMocks
    private Club club;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUserNullName() {
        String name = null;
        User result = club.addUser(name);
        Assert.assertNull(result);
    }

    @Test
    public void testAddUserEmptyName() {
        String name = "";
        User result = club.addUser(name);
        Assert.assertNull(result);
    }

    @Test
    public void testAddUserGoodName() {
        String name = "name";
        User result = club.addUser(name);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetUserNullName() {
        User result = club.getUser(null);
        Assert.assertNull(result);
    }

    @Test
    public void testGetUserEmptyName() {
        User result = club.getUser("");
        Assert.assertNull(result);
    }

    @Test
    public void testGetUserDoesNotExist() {
        String name = "name";
        User result = club.getUser(name);
        Assert.assertNull(result);
    }

    @Test
    public void testGetUserGoodName() {
        String name = "name";
        club.addUser(name);
        User result = club.getUser(name);
        Assert.assertNotNull(result);
    }

    @Test
    public void testRemoveUserNullName() {
        String name = null;
        User result = club.removeUser(name);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveUserEmptyName() {
        String name = "";
        User result = club.removeUser(name);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveUserDoesNotExist() {
        String name = "name";
        User result = club.removeUser(name);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveUserGoodName() {
        String name = "name";
        club.addUser(name);
        User result = club.removeUser(name);
        Assert.assertNotNull(result);
    }

    @Test
    public void testAddTheaterNull() {
        Theater theater = null;
        Theater result = club.addTheater(theater);
        Assert.assertNull(result);
    }

    @Test
    public void testAddTheaterEmpty() {
        Theater theater = new Theater();
        Theater result = club.addTheater(theater);
        Assert.assertNull(result);
    }

    @Test
    public void testAddTheaterFull() {
        Theater theater = makeTheater();
        Theater result = club.addTheater(theater);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetTheaterNullName() {
        String name = null;
        Theater result = club.getTheater(name);
        Assert.assertNull(result);
    }

    @Test
    public void testGetTheaterEmptyName() {
        String name = "";
        Theater result = club.getTheater(name);
        Assert.assertNull(result);
    }

    @Test
    public void testGetTheaterDoesNotExist() {
        String name = "name";
        Theater result = club.getTheater(name);
        Assert.assertNull(result);
    }

    @Test
    public void testGetTheaterGoodName() {
        Theater theater = makeTheater();
        club.addTheater(theater);
        Theater result = club.getTheater(theater.getName());
        Assert.assertNotNull(result);
    }

    @Test
    public void testRemoveTheaterNullName() {
        String name = null;
        Theater result = club.removeTheater(name);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveTheaterEmptyName() {
        String name = "";
        Theater result = club.removeTheater(name);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveTheaterDoesNotExist() {
        String name = "name";
        Theater result = club.removeTheater(name);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveTheaterGoodName() {
        Theater theater = makeTheater();
        club.addTheater(theater);
        Theater result = club.removeTheater(theater.getName());
        Assert.assertNotNull(result);
    }

    private Theater makeTheater() {

        Theater theater = new Theater("name");
        theater.setPhone("phone");
        theater.setZipcode("zipcode");
        theater.setCity("city");
        theater.setAddress("address");
        theater.setLocation("location");
        theater.setState("state");

        return theater;
    }

}
