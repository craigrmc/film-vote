package com.goblinworker.filmvote.model.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object that holds all the theaters / films / votes for a given day.
 */
public class FilmDate {

    private String date;

    private final Map<String, Film> filmMap = new HashMap<>();

    /**
     * Get film list from map.
     *
     * @return Film List
     */
    public List<Film> getFilmList() {
        return new ArrayList<>(filmMap.values());
    }

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
