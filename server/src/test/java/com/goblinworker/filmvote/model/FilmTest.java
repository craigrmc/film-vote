package com.goblinworker.filmvote.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class FilmTest {

    @InjectMocks
    private Film film;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddShowTimeNull() {
        String showTime = null;
        boolean result = film.addShowTime(showTime);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddShowTimeEmpty() {
        String showTime = "";
        boolean result = film.addShowTime(showTime);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddShowTimeJunk() {
        String showTime = "junk";
        boolean result = film.addShowTime(showTime);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddShowTimeWrongFormat() {
        String showTime = "12:00";
        boolean result = film.addShowTime(showTime);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddShowTimeGood() {
        String showTime = "19:30:00.000+0000";
        boolean result = film.addShowTime(showTime);
        Assert.assertTrue(result);
    }

}
