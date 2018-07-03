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

    private String date;
    private String time;
    private String film;
    private String theater;

    /**
     * Constructor used to create a vote.
     */
    public Vote() {
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

    // Getter / Setter

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
