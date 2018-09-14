package com.goblinworker.filmvote.model.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * Get all information as a string.
     *
     * @return String of Theater Info
     */
    public String getInfo() {


        StringBuilder builder = new StringBuilder();

        if (location != null) {
            builder.append(location);
            builder.append("\n");
        }

        if (phone != null) {
            builder.append(phone);
            builder.append("\n");
        }

        if (address != null) {
            builder.append(address);
            builder.append("\n");
        }

        if (city != null) {
            builder.append(city);
            builder.append("\n");
        }

        if (state != null) {
            builder.append(state);
            builder.append("\n");
        }

        if (zipcode != null) {
            builder.append(zipcode);
            builder.append("\n");
        }

        return builder.toString();
    }

    /**
     * Get film list for a specific date.
     *
     * @param date String yyyy-MM-dd
     * @return Film List
     */
    public List<Film> getFilmList(String date) {

        FilmDate filmDate = filmDateMap.get(date);
        if (filmDate == null) {
            return new ArrayList<>();
        }

        return filmDate.getFilmList();
    }

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
