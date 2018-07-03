package com.goblinworker.filmvote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class VoteDate {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

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

    /**
     * Add a user's vote to the date.
     * Returns false if vote not added.
     *
     * @param user User
     * @param vote Vote
     * @return boolean
     */
    public boolean addVote(User user, Vote vote) {

        if (user == null || user.getUserName() == null) {
            return false;
        }

        if (vote == null || !vote.isValid()) {
            return false;
        }

        // user can change their vote
        voteMap.put(user.getUserName(), vote);
        return true;
    }

    /**
     * Is the date valid.
     * Returns false if date is missing or wrong format.
     *
     * @return boolean
     */
    @JsonIgnore
    public boolean isValid() {

        if (date == null || date.isEmpty()) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }

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
