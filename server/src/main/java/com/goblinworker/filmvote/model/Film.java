package com.goblinworker.filmvote.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Object that holds info for a specific film.
 */
public class Film {

    private final String name;
    private String description;
    private String length;
    private String genre;

    private final List<String> directorList = new ArrayList<>();
    private final List<String> writerList = new ArrayList<>();
    private final List<String> actorList = new ArrayList<>();
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

        DateFormat timeFormat = ServerDateTime.getTimeFormat();

        try {
            timeFormat.parse(time);
        } catch (Exception e) {
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<String> getDirectorList() {
        return directorList;
    }

    public List<String> getWriterList() {
        return writerList;
    }

    public List<String> getActorList() {
        return actorList;
    }

    public List<String> getShowTimeList() {
        return showTimeList;
    }

}
