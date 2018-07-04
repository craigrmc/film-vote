package com.goblinworker.filmvote.service;

import com.goblinworker.filmvote.model.Club;
import com.goblinworker.filmvote.model.Theater;
import com.goblinworker.filmvote.model.User;
import com.goblinworker.filmvote.model.Vote;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
     * Remove a club if it's in the map.
     * Returns a club if it's removed.
     *
     * @param clubName String
     * @return Club
     */
    public Club removeClub(String clubName) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (!clubMap.containsKey(clubName)) {
            throw new Exception("club does not exist");
        }

        LOGGER.info("removed club: " + clubName);
        return clubMap.remove(clubName);
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

    /**
     * Remove a user from the club.
     * Returns user that was removed.
     *
     * @param clubName String
     * @param userName String
     * @return User
     */
    public User removeUser(String clubName, String userName) throws Exception {
        // TODO: finish him!!!
        return null;
    }

    /**
     * Add a theater to the club.
     * Returns a theater if it's added.
     *
     * @param clubName String
     * @param theater  Theater
     * @return Theater
     */
    public Theater addTheater(String clubName, Theater theater) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (theater == null || !theater.isValid()) {
            throw new Exception("theater invalid");
        }

        Club club = clubMap.get(clubName);
        if (club == null) {
            throw new Exception("club does not exist");
        }

        LOGGER.info("added theater: " + theater.getName());
        return club.addTheater(theater);
    }

    /**
     * Get a theater in a club.
     *
     * @param clubName    String
     * @param theaterName String
     * @return Theater
     */
    public Theater getTheater(String clubName, String theaterName) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (theaterName == null || theaterName.isEmpty()) {
            throw new Exception("theater name invalid");
        }

        Club club = clubMap.get(clubName);
        if (club == null) {
            throw new Exception("club does not exist");
        }

        return club.getTheater(theaterName);
    }

    /**
     * Remove a theater from the club.
     * Return theater that was removed.
     *
     * @param clubName    String
     * @param theaterName String
     * @return Theater
     */
    public Theater removeTheater(String clubName, String theaterName) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (theaterName == null || theaterName.isEmpty()) {
            throw new Exception("theater name invalid");
        }

        Club club = clubMap.get(clubName);
        if (club == null) {
            throw new Exception("club does not exist");
        }

        LOGGER.info("removed theater: " + theaterName);
        return club.removeTheater(theaterName);
    }

    /**
     * Get a map of theaters in the user's club.
     *
     * @param clubName String
     * @return Theater Map
     */
    public Map<String, Theater> getTheaterMap(String clubName) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        Club club = clubMap.get(clubName);
        if (club == null) {
            throw new Exception("club invalid");
        }

        return club.getTheaterMap();
    }

    /**
     * Add a user vote for a film.
     * Returns the Vote if it's added to the club.
     *
     * @param clubName String
     * @param userName String
     * @param vote     Vote
     * @return Vote
     */
    public Vote addVote(String clubName, String userName, Vote vote) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (userName == null || userName.isEmpty()) {
            throw new Exception("user name invalid");
        }

        if (vote == null || !vote.isValid()) {
            throw new Exception("vote invalid");
        }

        Club club = clubMap.get(clubName);
        if (club == null) {
            throw new Exception("club does not exist");
        }

        LOGGER.info("added vote: " + clubName + " - " + userName);
        return club.addVote(userName, vote);
    }

    /**
     * Get the leading vote for the closest date with activity.
     *
     * @param clubName String
     * @return Vote
     */
    public Vote getFilmVote(String clubName) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        Club club = clubMap.get(clubName);
        if (club == null) {
            throw new Exception("club does not exist");
        }

        return club.getFilmVote();
    }

    /**
     * Get the leading vote for a specific date.
     *
     * @param clubName String
     * @param date     String yyyy-MM-dd
     * @return Vote
     */
    public Vote getFilmVote(String clubName, String date) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (date == null || date.isEmpty()) {
            throw new Exception("date invalid");
        }

        Club club = clubMap.get(clubName);
        if (club == null) {
            throw new Exception("club does not exist");
        }

        return club.getFilmVote(date);
    }

    /**
     * Remove a user vote from the club on a specific date.
     * Returns the vote that was removed.
     *
     * @param clubName String
     * @param userName String
     * @param date     String yyyy-MM-dd
     * @return Vote
     */
    public Vote removeFilmVote(String clubName, String userName, String date) throws Exception {

        if (clubName == null || clubName.isEmpty()) {
            throw new Exception("club name invalid");
        }

        if (userName == null || userName.isEmpty()) {
            throw new Exception("user name invalid");
        }

        if (date == null || date.isEmpty()) {
            throw new Exception("date invalid");
        }

        Club club = clubMap.get(clubName);
        if (club == null) {
            throw new Exception("club does not exist");
        }

        LOGGER.info("removed vote: " + clubName + " - " + userName + " - " + date);
        return club.removeVote(userName, date);
    }

}
