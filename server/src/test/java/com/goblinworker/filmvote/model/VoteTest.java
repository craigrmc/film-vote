package com.goblinworker.filmvote.model;

import org.junit.Assert;
import org.junit.Test;

public class VoteTest {

    @Test
    public void testNewVoteEqualsNewVote() {
        Vote vote1 = new Vote();
        Vote vote2 = new Vote();

        boolean result = vote1.equals(vote2);
        Assert.assertTrue(result);
    }

    @Test
    public void testFullVoteEqualsNewVote() {
        Vote vote1 = makeVote();
        Vote vote2 = new Vote();

        boolean result = vote1.equals(vote2);
        Assert.assertFalse(result);
    }

    @Test
    public void testNewVoteEqualsFullVote() {
        Vote vote1 = new Vote();
        Vote vote2 = makeVote();

        boolean result = vote1.equals(vote2);
        Assert.assertFalse(result);
    }

    @Test
    public void testFullVoteEqualsNullVote() {
        Vote vote1 = makeVote();
        Vote vote2 = null;

        boolean result = vote1.equals(vote2);
        Assert.assertFalse(result);
    }

    @Test
    public void testFullVoteEqualsFullVote() {
        Vote vote1 = makeVote();
        Vote vote2 = makeVote();

        boolean result = vote1.equals(vote2);
        Assert.assertTrue(result);
    }

    @Test
    public void testIsEqualNulls() {
        Vote vote = new Vote();
        boolean result = vote.isEqual(null, null);
        Assert.assertTrue(result);
    }

    @Test
    public void testIsEqualFirstStringNull() {
        Vote vote = new Vote();
        boolean result = vote.isEqual(null, "string");
        Assert.assertFalse(result);
    }

    @Test
    public void testIsEqualFirstStringEmpty() {
        Vote vote = new Vote();
        boolean result = vote.isEqual("", "string");
        Assert.assertFalse(result);
    }

    @Test
    public void testIsEqualSecondStringNull() {
        Vote vote = new Vote();
        boolean result = vote.isEqual("string", null);
        Assert.assertFalse(result);
    }

    @Test
    public void testIsEqualSecondStringEmpty() {
        Vote vote = new Vote();
        boolean result = vote.isEqual("string", "");
        Assert.assertFalse(result);
    }

    @Test
    public void testIsEqualStrings() {
        Vote vote = new Vote();
        boolean result = vote.isEqual("string", "string");
        Assert.assertTrue(result);
    }

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
