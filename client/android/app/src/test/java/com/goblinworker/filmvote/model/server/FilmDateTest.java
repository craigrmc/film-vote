package com.goblinworker.filmvote.model.server;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FilmDateTest {

    @Test
    public void getFilmListTestNewFilmDate() {
        FilmDate filmDate = new FilmDate();

        List<Film> result = filmDate.getFilmList();

        Assert.assertNotNull(result);
        Assert.assertEquals(0L, result.size());
    }

    @Test
    public void getFilmListTestFullFilmDate() {
        FilmDate filmDate = makeFilmDate();

        List<Film> result = filmDate.getFilmList();

        Assert.assertNotNull(result);
        Assert.assertEquals(1L, result.size());
    }

    private FilmDate makeFilmDate() {

        Film film1 = makeFilm();

        FilmDate filmDate = new FilmDate();
        filmDate.setDate("2000-01-01");

        filmDate.getFilmMap().put(film1.getName(), film1);

        return filmDate;
    }

    private Film makeFilm() {

        Film film = new Film();
        film.setName("The Matrix");
        film.setGenre("Sci-Fi");
        film.setLength("90 min");
        film.setDescription("Description");

        return film;
    }

}
