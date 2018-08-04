package com.goblinworker.filmvote.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goblinworker.filmvote.R;
import com.goblinworker.filmvote.fragment.HomeFragment;
import com.goblinworker.filmvote.fragment.SignInClubFragment;
import com.goblinworker.filmvote.fragment.SignInUserFragment;
import com.goblinworker.filmvote.fragment.TheaterFragment;
import com.goblinworker.filmvote.fragment.VoteFragment;

/**
 * Activity to sign in / up to server.
 */
public class SignInActivity extends AppCompatActivity
        implements SignInClubFragment.OnInteractionListener, SignInUserFragment.OnInteractionListener {

    private static final String TAG = SignInActivity.class.getSimpleName();

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.view_pager_sign_in);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onSignInClub(String clubName) {
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onSignUpClub(String clubName) {

    }

    @Override
    public void onSignInUser(String clubName, String userName) {
        startMainActivity();
    }

    @Override
    public void onSignUpUser(String clubName, String userName) {

    }

    public void startMainActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    /**
     * Adapter to handle the Sign In Club / User fragments.
     */
    public class ViewPagerAdapter extends FragmentPagerAdapter {

        /**
         * Constructor to initiate adapter.
         *
         * @param manager FragmentManager
         */
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        /**
         * Called to instantiate the fragment for the given page.
         *
         * @param position int
         * @return Fragment
         */
        @Override
        public Fragment getItem(int position) {

            Fragment fragment;

            // TODO: add pretty intro page
            switch (position) {
                default:
                case 0:
                    fragment = SignInClubFragment.newInstance();
                    break;
                case 1:
                    fragment = SignInUserFragment.newInstance("Club Name");
                    break;
            }

            return fragment;
        }

        /**
         * Show 2 total pages.
         *
         * @return int
         */
        @Override
        public int getCount() {
            return 2;
        }

    }

}
