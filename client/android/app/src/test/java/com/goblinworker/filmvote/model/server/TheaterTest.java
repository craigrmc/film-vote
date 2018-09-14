package com.goblinworker.filmvote.model.server;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TheaterTest {

    @Test
    public void getInfoTestNewTheater() {
        Theater theater = new Theater();

        String result = theater.getInfo();
        Assert.assertEquals("", result);
    }

    @Test
    public void getInfoTestFullTheater() {
        Theater theater = makeTheater();

        String result = theater.getInfo();
        Assert.assertEquals("location\n" +
                "phone\n" +
                "address\n" +
                "city\n" +
                "state\n" +
                "zipcode\n", result);
    }

    @Test
    public void getFilmListTestNewTheaterNullDate() {
        Theater theater = new Theater();

        List<Film> result = theater.getFilmList(null);

        Assert.assertNotNull(result);
        Assert.assertEquals(0L, result.size());
    }

    @Test
    public void getFilmListTestNewTheaterEmptyDate() {
        Theater theater = new Theater();

        List<Film> result = theater.getFilmList("");

        Assert.assertNotNull(result);
        Assert.assertEquals(0L, result.size());
    }

    @Test
    public void getFilmListTestNewTheaterGoodDate() {
        Theater theater = new Theater();

        List<Film> result = theater.getFilmList("2000-01-01");

        Assert.assertNotNull(result);
        Assert.assertEquals(0L, result.size());
    }

    @Test
    public void getFilmListTestFullTheaterNullDate() {
        Theater theater = makeTheater();

        List<Film> result = theater.getFilmList(null);

        Assert.assertNotNull(result);
        Assert.assertEquals(0L, result.size());
    }

    @Test
    public void getFilmListTestFullTheaterEmptyDate() {
        Theater theater = makeTheater();

        List<Film> result = theater.getFilmList("");

        Assert.assertNotNull(result);
        Assert.assertEquals(0L, result.size());
    }

    @Test
    public void getFilmListTestFullTheaterGoodDate() {
        Theater theater = makeTheater();

        List<Film> result = theater.getFilmList("2000-01-01");

        Assert.assertNotNull(result);
        Assert.assertEquals(1L, result.size());
    }

    private Theater makeTheater() {

        FilmDate filmDate1 = makeFilmDate();

        Theater theater = new Theater();
        theater.setName("name");
        theater.setLocation("location");
        theater.setPhone("phone");
        theater.setAddress("address");
        theater.setCity("city");
        theater.setState("state");
        theater.setZipcode("zipcode");

        theater.getFilmDateMap().put(filmDate1.getDate(), filmDate1);

        return theater;
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
