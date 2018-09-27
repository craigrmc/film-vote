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
import java.util.List;
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
    public User signUp(String club, String user) throws IOException, JsonSyntaxException, JSONException {
        String url = server + MOBILE_V1 + SIGN_UP + encode(club) + SLASH + encode(user);

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

        return responseUser;
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

        return responseUser;
    }

    /**
     * Sign out of club.
     *
     * @param club String
     * @param user String
     * @return String response
     */
    public String signOut(String club, String user) throws IOException {
        String url = server + MOBILE_V1 + SIGN_OUT + encode(club) + SLASH + encode(user);

        // TODO: change api from post to put
        String json = post(url, "");

        Error responseError = gson.fromJson(json, Error.class);
        if (responseError != null && responseError.isValid()) {
            throw new IOException(responseError.getMessage());
        }

        return json;
    }

    /**
     * Add a voting club.
     *
     * @param club String
     * @return String response
     */
    public String addClub(String club) throws IOException {
        String url = server + MOBILE_V1 + ADD_CLUB + encode(club);

        // TODO: change api from post to put
        String json = post(url, "");

        Error responseError = gson.fromJson(json, Error.class);
        if (responseError != null && responseError.isValid()) {
            throw new IOException(responseError.getMessage());
        }

        return json;
    }

    /**
     * Remove a voting club.
     *
     * @param club String
     * @return String response
     */
    public String removeClub(String club) throws IOException {
        String url = server + MOBILE_V1 + REMOVE_CLUB + encode(club);

        String json = delete(url);

        Error responseError = gson.fromJson(json, Error.class);
        if (responseError != null && responseError.isValid()) {
            throw new IOException(responseError.getMessage());
        }

        return json;
    }

    /**
     * Add a theater to a club.
     *
     * @param club    String
     * @param theater String
     * @return Theater
     */
    public Theater addTheater(String club, Theater theater)
            throws IOException, JsonSyntaxException, JSONException {
        String url = server + MOBILE_V1 + ADD_THEATER + encode(club);

        String requestJson = gson.toJson(theater);
        String responseJson = post(url, requestJson);

        Error responseError = gson.fromJson(responseJson, Error.class);
        if (responseError != null && responseError.isValid()) {
            throw new IOException(responseError.getMessage());
        }

        Theater responseTheater = gson.fromJson(responseJson, Theater.class);
        if (responseTheater == null) {
            // TODO: throw exception
        }

        return responseTheater;
    }

    /**
     * Remove a theater from a club.
     *
     * @param club    String
     * @param theater String
     * @return Theater
     */
    public Theater removeTheater(String club, String theater)
            throws IOException, JsonSyntaxException, JSONException {
        String url = server + MOBILE_V1 + REMOVE_THEATER + encode(club) + SLASH + encode(theater);

        String json = delete(url);

        Error responseError = gson.fromJson(json, Error.class);
        if (responseError != null && responseError.isValid()) {
            throw new IOException(responseError.getMessage());
        }

        Theater responseTheater = gson.fromJson(json, Theater.class);
        if (responseTheater == null) {
            // TODO: throw exception
        }

        return responseTheater;
    }

    /**
     * Get a map of Theaters for a specific date.
     *
     * @param club String
     * @param date String yyyy-MM-dd
     * @return Map of Theaters
     */
    public Map<String, Theater> getTheatersForDate(String club, String date)
            throws IOException, JsonSyntaxException, JSONException {
        String url = server + MOBILE_V1 + GET_THEATERS_FOR_DATE + encode(club) + SLASH + encode(date);

        String json = get(url);

        Type type = new TypeToken<Map<String, Theater>>() {
        }.getType();

        Map<String, Theater> theaterMap = gson.fromJson(json, type);
        if (theaterMap == null) {
            throw new JSONException("Invalid Theater Map.");
        }

        return theaterMap;
    }

    /**
     * Get a map of Theaters for a specific location.
     *
     * @param location String address or zipcode
     * @return Map of Theaters
     */
    public Map<String, Theater> getTheatersForLocation(String location)
            throws IOException, JsonSyntaxException, JSONException {
        String url = server + MOBILE_V1 + GET_THEATERS_FOR_LOCATION + encode(location);

        String json = get(url);

        Type type = new TypeToken<Map<String, Theater>>() {
        }.getType();

        Map<String, Theater> theaterMap = gson.fromJson(json, type);
        if (theaterMap == null) {
            throw new JSONException("Invalid Theater Map.");
        }

        return theaterMap;
    }

    /**
     * Add a vote for a film.
     *
     * @param club String
     * @param user String
     * @param vote String
     * @return Vote
     */
    public Vote addVote(String club, String user, Vote vote)
            throws IOException, JsonSyntaxException, JSONException {
        String url = server + MOBILE_V1 + ADD_VOTE + encode(club) + SLASH + encode(user);

        String requestJson = gson.toJson(vote);
        String responseJson = post(url, requestJson);

        Error responseError = gson.fromJson(responseJson, Error.class);
        if (responseError != null && responseError.isValid()) {
            throw new IOException(responseError.getMessage());
        }

        Vote responseVote = gson.fromJson(responseJson, Vote.class);
        if (responseVote == null) {
            throw new JSONException("Invalid Vote.");
        }

        return responseVote;
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

        Error responseError = gson.fromJson(json, Error.class);
        if (responseError != null && responseError.isValid()) {
            throw new IOException(responseError.getMessage());
        }

        return gson.fromJson(json, Vote.class);
    }

    /**
     * Get the leading vote for a specific date.
     *
     * @param club String
     * @param date String yyyy-MM-dd
     * @return Vote
     */
    public Vote getFilmVote(String club, String date) throws IOException, JsonSyntaxException {
        String url = server + MOBILE_V1 + GET_FILM_VOTE + encode(club) + SLASH + encode(date);

        String json = get(url);

        Error responseError = gson.fromJson(json, Error.class);
        if (responseError != null && responseError.isValid()) {
            throw new IOException(responseError.getMessage());
        }

        return gson.fromJson(json, Vote.class);
    }

    /**
     * Get the leading vote for a specific date range.
     *
     * @param club  String
     * @param start String yyyy-MM-dd
     * @param end   String yyyy-MM-dd
     * @return Vote List
     */
    public List<Vote> getFilmVoteList(String club, String start, String end)
            throws IOException, JsonSyntaxException, JSONException {
        String url = server + MOBILE_V1 + GET_FILM_VOTE + encode(club) + SLASH + start + SLASH + end;

        String json = get(url);

        Type type = new TypeToken<List<Vote>>() {
        }.getType();

        List<Vote> voteList = gson.fromJson(json, type);
        if (voteList == null) {
            throw new JSONException("Invalid Vote List");
        }

        return voteList;
    }

}
