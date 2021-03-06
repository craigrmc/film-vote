package com.goblinworker.filmvote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.DateFormat;
import java.util.*;

/**
 * Object that holds all votes for a specific date.
 */
public class VoteDate {

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
     * Returns the vote if added.
     *
     * @param user String
     * @param vote Vote
     * @return Vote
     */
    public Vote addVote(String user, Vote vote) {

        if (user == null || user.isEmpty()) {
            return null;
        }

        if (vote == null || !vote.isValid()) {
            return null;
        }

        // user can change their vote
        voteMap.put(user, vote);
        return vote;
    }

    /**
     * Get the vote made by a specific user.
     *
     * @param user String
     * @return Vote
     */
    public Vote getVote(String user) {
        return voteMap.get(user);
    }

    /**
     * Remove the vote made by a specific user.
     * Returns the vote that was removed.
     *
     * @param user String
     * @return Vote
     */
    public Vote removeVote(String user) {
        return voteMap.remove(user);
    }

    /**
     * Get the leading vote.
     * Returns an empty vote if none found.
     *
     * @return Vote
     */
    public Vote getFilmVote() {

        List<Vote> voteList = new ArrayList<>();

        // add vote tally
        for (Map.Entry<String, Vote> voteEntry : voteMap.entrySet()) {
            Vote vote = voteEntry.getValue();
            int voteIndex = voteList.indexOf(vote);
            if (0 <= voteIndex) {
                voteList.get(voteIndex).addTally();
            } else {
                voteList.add(vote);
            }
        }

        Vote leadingVote = new Vote(0, date);

        // get leading vote
        for (Vote vote : voteList) {
            if (leadingVote.getTally() < vote.getTally()) {
                leadingVote = vote;
            }
        }

        return leadingVote;
    }

    /**
     * Is the voting date between the start / end dates.
     *
     * @param start String yyyy-MM-dd
     * @param end   String yyyy-MM-dd
     * @return boolean
     */
    @JsonIgnore
    public boolean isBetween(String start, String end) {

        DateFormat dateFormat = ServerDateTime.getDateFormat();

        Date startDate;
        try {
            startDate = dateFormat.parse(start);
        } catch (Exception e) {
            return false;
        }

        Date endDate;
        try {
            endDate = dateFormat.parse(end);
        } catch (Exception e) {
            return false;
        }

        Date voteDate;
        try {
            voteDate = dateFormat.parse(date);
        } catch (Exception e) {
            return false;
        }

        return !voteDate.before(startDate) && !voteDate.after(endDate);
    }

    /**
     * Is the voting date on or before the specified date.
     *
     * @param when String yyyy-MM-dd
     * @return boolean
     */
    @JsonIgnore
    public boolean isBefore(String when) {

        DateFormat dateFormat = ServerDateTime.getDateFormat();

        Date whenDate;
        try {
            whenDate = dateFormat.parse(when);
        } catch (Exception e) {
            return false;
        }

        Date voteDate;
        try {
            voteDate = dateFormat.parse(date);
        } catch (Exception e) {
            return false;
        }

        return !voteDate.after(whenDate);
    }

    /**
     * Is the voting date on or after the specified date.
     *
     * @param when String yyyy-MM-dd
     * @return boolean
     */
    @JsonIgnore
    public boolean isAfter(String when) {

        DateFormat dateFormat = ServerDateTime.getDateFormat();

        Date whenDate;
        try {
            whenDate = dateFormat.parse(when);
        } catch (Exception e) {
            return false;
        }

        Date voteDate;
        try {
            voteDate = dateFormat.parse(date);
        } catch (Exception e) {
            return false;
        }

        return !voteDate.before(whenDate);
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

        DateFormat dateFormat = ServerDateTime.getDateFormat();

        try {
            dateFormat.parse(date);
        } catch (Exception e) {
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
