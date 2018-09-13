package com.goblinworker.filmvote.app;

import com.goblinworker.filmvote.model.server.User;

/**
 * Singleton Class that holds data for current app session.
 */
public class AppInstance {

    private static final AppInstance appInstance = new AppInstance();

    private String server;
    private User user;

    public static AppInstance getInstance() {
        return appInstance;
    }

    /**
     * Singleton Classes do not have public constructors.
     */
    private AppInstance() {
    }

    /**
     * Get the current club name for user.
     *
     * @return String
     */
    public String getClubName() {

        if (user == null) {
            return null;
        }

        return user.getClubName();
    }

    /**
     * Get the current user name for user.
     *
     * @return String
     */
    public String getUserName() {

        if (user == null) {
            return null;
        }

        return user.getUserName();
    }

    /**
     * Get the current mobile server for the API.
     *
     * @return String URL
     */
    public String getServer() {

        if (server == null) {
            return AppConfig.DEFAULT_SERVER;
        }

        return server;
    }

    /**
     * Set the current mobile server for the API.
     *
     * @param server String URL
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Get the user's sign-in data.
     *
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user's sign-in data.
     *
     * @param user User
     */
    public void setUser(User user) {
        this.user = user;
    }

}
