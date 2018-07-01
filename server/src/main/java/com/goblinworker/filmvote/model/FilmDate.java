package com.goblinworker.filmvote.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Object that holds all the theaters / films / votes for a given day.
 */
public class FilmDate {

    public static final String DATE_FORMAT = "yyyy/MM/dd";

    private final String date;

    private final Map<String, Film> filmMap = new HashMap<>();

    /**
     * Constructor used for testing.
     */
    FilmDate() {
        this(null);
    }

    /**
     * Constructor used to create a voting date.
     *
     * @param date String
     */
    public FilmDate(String date) {
        this.date = date;
    }

    /**
     * Add a film to the date if it's new.
     * Returns a film if it's in the date.
     *
     * @param film Film
     * @return Film
     */
    public Film addFilm(Film film) {

        if (film == null || film.getName() == null) {
            return null;
        }

        if (filmMap.containsKey(film.getName())) {
            return filmMap.get(film.getName());
        }

        return filmMap.put(film.getName(), film);
    }

    /**
     * Get a film if it's in the date.
     *
     * @param name String
     * @return Film
     */
    public Film getFilm(String name) {
        return filmMap.get(name);
    }

    /**
     * Remove a film from the date.
     * Returns a film if it's removed.
     *
     * @param name String
     * @return Film
     */
    public Film removeFilm(String name) {
        return filmMap.remove(name);
    }

    // Getter / Setter

    public String getDate() {
        return date;
    }

    public Map<String, Film> getFilmMap() {
        return filmMap;
    }

}
