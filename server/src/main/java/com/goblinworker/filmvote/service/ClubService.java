package com.goblinworker.filmvote.service;

import com.goblinworker.filmvote.model.Club;
import com.goblinworker.filmvote.model.Theater;
import com.goblinworker.filmvote.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service that manages user's sign in / out and club assignment.
 */
@Component
public class ClubService {

    private final Logger LOGGER = Logger.getLogger(ClubService.class.getSimpleName());

    private final Map<String, Club> clubMap = new HashMap<>();

    /**
     * Adds the club to the map if new.
     * Returns a club if it's in the map.
     *
     * @param clubName String
     * @return Club
     */
    public Club addClub(String clubName) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (clubMap.containsKey(clubName)) {
            throw new Exception("club already exists");
        }

        Club club = new Club(clubName);
        clubMap.put(club.getName(), club);

        LOGGER.info("added club: " + club.getName());
        return club;
    }

    /**
     * Returns a club if it's in the map.
     *
     * @param clubName String
     * @return Club
     */
    public Club getClub(String clubName) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (!clubMap.containsKey(clubName)) {
            throw new Exception("club does not exist");
        }

        return clubMap.get(clubName);
    }

    /**
     * Adds a user to the club if they're new.
     * Returns a user if they're in the club.
     *
     * @param clubName String
     * @param userName String
     * @return User
     */
    public User addUser(String clubName, String userName) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (userName == null || userName.isEmpty()) {
            throw new Exception("user name invalid");
        }

        Club club = clubMap.get(clubName);
        if (club == null) {
            throw new Exception("club does not exist");
        }

        User user = club.addUser(userName);
        if (user == null) {
            throw new Exception("user not added");
        }

        LOGGER.info("added user: " + userName);
        return user;
    }

    /**
     * Returns a user if they're in the club.
     *
     * @param clubName String
     * @param userName String
     * @return User
     */
    public User getUser(String clubName, String userName) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (userName == null || userName.isEmpty()) {
            throw new Exception("user name invalid");
        }

        Club club = clubMap.get(clubName);
        if (club == null) {
            throw new Exception("club does not exist");
        }

        User user = club.getUser(userName);
        if (user == null) {
            throw new Exception("user does not exist");
        }

        return user;
    }

    public Theater addTheater() {
        // TODO: finish him!!!
        return null;
    }

    public Theater getTheater() {
        // TODO: finish him!!!
        return null;
    }

    public Theater removeTheater() {
        // TODO: finish him!!!
        return null;
    }

    /**
     * Returns a list of theaters in the club.
     *
     * @param clubName String
     * @return List<String>
     */
    public List<String> getTheaterList(String clubName) {
        // TODO: finish him!!!
        return new ArrayList<>();
    }

    /**
     * Is the date valid.
     *
     * @return boolean
     */
    boolean isValid(String date) {
        // TODO: finish him!!!
        return true;
    }

}
