package com.goblinworker.filmvote.model.server;

import org.junit.Assert;
import org.junit.Test;

public class VoteTest {

    @Test
    public void getDayTestBaseVote() {
        Vote vote = new Vote();

        String result = vote.getDay();

        Assert.assertNull(result);
    }

    @Test
    public void getDayTestFullVote() {
        Vote vote = makeVote();

        String result = vote.getDay();

        Assert.assertNotNull(result);
    }

    @Test
    public void getDayLegacyTestBaseVote() {
        Vote vote = new Vote();

        String result = vote.getDayLegacy();

        Assert.assertNull(result);
    }

    @Test
    public void getDayLegacyTestFullVote() {
        Vote vote = makeVote();

        String result = vote.getDayLegacy();

        Assert.assertNotNull(result);
    }

    @Test
    public void getLocalTimeTestBaseVote() {
        Vote vote = new Vote();

        String result = vote.getLocalTime();

        Assert.assertNull(result);
    }

    @Test
    public void getLocalTimeTestFullVote() {
        Vote vote = makeVote();

        String result = vote.getLocalTime();

        Assert.assertNotNull(result);
    }

    @Test
    public void getLocalTimeLegacyTestBaseVote() {
        Vote vote = new Vote();

        String result = vote.getLocalTimeLegacy();

        Assert.assertNull(result);
    }

    @Test
    public void getLocalTimeLegacyTestFullVote() {
        Vote vote = makeVote();

        String result = vote.getLocalTimeLegacy();

        Assert.assertNotNull(result);
    }

    private Vote makeVote() {

        Vote vote = new Vote();
        vote.setTally(42);
        vote.setDate("2000-01-01");
        vote.setTheater("The Grand 16");
        vote.setFilm("The Matrix");
        vote.setTime("12:30:00");

        return vote;
    }

}
