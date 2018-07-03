package com.goblinworker.filmvote.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class VoteDateTest {

    @InjectMocks
    private VoteDate voteDate;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddVoteNullUser() {
        User user = null;
        Vote vote = makeVote();
        boolean result = voteDate.addVote(user, vote);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddVoteNewUser() {
        User user = new User();
        Vote vote = makeVote();
        boolean result = voteDate.addVote(user, vote);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddVoteNullVote() {
        User user = makeUser();
        Vote vote = null;
        boolean result = voteDate.addVote(user, vote);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddVoteNewVote() {
        User user = makeUser();
        Vote vote = new Vote();
        boolean result = voteDate.addVote(user, vote);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddVote() {
        User user = makeUser();
        Vote vote = makeVote();
        boolean result = voteDate.addVote(user, vote);
        Assert.assertTrue(result);
    }

    @Test
    public void testIsValidNullDate() {
        VoteDate voteDate = new VoteDate();
        boolean result = voteDate.isValid();
        Assert.assertFalse(result);
    }

    @Test
    public void testIsValidEmptyDate() {
        VoteDate voteDate = new VoteDate("");
        boolean result = voteDate.isValid();
        Assert.assertFalse(result);
    }

    @Test
    public void testIsValidJunkDate() {
        VoteDate voteDate = new VoteDate("junk");
        boolean result = voteDate.isValid();
        Assert.assertFalse(result);
    }

    @Test
    public void testIsValidGoodDate() {
        VoteDate voteDate = new VoteDate("2000-01-01");
        boolean result = voteDate.isValid();
        Assert.assertTrue(result);
    }

    private User makeUser() {
        return new User("club", "user");
    }

    private Vote makeVote() {

        Vote vote = new Vote();
        vote.setDate("2000-01-01");
        vote.setTime("19:30:00.000-0500");
        vote.setFilm("The Matrix");
        vote.setTheater("The Grand 16");

        return vote;
    }

}
