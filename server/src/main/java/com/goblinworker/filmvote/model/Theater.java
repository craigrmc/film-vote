package com.goblinworker.filmvote.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Object that holds info for a specific theater.
 */
public class Theater {

    private final String name;
    private String location;

    private final Map<String, FilmDate> filmDateMap = new HashMap<>();

    /**
     * Constructor used for testing.
     */
    Theater() {
        this(null);
    }

    /**
     * Constructor used to create a theater.
     *
     * @param name String
     */
    public Theater(String name) {
        this.name = name;
    }

    /**
     * Add the viewing date to the theater.
     *
     * @param date String
     * @return FilmDate
     */
    public FilmDate addDate(String date) {

        if (date == null || date.isEmpty()) {
            return null;
        }

        if (filmDateMap.containsKey(date)) {
            return null;
        }

        FilmDate filmDate = new FilmDate(date);
        filmDateMap.put(filmDate.getDate(), filmDate);

        return filmDate;
    }

    /**
     * Get the viewing date for the theater.
     *
     * @param date String
     * @return FilmDate
     */
    public FilmDate getDate(String date) {
        return filmDateMap.get(date);
    }

    /**
     * Remove the viewing date from the theater.
     *
     * @param date String
     * @return FilmDate
     */
    public FilmDate removeDate(String date) {
        return filmDateMap.remove(date);
    }

    // Getter / Setter

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, FilmDate> getFilmDateMap() {
        return filmDateMap;
    }

}
