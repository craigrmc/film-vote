package com.goblinworker.filmvote.model.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Object that holds the collection of user's that will vote on a film.
 */
public class Club {

    private String name;

    private final Map<String, User> userMap = new HashMap<>();
    private final Map<String, Theater> theaterMap = new HashMap<>();
    private final Map<String, VoteDate> voteDateMap = new HashMap<>();

    // Getter / Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Map<String, Theater> getTheaterMap() {
        return theaterMap;
    }

    public Map<String, VoteDate> getVoteDateMap() {
        return voteDateMap;
    }

}
