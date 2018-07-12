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
     * Constructor used to create a vote.
     */
    public Vote() {
        this.tally = 1;
    }

    /**
     * Get the Date to display on UI.
     *
     * @return String
     */
    public String getDisplayDate() {
        // TODO: finish him!!!
        return "Friday";
    }

    /**
     * Get the Time to display on UI.
     *
     * @return String
     */
    public String getDisplayTime() {
        // TODO: finish him!!!
        return "7:30 PM";
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
