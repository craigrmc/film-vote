package com.goblinworker.filmvote.model;

import java.text.DateFormat;
import java.util.*;

/**
 * Object that holds the collection of user's that will vote on a film.
 */
public class Club {

    private final String name;

    private final Map<String, User> userMap = new HashMap<>();
    private final Map<String, Theater> theaterMap = new HashMap<>();
    private final Map<String, VoteDate> voteDateMap = new HashMap<>();

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

    /**
     * Add a user's vote to the club.
     * Returns the vote if added.
     *
     * @param userName String
     * @param vote     Vote
     * @return Vote
     */
    public Vote addVote(String userName, Vote vote) {

        if (vote == null || !vote.isValid()) {
            return null;
        }

        VoteDate voteDate = voteDateMap.get(vote.getDate());
        if (voteDate == null) {
            voteDate = new VoteDate(vote.getDate());
            voteDateMap.put(voteDate.getDate(), voteDate);
        }

        return voteDate.addVote(userName, vote);
    }

    /**
     * Get a user's vote from the club.
     *
     * @param userName String
     * @param date     String yyyy-MM-dd
     * @return Vote
     */
    public Vote getVote(String userName, String date) {

        VoteDate voteDate = voteDateMap.get(date);
        if (voteDate == null) {
            return null;
        }

        return voteDate.getVote(userName);
    }

    /**
     * Remove a user's vote from the club.
     *
     * @param userName String
     * @param date     String yyyy-MM-dd
     * @return Vote
     */
    public Vote removeVote(String userName, String date) {

        VoteDate voteDate = voteDateMap.get(date);
        if (voteDate == null) {
            return null;
        }

        return voteDate.removeVote(userName);
    }

    /**
     * Get the leading vote for the closest date with activity.
     * Returns null if no upcoming votes.
     *
     * @return Vote
     */
    public Vote getFilmVote() {

        Vote vote = null;

        String currentDate = getDateString(new Date());

        for (Map.Entry<String, VoteDate> voteDateEntry : voteDateMap.entrySet()) {
            VoteDate voteDate = voteDateEntry.getValue();
            if (voteDate.isAfter(currentDate)) {
                if (vote == null || voteDate.isBefore(vote.getDate())) {
                    vote = voteDate.getFilmVote();
                }
            }
        }

        return vote;
    }

    /**
     * Get the leading vote for a specific date.
     * Returns empty vote if none found.
     *
     * @param date String yyyy-MM-dd
     * @return Vote
     */
    public Vote getFilmVote(String date) {

        VoteDate voteDate = voteDateMap.get(date);
        if (voteDate == null) {
            return new Vote(0, date);
        }

        return voteDate.getFilmVote();
    }

    /**
     * Get the leading vote for a specific date range.
     *
     * @param startDate String yyyy-MM-dd
     * @param endDate   String yyyy-MM-dd
     * @return Vote List
     */
    public List<Vote> getFilmVoteList(String startDate, String endDate) {

        List<Vote> list = new ArrayList<>();

        Calendar startCalendar = getCalendar(startDate);
        if (startCalendar == null) {
            return list;
        }

        Calendar endCalendar = getCalendar(endDate);
        if (endCalendar == null) {
            return list;
        }

        while (startCalendar.before(endCalendar) || startCalendar.equals(endCalendar)) {
            String dateString = getDateString(startCalendar.getTime());
            VoteDate voteDate = voteDateMap.get(dateString);

            if (voteDate == null) {
                list.add(new Vote(0, dateString));
            } else {
                list.add(voteDate.getFilmVote());
            }

            startCalendar.add(Calendar.DATE, 1);
        }

        return list;
    }

    /**
     * Get a calendar with the server timezone / locale.
     * Returns null if string not valid.
     *
     * @param string String yyyy-MM-dd
     * @return Calendar
     */
    Calendar getCalendar(String string) {

        Date date = getDate(string);
        if (date == null) {
            return null;
        }

        return getCalendar(date);
    }

    /**
     * Get a calendar with the server timezone / locale.
     *
     * @param date Date
     * @return Calendar
     */
    Calendar getCalendar(Date date) {

        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance(ServerDateTime.TIME_ZONE, ServerDateTime.LOCALE);
        calendar.setTime(date);

        return calendar;
    }

    /**
     * Get the date by parsing a string.
     * Returns null if string not valid.
     *
     * @param string String yyyy-MM-dd
     * @return Date
     */
    Date getDate(String string) {

        DateFormat dateFormat = ServerDateTime.getDateFormat();

        Date date;
        try {
            date = dateFormat.parse(string);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }

    /**
     * Get the date as a string.
     *
     * @param date Date
     * @return String yyyy-MM-dd
     */
    String getDateString(Date date) {

        if (date == null) {
            return null;
        }

        DateFormat dateFormat = ServerDateTime.getDateFormat();

        return dateFormat.format(date);
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

    public Map<String, VoteDate> getVoteDateMap() {
        return voteDateMap;
    }

}
