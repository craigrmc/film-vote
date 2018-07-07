package com.goblinworker.filmvote.network;

import android.util.Log;

import com.goblinworker.filmvote.BuildConfig;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Abstract Class that handles basic REST Call logic.
 */
public abstract class BaseClient {

    private static final String TAG = BaseClient.class.getSimpleName();

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    final OkHttpClient client = new OkHttpClient();
    final Gson gson = new Gson();
    final String server;

    /**
     * Constructor to initialize REST Client.
     *
     * @param server String
     */
    BaseClient(String server) {
        this.server = server;
    }

    String get(String url) throws IOException {

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "request url:  " + url);
        }

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "response json: " + responseJson);
        }

        return responseJson;
    }

    String post(String url, String json) throws IOException {

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "request url:  " + url);
            Log.d(TAG, "request json: " + json);
        }

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "response json: " + responseJson);
        }

        return responseJson;
    }

    String delete(String url) throws IOException {

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "request url:  " + url);
        }

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "response json: " + responseJson);
        }

        return responseJson;
    }

    String delete(String url, String json) throws IOException {

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "request url:  " + url);
            Log.d(TAG, "request json: " + json);
        }

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "response json: " + responseJson);
        }

        return responseJson;
    }

}
