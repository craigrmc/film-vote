package com.goblinworker.filmvote.model;

import java.util.HashMap;
import java.util.Map;

public class VoteDate {

    public static final String DATE_FORMAT = "yyyy/MM/dd";

    private final String date;

    private final Map<String, Vote> voteMap = new HashMap<>();

    /**
     * Constructor used for testing.
     */
    VoteDate() {
        this(null);
    }

    /**
     * Constructor used to create a voting date.
     *
     * @param date String
     */
    public VoteDate(String date) {
        this.date = date;
    }

    public boolean addVote(User user, Vote vote) {

        if (user == null || user.getUserName() == null) {
            return false;
        }

        if (vote == null) {
            return false;
        }

        // user can change their vote
        voteMap.put(user.getUserName(), vote);
        return true;
    }

    // Getter / Setter

    public String getDate() {
        return date;
    }

    public Map<String, Vote> getVoteMap() {
        return voteMap;
    }

}
