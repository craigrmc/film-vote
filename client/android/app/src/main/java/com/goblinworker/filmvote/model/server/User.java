package com.goblinworker.filmvote.model.server;

/**
 * Object for an entity that can sign in and cast votes.
 */
public class User {

    private String clubName;
    private String userName;

    /**
     * Is the user valid enough to vote.
     *
     * @return boolean
     */
    public boolean isValid() {

        if (clubName == null || clubName.isEmpty()) {
            return false;
        }

        if (userName == null || userName.isEmpty()) {
            return false;
        }

        return true;
    }

    // Getter / Setter

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
