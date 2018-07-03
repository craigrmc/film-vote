package com.goblinworker.filmvote.model;

import org.junit.Assert;
import org.junit.Test;

public class VoteTest {

    @Test
    public void testIsValidNew() {
        Vote vote = new Vote();
        boolean result = vote.isValid();
        Assert.assertFalse(result);
    }

    @Test
    public void testIsValidJunkDate() {
        Vote vote = makeVote();
        vote.setDate("junk");
        boolean result = vote.isValid();
        Assert.assertFalse(result);
    }

    @Test
    public void testIsValidJunkTime() {
        Vote vote = makeVote();
        vote.setTime("junk");
        boolean result = vote.isValid();
        Assert.assertFalse(result);
    }

    @Test
    public void testIsValid() {
        Vote vote = makeVote();
        boolean result = vote.isValid();
        Assert.assertTrue(result);
    }

    private Vote makeVote() {

        Vote vote = new Vote();
        vote.setDate("2000-01-01");
        vote.setTime("19:15:00.000-0500");
        vote.setFilm("Blade Runner");
        vote.setTheater("The Grand 16");

        return vote;
    }

}
