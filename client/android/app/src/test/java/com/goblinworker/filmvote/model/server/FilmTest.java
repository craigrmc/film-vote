package com.goblinworker.filmvote.model.server;

import org.junit.Assert;
import org.junit.Test;

public class FilmTest {

    @Test
    public void getShowTimeDisplayTestNewFilm() {
        Film film = new Film();
        String result = film.getShowTimes();

        Assert.assertEquals("", result);
    }

    @Test
    public void getShowTimeDisplayTestFullFilm() {
        Film film = makeFilm();
        String result = film.getShowTimes();

        Assert.assertEquals("12:00 PM, 3:00 PM, 5:00 PM, 7:00 PM, 10:00 PM", result);
    }

    private Film makeFilm() {

        Film film = new Film();
        film.setName("The Matrix");
        film.setDescription("Kickass film.");
        film.setGenre("Sci-Fi");
        film.setLength("00 min");

        film.getDirectorList().add("director");

        film.getWriterList().add("writer");

        film.getActorList().add("actor");

        film.getShowTimeList().add("12:00 PM");
        film.getShowTimeList().add("3:00 PM");
        film.getShowTimeList().add("5:00 PM");
        film.getShowTimeList().add("7:00 PM");
        film.getShowTimeList().add("10:00 PM");

        return film;
    }

}
