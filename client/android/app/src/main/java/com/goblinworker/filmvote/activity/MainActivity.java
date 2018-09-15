package com.goblinworker.filmvote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.goblinworker.filmvote.R;
import com.goblinworker.filmvote.fragment.HomeFragment;
import com.goblinworker.filmvote.fragment.TheaterFragment;
import com.goblinworker.filmvote.fragment.VoteFragment;

/**
 * Activity that displays the Home / Vote / Club Fragments via Bottom Navigation View.
 */
public class MainActivity extends AppCompatActivity
        implements VoteFragment.Listener, TheaterFragment.Listener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_vote:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_theater:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    /**
     * Create a new intent for activity.
     *
     * @param context Activity Context
     * @return Intent to Start Activity
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_view_main);
        navigation.setOnNavigationItemSelectedListener(navigationListener);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.view_pager_main);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onVoteDateTap(String date) {
        Intent intent = VoteDateActivity.newIntent(this, date);
        startActivity(intent);
    }

    @Override
    public void onTheaterFind() {
        Intent intent = TheaterFindActivity.newIntent(this);
        startActivity(intent);
    }

    /**
     * Adapter to handle the fragments in BottomNavigationView.
     */
    public class ViewPagerAdapter extends FragmentPagerAdapter {

        /**
         * Constructor to initiate adapter.
         *
         * @param manager FragmentManager
         */
        ViewPagerAdapter(FragmentManager manager) {
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

            switch (position) {
                default:
                case 0:
                    fragment = HomeFragment.newInstance();
                    break;
                case 1:
                    fragment = VoteFragment.newInstance();
                    break;
                case 2:
                    fragment = TheaterFragment.newInstance();
                    break;
            }

            return fragment;
        }

        /**
         * Show 3 total pages.
         *
         * @return int
         */
        @Override
        public int getCount() {
            return 3;
        }

    }

}
