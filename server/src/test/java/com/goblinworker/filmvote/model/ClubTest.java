package com.goblinworker.filmvote.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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

    @Test
    public void testAddVoteNullUser() {
        Vote vote = makeVote();
        Vote result = club.addVote(null, vote);
        Assert.assertNull(result);
    }

    @Test
    public void testAddVoteEmptyUser() {
        Vote vote = makeVote();
        Vote result = club.addVote("", vote);
        Assert.assertNull(result);
    }

    @Test
    public void testAddVoteNull() {
        Vote vote = null;
        Vote result = club.addVote("user1", vote);
        Assert.assertNull(result);
    }

    @Test
    public void testAddVoteEmpty() {
        Vote vote = new Vote();
        Vote result = club.addVote("user1", vote);
        Assert.assertNull(result);
    }

    @Test
    public void testAddVote() {
        Vote vote = makeVote();
        Vote result = club.addVote("user1", vote);
        Assert.assertNotNull(result);
    }

    @Test
    public void testAddVoteTwice() {
        Vote vote1 = makeVote();
        vote1.setTheater("The Grand 16");
        Vote result1 = club.addVote("user1", vote1);
        Assert.assertNotNull(result1);

        Vote vote2 = makeVote();
        vote2.setTheater("AMC");
        Vote result2 = club.addVote("user1", vote2);
        Assert.assertNotNull(result2);

        Vote vote = club.getVote("user1", vote2.getDate());
        Assert.assertEquals(vote2.getTheater(), vote.getTheater());
    }

    @Test
    public void testGetVoteNullUser() {
        Vote result = club.getVote(null, "2000-01-01");
        Assert.assertNull(result);
    }

    @Test
    public void testGetVoteEmptyUser() {
        Vote result = club.getVote("", "2000-01-01");
        Assert.assertNull(result);
    }

    @Test
    public void testGetVoteNullDate() {
        Vote result = club.getVote("user1", null);
        Assert.assertNull(result);
    }

    @Test
    public void testGetVoteEmptyDate() {
        Vote result = club.getVote("user1", "");
        Assert.assertNull(result);
    }

    @Test
    public void testGetVoteZeroVotes() {
        Vote result = club.getVote("user1", "2000-01-01");
        Assert.assertNull(result);
    }

    @Test
    public void testGetVoteOneVote() {
        Vote vote = club.addVote("user1", makeVote());

        Vote result = club.getVote("user1", vote.getDate());
        Assert.assertNotNull(result);
    }

    @Test
    public void testRemoveVoteNullUser() {
        Vote result = club.removeVote(null, "2000-01-01");
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveVoteEmptyUser() {
        Vote result = club.removeVote("", "2000-01-01");
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveVoteNullDate() {
        Vote result = club.removeVote("user1", null);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveVoteEmptyDate() {
        Vote result = club.removeVote("user1", "");
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveVoteZeroVotes() {
        Vote result = club.removeVote("user1", "2000-01-01");
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveVoteOneVote() {
        Vote vote = club.addVote("user1", makeVote());

        Vote result = club.removeVote("user1", vote.getDate());
        Assert.assertNotNull(result);
    }

    @Test
    @Ignore("method currently returns empty vote")
    public void testGetFilmVoteZeroVotes() {
        Vote result = club.getFilmVote();
        Assert.assertNull(result);
    }

    @Test
    @Ignore("method currently returns empty vote")
    public void testGetFilmVoteOneAncientRomanVote() {
        Vote vote = makeVote();
        vote.setDate("0235-01-01");
        club.addVote("ASeverus", vote);
        Vote result = club.getFilmVote();
        Assert.assertNull(result);
    }

    @Test
    public void testGetFilmVoteOneFutureVote() {
        Vote vote = makeVote();
        vote.setDate("2400-01-01");
        club.addVote("TKovacs", vote);
        Vote result = club.getFilmVote();
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetFilmVoteNullDate() {
        Vote result = club.getFilmVote(null);
        Assert.assertNull(result);
    }

    @Test
    public void testGetFilmVoteEmptyDate() {
        Vote result = club.getFilmVote("");
        Assert.assertNull(result);
    }

    @Test
    public void testGetFilmVoteNoVotesOnDate() {
        Vote result = club.getFilmVote("2000-01-01");
        Assert.assertNull(result);
    }

    @Test
    public void testGetFilmVoteOneVoteOnDate() {
        Vote vote = club.addVote("user1", makeVote());
        Vote result = club.getFilmVote(vote.getDate());
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetCurrentDate() {
        String result = club.getCurrentDate();
        Assert.assertNotNull(result);
    }

    private Theater makeTheater() {

        Theater theater = new Theater("The Grand 16");
        theater.setPhone("phone");
        theater.setZipcode("zipcode");
        theater.setCity("city");
        theater.setAddress("address");
        theater.setLocation("location");
        theater.setState("state");

        return theater;
    }

    private Vote makeVote() {

        Vote vote = new Vote();
        vote.setDate("2000-01-01");
        vote.setTime("19:30:00.000-0500");
        vote.setFilm("Fight Club");
        vote.setTheater("The Grand 16");

        return vote;
    }

}