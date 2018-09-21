package com.goblinworker.filmvote.model.server;

/**
 * Object that holds a vote for when and where to watch a film.
 */
public class Vote {

    private Integer tally;
    private String date;
    private String time;
    private String film;
    private String theater;

    /**
     * Constructor used for testing.
     */
    public Vote() {
        this.tally = 1;
    }

    /**
     * Constructor used to create a vote.
     *
     * @param theater String
     * @param date    String yyyy-MM-dd
     * @param film    String
     * @param time    String
     */
    public Vote(String theater, String date, String film, String time) {
        this.tally = 1;
        this.theater = theater;
        this.date = date;
        this.film = film;
        this.time = time;
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
