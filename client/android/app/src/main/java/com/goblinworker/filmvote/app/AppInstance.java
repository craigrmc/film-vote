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

    public String getServer() {

        if (server == null) {
            return AppConfig.DEFAULT_SERVER;
        }

        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
