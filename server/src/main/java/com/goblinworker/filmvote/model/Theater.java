package com.goblinworker.filmvote.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Object that holds info for a specific theater.
 */
public class Theater {

    private final String name;
    private String location;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String phone;

    private final Map<String, FilmDate> filmDateMap = new HashMap<>();

    /**
     * Constructor used for testing.
     */
    Theater() {
        this(null);
    }

    /**
     * Constructor used to create a theater.
     *
     * @param name String
     */
    public Theater(String name) {
        this.name = name;
    }

    /**
     * Add the viewing date to the theater.
     *
     * @param filmDate FilmDate
     * @return FilmDate
     */
    public FilmDate addFilmDate(FilmDate filmDate) {

        if (filmDate == null || !filmDate.isValid()) {
            return null;
        }

        if (filmDateMap.containsKey(filmDate.getDate())) {
            return null;
        }

        filmDateMap.put(filmDate.getDate(), filmDate);

        return filmDate;
    }

    /**
     * Get the viewing date for the theater.
     *
     * @param date String
     * @return FilmDate
     */
    public FilmDate getFilmDate(String date) {
        return filmDateMap.get(date);
    }

    /**
     * Remove the viewing date from the theater.
     *
     * @param date String
     * @return FilmDate
     */
    public FilmDate removeFilmDate(String date) {
        return filmDateMap.remove(date);
    }

    // Getter / Setter

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Map<String, FilmDate> getFilmDateMap() {
        return filmDateMap;
    }

}
