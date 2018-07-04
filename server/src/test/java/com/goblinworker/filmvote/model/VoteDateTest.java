package com.goblinworker.filmvote.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class VoteDateTest {

    // Dates
    private final String LAST_YEAR = "1999-02-02";
    private final String LAST_MONTH = "2000-01-02";
    private final String YESTERDAY = "2000-02-01";
    private final String TODAY = "2000-02-02";
    private final String TOMORROW = "2000-02-03";
    private final String NEXT_MONTH = "2000-03-02";
    private final String NEXT_YEAR = "2001-02-02";

    // Times
    private final String SEVEN_THIRTY_PM = "19:30:00.000-0500";

    // Films
    private final String BLADE_RUNNER = "Blade Runner";
    private final String FIGHT_CLUB = "Fight Club";
    private final String THE_MATRIX = "The Matrix";

    // Theaters
    private final String AMC = "AMC Panama City 10";
    private final String THE_GRAND = "The Grand 16";
    private final String MARTIN = "Martin Theatre";

    @InjectMocks
    private VoteDate voteDate;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddVoteNullUser() {
        Vote vote = makeVote();
        Vote result = voteDate.addVote(null, vote);
        Assert.assertNull(result);
    }

    @Test
    public void testAddVoteEmptyUser() {
        Vote vote = makeVote();
        Vote result = voteDate.addVote("", vote);
        Assert.assertNull(result);
    }

    @Test
    public void testAddVoteNullVote() {
        Vote vote = null;
        Vote result = voteDate.addVote("user1", vote);
        Assert.assertNull(result);
    }

    @Test
    public void testAddVoteNewVote() {
        Vote vote = new Vote();
        Vote result = voteDate.addVote("user1", vote);
        Assert.assertNull(result);
    }

    @Test
    public void testAddVote() {
        Vote vote = makeVote();
        Vote result = voteDate.addVote("user1", vote);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetVoteTheMatrixByOne() {
        Vote vote1 = makeVote();
        vote1.setFilm(THE_MATRIX);
        voteDate.addVote("user1", vote1);

        Vote vote = voteDate.getVote();
        Assert.assertEquals(TODAY, vote.getDate());
        Assert.assertEquals(SEVEN_THIRTY_PM, vote.getTime());
        Assert.assertEquals(THE_MATRIX, vote.getFilm());
        Assert.assertEquals(THE_GRAND, vote.getTheater());
    }

    @Test
    public void testGetVoteTied() {
        Vote vote1 = makeVote();
        vote1.setFilm(THE_MATRIX);
        voteDate.addVote("user1", vote1);

        Vote vote2 = makeVote();
        vote2.setFilm(BLADE_RUNNER);
        voteDate.addVote("user2", vote2);

        Vote vote = voteDate.getVote();
        Assert.assertEquals(TODAY, vote.getDate());
        Assert.assertEquals(SEVEN_THIRTY_PM, vote.getTime());
        Assert.assertEquals(THE_MATRIX, vote.getFilm());
        Assert.assertEquals(THE_GRAND, vote.getTheater());
    }

    @Test
    public void testGetVoteBladeRunnerByTwo() {
        Vote vote1 = makeVote();
        vote1.setFilm(BLADE_RUNNER);
        voteDate.addVote("user1", vote1);

        Vote vote2 = makeVote();
        vote2.setFilm(FIGHT_CLUB);
        voteDate.addVote("user2", vote2);

        Vote vote3 = makeVote();
        vote3.setFilm(BLADE_RUNNER);
        voteDate.addVote("user3", vote3);

        Vote vote = voteDate.getVote();
        Assert.assertEquals(TODAY, vote.getDate());
        Assert.assertEquals(SEVEN_THIRTY_PM, vote.getTime());
        Assert.assertEquals(BLADE_RUNNER, vote.getFilm());
        Assert.assertEquals(THE_GRAND, vote.getTheater());
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
        VoteDate voteDate = new VoteDate(TODAY);
        boolean result = voteDate.isValid();
        Assert.assertTrue(result);
    }

    @Test
    public void testIsBetweenNullDate() {
        VoteDate voteDate = new VoteDate(null);
        boolean between = voteDate.isBetween(YESTERDAY, TOMORROW);
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenEmptyDate() {
        VoteDate voteDate = new VoteDate("");
        boolean between = voteDate.isBetween(YESTERDAY, TOMORROW);
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenNullStartDate() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean between = voteDate.isBetween(null, TOMORROW);
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenEmptyStartDate() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean between = voteDate.isBetween("", TOMORROW);
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenNullEndDate() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean between = voteDate.isBetween(YESTERDAY, null);
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenEmptyEndDate() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean between = voteDate.isBetween(YESTERDAY, "");
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenLastYear() {
        VoteDate voteDate = new VoteDate(LAST_YEAR);
        boolean between = voteDate.isBetween(YESTERDAY, TOMORROW);
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenNextYear() {
        VoteDate voteDate = new VoteDate(NEXT_YEAR);
        boolean between = voteDate.isBetween(YESTERDAY, TOMORROW);
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenLastMonth() {
        VoteDate voteDate = new VoteDate(LAST_MONTH);
        boolean between = voteDate.isBetween(YESTERDAY, TOMORROW);
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenNextMonth() {
        VoteDate voteDate = new VoteDate(NEXT_MONTH);
        boolean between = voteDate.isBetween(YESTERDAY, TOMORROW);
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenTimeWarp() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean between = voteDate.isBetween(TOMORROW, YESTERDAY);
        Assert.assertFalse(between);
    }

    @Test
    public void testIsBetweenToday() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean between = voteDate.isBetween(YESTERDAY, TOMORROW);
        Assert.assertTrue(between);
    }

    private User makeUser() {
        return new User("club", "user");
    }

    private Vote makeVote() {

        Vote vote = new Vote();
        vote.setDate(TODAY);
        vote.setTime(SEVEN_THIRTY_PM);
        vote.setFilm(THE_MATRIX);
        vote.setTheater(THE_GRAND);

        return vote;
    }

}
