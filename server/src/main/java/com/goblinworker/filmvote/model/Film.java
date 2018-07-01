package com.goblinworker.filmvote.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Object that holds info for a specific film.
 */
public class Film {

    private final String name;
    private String description;

    private final List<String> showTimeList = new ArrayList<>();

    /**
     * Constructor used for testing.
     */
    Film() {
        this(null);
    }

    /**
     * Constructor used to create a film.
     *
     * @param name String
     */
    public Film(String name) {
        this.name = name;
    }

    /**
     * Adds a show time to film if it's new.
     * Returns true if show time was added.
     *
     * @param time String
     * @return boolean
     */
    public boolean addShowTime(String time) {

        if (time == null || time.isEmpty()) {
            return false;
        }

        if (showTimeList.contains(time)) {
            return false;
        }

        return showTimeList.add(time);
    }

    // Getter / Setter

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getShowTimeList() {
        return showTimeList;
    }

}
