package com.goblinworker.filmvote.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class that holds data for current app installation.
 */
public class AppPreference {

    private final String SERVER = "SERVER";
    private final String TOKEN = "TOKEN";

    private final SharedPreferences preferences;

    /**
     * Constructor to create App Preference Manager.
     *
     * @param context Activity Context
     */
    public AppPreference(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Get the current mobile server for the API.
     *
     * @return String URL
     */
    public String getServer() {
        return preferences.getString(SERVER, AppConfig.DEFAULT_SERVER);
    }

    /**
     * Set the current mobile server for the API.
     *
     * @param server String URL
     */
    public void setServer(String server) {
        preferences.edit().putString(SERVER, server).apply();
    }

    /**
     * Get the current OAuth Token for user's session.
     *
     * @return String token
     */
    public String getToken() {
        return preferences.getString(TOKEN, null);
    }

    /**
     * Set the current OAuth Token for user's session.
     *
     * @param token String
     */
    public void setToken(String token) {
        preferences.edit().putString(TOKEN, token).apply();
    }

}
