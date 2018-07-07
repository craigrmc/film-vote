package com.goblinworker.filmvote.model.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Object that holds info for a specific theater.
 */
public class Theater {

    private String name;
    private String location;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String phone;

    private final Map<String, FilmDate> filmDateMap = new HashMap<>();

    // Getter / Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
