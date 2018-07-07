package com.goblinworker.filmvote.model.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Object that holds all votes for a specific date.
 */
public class VoteDate {

    private String date;

    private final Map<String, Vote> voteMap = new HashMap<>();

    // Getter / Setter

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Vote> getVoteMap() {
        return voteMap;
    }

}
