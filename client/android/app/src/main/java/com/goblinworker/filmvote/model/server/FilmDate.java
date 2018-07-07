package com.goblinworker.filmvote.model.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Object that holds all the theaters / films / votes for a given day.
 */
public class FilmDate {

    private String date;

    private final Map<String, Film> filmMap = new HashMap<>();

    // Getter / Setter

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Film> getFilmMap() {
        return filmMap;
    }

}
