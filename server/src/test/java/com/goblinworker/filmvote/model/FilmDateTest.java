package com.goblinworker.filmvote.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class FilmDateTest {

    @InjectMocks
    private FilmDate filmDate;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddFilmNew() {
        Film film = new Film();
        Film result = filmDate.addFilm(film);
        Assert.assertNull(result);
    }

    @Test
    public void testAddFilmFull() {
        Film film = makeFilm();
        Film result = filmDate.addFilm(film);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetFilmNullName() {
        String name = null;
        Film result = filmDate.getFilm(name);
        Assert.assertNull(result);
    }

    @Test
    public void testGetFilmEmptyName() {
        String name = "";
        Film result = filmDate.getFilm(name);
        Assert.assertNull(result);
    }

    @Test
    public void testGetFilmDoesNotExist() {
        Film film = makeFilm();
        Film result = filmDate.getFilm(film.getName());
        Assert.assertNull(result);
    }

    @Test
    public void testGetFilmGoodName() {
        Film film = makeFilm();
        filmDate.addFilm(film);
        Film result = filmDate.getFilm(film.getName());
        Assert.assertNotNull(result);
    }

    @Test
    public void testRemoveFilmNullName() {
        String name = null;
        Film result = filmDate.removeFilm(name);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveFilmEmptyName() {
        String name = "";
        Film result = filmDate.removeFilm(name);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveFilmDoesNotExist() {
        Film film = makeFilm();
        Film result = filmDate.removeFilm(film.getName());
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveFilmGoodName() {
        Film film = makeFilm();
        filmDate.addFilm(film);
        Film result = filmDate.removeFilm(film.getName());
        Assert.assertNotNull(result);
    }

    @Test
    public void testIsValidNullDate() {
        FilmDate filmDate = new FilmDate();
        boolean result = filmDate.isValid();
        Assert.assertFalse(result);
    }

    @Test
    public void testIsValidEmptyDate() {
        FilmDate filmDate = new FilmDate("");
        boolean result = filmDate.isValid();
        Assert.assertFalse(result);
    }

    @Test
    public void testIsValidJunkDate() {
        FilmDate filmDate = new FilmDate("junk");
        boolean result = filmDate.isValid();
        Assert.assertFalse(result);
    }

    @Test
    public void testIsValidGoodDate() {
        FilmDate filmDate = new FilmDate("2000-01-01");
        boolean result = filmDate.isValid();
        Assert.assertTrue(result);
    }

    Film makeFilm() {

        Film film = new Film("name");
        film.setGenre("genre");
        film.setLength("length");
        film.setDescription("description");

        return film;
    }

}
