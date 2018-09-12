package com.goblinworker.filmvote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Object that holds a vote for when and where to watch a film.
 */
public class Vote {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";

    private Integer tally;
    private String date;
    private String time;
    private String film;
    private String theater;

    /**
     * Constructor used for testing.
     */
    public Vote() {
        this(1, null);
    }

    /**
     * Constructor used to tally votes.
     *
     * @param tally Integer
     * @param date String
     */
    public Vote(Integer tally, String date) {
        this.tally = tally;
        this.date = date;
    }

    /**
     * Is the given object the same vote.
     * Does not compare the vote tally.
     *
     * @param object Object
     * @return boolean
     */
    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Vote)) {
            return false;
        }

        Vote vote = (Vote) object;

        return isEqual(date, vote.date) &&
                isEqual(time, vote.time) &&
                isEqual(film, vote.film) &&
                isEqual(theater, vote.theater);
    }

    /**
     * Compare strings.
     * TODO: There is probably already a util class for this.
     *
     * @param string1 String
     * @param string2 String
     * @return boolean
     */
    boolean isEqual(String string1, String string2) {

        if (string1 != null) {
            return string1.equals(string2);
        }

        return string2 == null;
    }

    /**
     * Is all voting data valid.
     * Returns false if any data is null or empty.
     * Returns false if date format is wrong.
     * Returns false if time format is wrong.
     *
     * @return boolean
     */
    @JsonIgnore
    public boolean isValid() {

        if (tally == null || tally < 0) {
            return false;
        }

        if (date == null || date.isEmpty()) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }

        if (time == null || time.isEmpty()) {
            return false;
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);

        try {
            timeFormat.parse(time);
        } catch (ParseException e) {
            return false;
        }

        if (film == null || film.isEmpty()) {
            return false;
        }

        if (theater == null || theater.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Increments the current tally of votes.
     */
    public void addTally() {
        if (tally != null) {
            tally++;
        }
    }

    // Getter / Setter

    public Integer getTally() {
        return tally;
    }

    public void setTally(Integer tally) {
        this.tally = tally;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

}
