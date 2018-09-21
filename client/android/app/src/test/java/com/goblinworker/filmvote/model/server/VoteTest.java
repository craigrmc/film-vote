package com.goblinworker.filmvote.model.server;

public class VoteTest {

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
