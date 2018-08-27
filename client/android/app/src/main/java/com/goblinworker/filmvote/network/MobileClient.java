package com.goblinworker.filmvote.network;

import com.goblinworker.filmvote.model.server.Error;
import com.goblinworker.filmvote.model.server.Theater;
import com.goblinworker.filmvote.model.server.User;
import com.goblinworker.filmvote.model.server.Vote;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Class that handles Film Vote Server REST Calls.
 */
public class MobileClient extends BaseClient {

    private final String MOBILE_V1 = "mobile/v1/";
    private final String SIGN_UP = "signUp/";
    private final String SIGN_IN = "signIn/";
    private final String SIGN_OUT = "signOut/";
    private final String ADD_CLUB = "addClub/";
    private final String REMOVE_CLUB = "removeClub/";
    private final String ADD_THEATER = "addTheater/";
    private final String REMOVE_THEATER = "removeTheater/";
    private final String GET_THEATERS_FOR_DATE = "getTheatersForDate/";
    private final String GET_THEATERS_FOR_LOCATION = "getTheatersForLocation/";
    private final String ADD_VOTE = "addVote/";
    private final String GET_FILM_VOTE = "getFilmVote/";
    private final String SLASH = "/";

    /**
     * Constructor to initialize REST Client.
     *
     * @param server String
     */
    public MobileClient(String server) {
        super(server);
    }

    /**
     * Add a user to a club.
     *
     * @param club String
     * @param user String
     * @return User
     */
    public User signUp(String club, String user) throws IOException, JsonSyntaxException {
        String url = server + MOBILE_V1 + SIGN_UP + encode(club) + SLASH + encode(user);

        // TODO: change api from post to put
        String json = post(url, "");

        return gson.fromJson(json, User.class);
    }

    /**
     * Sign in to a club.
     *
     * @param club String
     * @param user String
     * @return User
     */
    public User signIn(String club, String user) throws IOException, JsonSyntaxException, JSONException {
        String url = server + MOBILE_V1 + SIGN_IN + encode(club) + SLASH + encode(user);

        // TODO: change api from post to put
        String json = post(url, "");

        Error responseError = gson.fromJson(json, Error.class);
        if (responseError != null && responseError.isValid()) {
            throw new IOException(responseError.getMessage());
        }

        User responseUser = gson.fromJson(json, User.class);
        if (responseUser == null || !responseUser.isValid()) {
            throw new JSONException("Invalid User.");
        }

        return gson.fromJson(json, User.class);
    }

    /**
     * Sign out of club.
     *
     * @param club String
     * @param user String
     */
    public void signOut(String club, String user) throws IOException {
        String url = server + MOBILE_V1 + SIGN_OUT + encode(club) + SLASH + encode(user);

        // TODO: change api from post to put
        String json = post(url, "");

        return;
    }

    /**
     * Add a voting club.
     *
     * @param club String
     */
    public void addClub(String club) throws IOException {
        String url = server + MOBILE_V1 + ADD_CLUB + encode(club);

        // TODO: change api from post to put
        String json = post(url, "");

        return;
    }

    /**
     * Remove a voting club.
     *
     * @param club String
     */
    public void removeClub(String club) throws IOException {
        String url = server + MOBILE_V1 + REMOVE_CLUB + encode(club);

        String json = delete(url);

        return;
    }

    /**
     * Add a theater to a club.
     *
     * @param club    String
     * @param theater String
     * @return Theater
     */
    public Theater addTheater(String club, Theater theater) throws IOException, JsonSyntaxException {
        String url = server + MOBILE_V1 + ADD_THEATER + encode(club);

        String requestJson = gson.toJson(theater);
        String responseJson = post(url, requestJson);

        return gson.fromJson(responseJson, Theater.class);
    }

    /**
     * Remove a theater from a club.
     *
     * @param club    String
     * @param theater String
     * @return Theater
     */
    public Theater removeTheater(String club, String theater) throws IOException, JsonSyntaxException {
        String url = server + MOBILE_V1 + REMOVE_THEATER + encode(club) + SLASH + encode(theater);

        String json = delete(url);

        return gson.fromJson(json, Theater.class);
    }

    /**
     * Get a map of Theaters in a club.
     *
     * @param club String
     * @return Map of Theaters
     */
    public Map<String, Theater> getTheatersForClub(String club) {
        // TODO: finish him!!!
        return null;
    }

    /**
     * Get a map of Theaters for a specific date.
     *
     * @param club String
     * @param date String yyyy-MM-dd
     * @return Map of Theaters
     */
    public Map<String, Theater> getTheatersForDate(String club, String date) throws IOException, JsonSyntaxException {
        String url = server + MOBILE_V1 + GET_THEATERS_FOR_DATE + encode(club) + SLASH + encode(date);

        String json = get(url);

        Type type = new TypeToken<Map<String, Theater>>() {
        }.getType();

        return gson.fromJson(json, type);
    }

    /**
     * Get a map of Theaters for a specific location.
     *
     * @param location String address or zipcode
     * @return Map of Theaters
     */
    public Map<String, Theater> getTheatersForLocation(String location) throws IOException, JsonSyntaxException {
        String url = server + MOBILE_V1 + GET_THEATERS_FOR_LOCATION + encode(location);

        String json = get(url);

        Type type = new TypeToken<Map<String, Theater>>() {
        }.getType();

        return gson.fromJson(json, type);
    }

    /**
     * Add a vote for a film.
     *
     * @param club String
     * @param user String
     * @param vote String
     * @return Vote
     */
    public Vote addVote(String club, String user, Vote vote) throws IOException, JsonSyntaxException {
        String url = server + MOBILE_V1 + ADD_VOTE + encode(club) + SLASH + encode(user);

        String requestJson = gson.toJson(vote);
        String responseJson = post(url, requestJson);

        return gson.fromJson(responseJson, Vote.class);
    }

    /**
     * Get the leading vote for the next active date.
     *
     * @param club String
     * @return Vote
     */
    public Vote getFilmVote(String club) throws IOException, JsonSyntaxException {
        String url = server + MOBILE_V1 + GET_FILM_VOTE + encode(club);

        String json = get(url);

        return gson.fromJson(json, Vote.class);
    }

    /**
     * Get the leading vote for a specific date.
     *
     * @param club String
     * @param date String
     * @return Vote
     */
    public Vote getFilmVote(String club, String date) throws IOException, JsonSyntaxException {
        String url = server + MOBILE_V1 + GET_FILM_VOTE + encode(club) + SLASH + encode(date);

        String json = get(url);

        return gson.fromJson(json, Vote.class);
    }

}
