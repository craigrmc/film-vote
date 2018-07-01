package com.goblinworker.filmvote.model;

/**
 * Object for an entity that can sign in and cast votes.
 */
public class User {

    private final String clubName;
    private final String userName;

    /**
     * Constructor used for testing.
     */
    User() {
        this(null, null);
    }

    /**
     * Constructor used to create a user.
     *
     * @param club String
     * @param user String
     */
    public User(String club, String user) {
        this.clubName = club;
        this.userName = user;
    }

    // Getter / Setter

    public String getClubName() {
        return clubName;
    }

    public String getUserName() {
        return userName;
    }

}
