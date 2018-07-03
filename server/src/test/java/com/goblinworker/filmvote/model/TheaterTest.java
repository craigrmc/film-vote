package com.goblinworker.filmvote.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class TheaterTest {

    @InjectMocks
    Theater theater;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddFilmDateNull() {
        FilmDate filmDate = null;
        FilmDate result = theater.addFilmDate(filmDate);
        Assert.assertNull(result);
    }

    @Test
    public void testAddFilmDateNew() {
        FilmDate filmDate = new FilmDate();
        FilmDate result = theater.addFilmDate(filmDate);
        Assert.assertNull(result);
    }

    @Test
    public void testAddFilmDateFull() {
        FilmDate filmDate = makeFilmDate();
        FilmDate result = theater.addFilmDate(filmDate);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetFilmDateNull() {
        String date = null;
        FilmDate result = theater.getFilmDate(date);
        Assert.assertNull(result);
    }

    @Test
    public void testGetFilmDateEmpty() {
        String date = "";
        FilmDate result = theater.getFilmDate(date);
        Assert.assertNull(result);
    }

    @Test
    public void testGetFilmDateDoesNotExist() {
        FilmDate filmDate = makeFilmDate();
        FilmDate result = theater.getFilmDate(filmDate.getDate());
        Assert.assertNull(result);
    }

    @Test
    public void testGetFilmDate() {
        FilmDate filmDate = makeFilmDate();
        theater.addFilmDate(filmDate);
        FilmDate result = theater.getFilmDate(filmDate.getDate());
        Assert.assertNotNull(result);
    }

    @Test
    public void testRemoveFilmDateNull() {
        String date = null;
        FilmDate result = theater.removeFilmDate(date);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveFilmDateNullEmpty() {
        String date = "";
        FilmDate result = theater.removeFilmDate(date);
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveFilmDateDoesNotExist() {
        FilmDate filmDate = makeFilmDate();
        FilmDate result = theater.removeFilmDate(filmDate.getDate());
        Assert.assertNull(result);
    }

    @Test
    public void testRemoveFilmDate() {
        FilmDate filmDate = makeFilmDate();
        theater.addFilmDate(filmDate);
        FilmDate result = theater.removeFilmDate(filmDate.getDate());
        Assert.assertNotNull(result);
    }

    private FilmDate makeFilmDate() {
        return new FilmDate("2000-01-01");
    }

}
