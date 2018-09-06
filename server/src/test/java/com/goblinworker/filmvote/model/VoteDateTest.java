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
    public void testGetFilmVoteNoActivity() {
        Vote vote = voteDate.getFilmVote();
        Assert.assertNotNull(vote);
        Assert.assertNull(vote.getDate());
        Assert.assertNull(vote.getTime());
        Assert.assertNull(vote.getFilm());
        Assert.assertNull(vote.getTheater());
    }

    @Test
    public void testGetFilmVoteTheMatrixByOne() {
        Vote vote1 = makeVote();
        vote1.setFilm(THE_MATRIX);
        voteDate.addVote("user1", vote1);

        Vote vote = voteDate.getFilmVote();
        Assert.assertEquals(TODAY, vote.getDate());
        Assert.assertEquals(SEVEN_THIRTY_PM, vote.getTime());
        Assert.assertEquals(THE_MATRIX, vote.getFilm());
        Assert.assertEquals(THE_GRAND, vote.getTheater());
    }

    @Test
    public void testGetFilmVoteTied() {
        Vote vote1 = makeVote();
        vote1.setFilm(THE_MATRIX);
        voteDate.addVote("user1", vote1);

        Vote vote2 = makeVote();
        vote2.setFilm(BLADE_RUNNER);
        voteDate.addVote("user2", vote2);

        Vote vote = voteDate.getFilmVote();
        Assert.assertEquals(TODAY, vote.getDate());
        Assert.assertEquals(SEVEN_THIRTY_PM, vote.getTime());
        Assert.assertEquals(THE_MATRIX, vote.getFilm());
        Assert.assertEquals(THE_GRAND, vote.getTheater());
    }

    @Test
    public void testGetFilmVoteBladeRunnerByTwo() {
        Vote vote1 = makeVote();
        vote1.setFilm(BLADE_RUNNER);
        voteDate.addVote("user1", vote1);

        Vote vote2 = makeVote();
        vote2.setFilm(FIGHT_CLUB);
        voteDate.addVote("user2", vote2);

        Vote vote3 = makeVote();
        vote3.setFilm(BLADE_RUNNER);
        voteDate.addVote("user3", vote3);

        Vote vote = voteDate.getFilmVote();
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

    @Test
    public void testIsBeforeNullDate() {
        VoteDate voteDate = new VoteDate(null);
        boolean before = voteDate.isBefore(TOMORROW);
        Assert.assertFalse(before);
    }

    @Test
    public void testIsBeforeEmptyDate() {
        VoteDate voteDate = new VoteDate("");
        boolean before = voteDate.isBefore(TOMORROW);
        Assert.assertFalse(before);
    }

    @Test
    public void testIsBeforeNullMarkDate() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean before = voteDate.isBefore(null);
        Assert.assertFalse(before);
    }

    @Test
    public void testIsBeforeEmptyMarkDate() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean before = voteDate.isBefore("");
        Assert.assertFalse(before);
    }

    @Test
    public void testIsBeforeLastYear() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean before = voteDate.isBefore(LAST_YEAR);
        Assert.assertFalse(before);
    }

    @Test
    public void testIsBeforeNextYear() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean before = voteDate.isBefore(NEXT_YEAR);
        Assert.assertTrue(before);
    }

    @Test
    public void testIsBeforeLastMonth() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean before = voteDate.isBefore(LAST_MONTH);
        Assert.assertFalse(before);
    }

    @Test
    public void testIsBeforeNextMonth() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean before = voteDate.isBefore(NEXT_MONTH);
        Assert.assertTrue(before);
    }

    @Test
    public void testIsBeforeYesterday() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean before = voteDate.isBefore(YESTERDAY);
        Assert.assertFalse(before);
    }

    @Test
    public void testIsBeforeToday() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean before = voteDate.isBefore(TODAY);
        Assert.assertTrue(before);
    }

    @Test
    public void testIsBeforeTomorrow() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean before = voteDate.isBefore(TOMORROW);
        Assert.assertTrue(before);
    }

    @Test
    public void testIsAfterNullDate() {
        VoteDate voteDate = new VoteDate(null);
        boolean after = voteDate.isAfter(TODAY);
        Assert.assertFalse(after);
    }

    @Test
    public void testIsAfterEmptyDate() {
        VoteDate voteDate = new VoteDate("");
        boolean after = voteDate.isAfter(TODAY);
        Assert.assertFalse(after);
    }

    @Test
    public void testIsAfterNullMarkDate() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean after = voteDate.isAfter(null);
        Assert.assertFalse(after);
    }

    @Test
    public void testIsAfterEmptyMarkDate() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean after = voteDate.isAfter("");
        Assert.assertFalse(after);
    }

    @Test
    public void testIsAfterLastYear() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean after = voteDate.isAfter(LAST_YEAR);
        Assert.assertTrue(after);
    }

    @Test
    public void testIsAfterNextYear() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean after = voteDate.isAfter(NEXT_YEAR);
        Assert.assertFalse(after);
    }

    @Test
    public void testIsAfterLastMonth() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean after = voteDate.isAfter(LAST_MONTH);
        Assert.assertTrue(after);
    }

    @Test
    public void testIsAfterNextMonth() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean after = voteDate.isAfter(NEXT_MONTH);
        Assert.assertFalse(after);
    }

    @Test
    public void testIsAfterYesterday() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean after = voteDate.isAfter(YESTERDAY);
        Assert.assertTrue(after);
    }

    @Test
    public void testIsAfterToday() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean after = voteDate.isAfter(TODAY);
        Assert.assertTrue(after);
    }

    @Test
    public void testIsAfterTomorrow() {
        VoteDate voteDate = new VoteDate(TODAY);
        boolean after = voteDate.isAfter(TOMORROW);
        Assert.assertFalse(after);
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
