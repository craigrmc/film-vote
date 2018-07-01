package com.goblinworker.filmvote.model;

/**
 * Object that holds a vote for when and where to watch a film.
 */
public class Vote {

    // TODO: make date final???
    private String date;
    private String time;
    private String film;
    private String theater;

    /**
     * Constructor used to create a vote.
     */
    public Vote() {
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
