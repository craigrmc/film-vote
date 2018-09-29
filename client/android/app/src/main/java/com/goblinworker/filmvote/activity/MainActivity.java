package com.goblinworker.filmvote.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.goblinworker.filmvote.R;
import com.goblinworker.filmvote.app.AppInstance;
import com.goblinworker.filmvote.fragment.HomeFragment;
import com.goblinworker.filmvote.fragment.TheaterFragment;
import com.goblinworker.filmvote.fragment.VoteFragment;
import com.goblinworker.filmvote.network.MobileClient;

/**
 * Activity that displays the Home / Vote / Club Fragments via Bottom Navigation View.
 */
public class MainActivity extends AppCompatActivity
        implements VoteFragment.Listener, TheaterFragment.Listener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    private SignOutTask signOutTask;
    private ProgressDialog signOutDialog;

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

        BottomNavigationView navigation = findViewById(R.id.main_bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(navigationListener);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.main_view_pager);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelSignOutTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                startSignOutTask();
            default:
                Log.w(TAG, "unknown item selected: " + item.getItemId());
        }

        return true;
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

    protected void showProgressDialog() {
        signOutDialog = ProgressDialog.show(
                this, null, getString(R.string.signing_out), true);
    }

    protected void hideProgressDialog() {
        if (signOutDialog != null) {
            signOutDialog.dismiss();
        }
    }

    protected void showFailDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.failed_to_send_request);

        if (message == null || message.isEmpty()) {
            builder.setMessage(R.string.please_try_again_later);
        } else {
            builder.setMessage(message);
        }

        builder.setPositiveButton(R.string.ok, null);
        builder.show();
    }

    protected void startSignOutTask() {

        cancelSignOutTask();

        showProgressDialog();

        signOutTask = new SignOutTask();
        signOutTask.setCallback(new SignOutTask.Callback() {
            @Override
            public void onResult(Boolean result, String message) {
                hideProgressDialog();
                if (result) {
                    Log.i(TAG, "sign out done: " + message);
                    finish();
                } else {
                    Log.w(TAG, "sign out fail: " + message);
                    showFailDialog(message);
                }
            }
        });
        signOutTask.execute();
    }

    protected void cancelSignOutTask() {
        if (signOutTask != null) {
            signOutTask.cancel(true);
            signOutTask = null;
        }
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

    /**
     * Task that ends user's session in the app.
     */
    public static class SignOutTask extends AsyncTask<Void, Void, Boolean> {

        private Callback asyncCallback;
        private Boolean asyncResult;
        private String asyncMessage;

        @Override
        protected Boolean doInBackground(Void... voids) {

            AppInstance appInstance = AppInstance.getInstance();

            MobileClient client = new MobileClient(appInstance.getServer());

            try {

                client.signOut(appInstance.getClubName(), appInstance.getUserName());

                asyncResult = true;
                asyncMessage = "Request was successful.";
            } catch (Exception e) {
                asyncResult = false;
                asyncMessage = e.getMessage();
            }

            return asyncResult;
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
            asyncResult = false;
            asyncMessage = "Request was cancelled.";
            onPostExecute(false);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (asyncCallback != null) {
                asyncCallback.onResult(asyncResult, asyncMessage);
            }
        }

        void setCallback(Callback callback) {
            asyncCallback = callback;
        }

        interface Callback {

            void onResult(Boolean result, String message);

        }

    }

}
