package com.goblinworker.filmvote.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Object that holds the collection of user's that will vote on a film.
 */
public class Club {

    private final String name;

    private final Map<String, User> userMap = new HashMap<>();
    private final Map<String, Theater> theaterMap = new HashMap<>();

    /**
     * Constructor used for testing.
     */
    Club() {
        this(null);
    }

    /**
     * Constructor used to create a club.
     *
     * @param name String
     */
    public Club(String name) {
        this.name = name;
    }

    /**
     * Add a user to the club if new.
     * Returns a user if they're in the club.
     *
     * @param name String
     * @return User
     */
    public User addUser(String name) {

        if (name == null || name.isEmpty()) {
            return null;
        }

        if (userMap.containsKey(name)) {
            return null;
        }

        User user = new User(this.name, name);
        userMap.put(user.getUserName(), user);

        return user;
    }

    /**
     * Get a user in the club.
     * Returns a user if they're in the club.
     *
     * @param name String
     * @return User
     */
    public User getUser(String name) {
        return userMap.get(name);
    }

    /**
     * Remove a user from the club.
     * Returns a user if they're were removed from the club.
     *
     * @param name String
     * @return User
     */
    public User removeUser(String name) {
        return userMap.remove(name);
    }

    /**
     * Add a theater to the club.
     * Returns theater if it's added to the club.
     *
     * @param theater Theater
     * @return Theater
     */
    public Theater addTheater(Theater theater) {

        if (theater == null || theater.getName() == null) {
            return null;
        }

        if (theaterMap.containsKey(theater.getName())) {
            return null;
        }

        theaterMap.put(theater.getName(), theater);

        return theater;
    }

    /**
     * Get a theater if it's in the club.
     *
     * @param name String
     * @return Theater
     */
    public Theater getTheater(String name) {
        return theaterMap.get(name);
    }

    /**
     * Remove a theater from the club.
     * Returns the theater if it was removed.
     *
     * @param name String
     * @return Theater
     */
    public Theater removeTheater(String name) {
        return theaterMap.remove(name);
    }

    // Getter / Setter

    public String getName() {
        return name;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Map<String, Theater> getTheaterMap() {
        return theaterMap;
    }

}
