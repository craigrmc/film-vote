package com.goblinworker.filmvote.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goblinworker.filmvote.R;

/**
 * Activity to find and add new theater to the club.
 */
public class TheaterFindActivity extends AppCompatActivity {

    private static final String TAG = TheaterFindActivity.class.getSimpleName();

    /**
     * Create a new intent for activity.
     *
     * @param context Activity Context
     * @return Intent to Start Activity
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, TheaterFindActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_find);
    }

}
