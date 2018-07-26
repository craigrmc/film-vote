package com.goblinworker.filmvote.model.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Object that holds info for a specific film.
 */
public class Film {

    private String name;
    private String description;
    private String length;
    private String genre;

    private final List<String> directorList = new ArrayList<>();
    private final List<String> writerList = new ArrayList<>();
    private final List<String> actorList = new ArrayList<>();
    private final List<String> showTimeList = new ArrayList<>();

    /**
     * Get the list of show times as a string.
     *
     * @return String
     */
    public String getShowTimes() {

        StringBuilder builder = new StringBuilder();

        Iterator<String> iterator = showTimeList.iterator();

        while (iterator.hasNext()) {
            builder.append(iterator.next());

            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    // Getter / Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
