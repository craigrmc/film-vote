package com.goblinworker.filmvote.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.goblinworker.filmvote.R;
import com.goblinworker.filmvote.app.AppInstance;
import com.goblinworker.filmvote.fragment.SignInClubFragment;
import com.goblinworker.filmvote.fragment.SignInGetStartedFragment;
import com.goblinworker.filmvote.fragment.SignInUserFragment;
import com.goblinworker.filmvote.fragment.SignInWorkFragment;
import com.goblinworker.filmvote.model.server.User;
import com.goblinworker.filmvote.network.MobileClient;

/**
 * Activity to sign in / up to server.
 */
public class SignInActivity extends AppCompatActivity
        implements SignInGetStartedFragment.Listener, SignInClubFragment.Listener, SignInUserFragment.Listener {

    private static final String TAG = SignInActivity.class.getSimpleName();

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    private SignInTask signInUserTask;

    private String clubName;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.sign_in_view_pager);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelSignInTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sign_in, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_server:
                showServerDialog();
            default:
                Log.w(TAG, "unknown item selected: " + item.getItemId());
        }

        return true;
    }

    @Override
    public void onGetStarted() {
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onClubBack() {
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onClubNext(String clubName) {
        this.clubName = clubName;

        viewPager.setCurrentItem(2);
    }

    @Override
    public void onUserBack() {
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onUserNext(String userName) {
        this.userName = userName;

        viewPager.setCurrentItem(3);

        startSignInTask();
    }

    public void startSignInTask() {

        cancelSignInTask();

        signInUserTask = new SignInTask(clubName, userName, true);
        signInUserTask.setCallback(new SignInTask.Callback() {
            @Override
            public void onResult(Boolean result, String message, User user) {
                if (result) {
                    startMainActivity(user);
                } else {
                    showFailDialog(message);
                }
            }
        });
        signInUserTask.execute();
    }

    public void cancelSignInTask() {
        if (signInUserTask != null) {
            signInUserTask.cancel(true);
            signInUserTask = null;
        }
    }

    private void showServerDialog() {

        final AppInstance appInstance = AppInstance.getInstance();

        final EditText editText = new EditText(this);
        editText.setText(appInstance.getServer());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.server_address);
        builder.setView(editText);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                appInstance.setServer(editText.getText().toString());
            }
        });
        builder.show();
    }

    private void showFailDialog(String message) {

        viewPager.setCurrentItem(1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.failed_to_sign_in);

        if (message == null || message.isEmpty()) {
            builder.setMessage(R.string.please_try_again_later);
        } else {
            builder.setMessage(message);
        }

        builder.setPositiveButton(R.string.ok, null);
        builder.create().show();
    }

    private void startMainActivity(User user) {

        if (user == null || !user.isValid()) {
            Log.w(TAG, "failed to start main activity, invalid user");
            return;
        }

        AppInstance.getInstance().setUser(user);

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
                    fragment = SignInGetStartedFragment.newInstance();
                    break;
                case 1:
                    fragment = SignInClubFragment.newInstance();
                    break;
                case 2:
                    fragment = SignInUserFragment.newInstance();
                    break;
                case 3:
                    fragment = SignInWorkFragment.newInstance();
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
            return 4;
        }

    }

    /**
     * AsyncTask to Sign In the user to the server.
     */
    public static class SignInTask extends AsyncTask<Void, Void, Boolean> {

        private final String clubName;
        private final String userName;
        private final Boolean createUser;

        private Callback asyncCallback;
        private Boolean asyncResult;
        private String asyncMessage;
        private User asyncUser;

        /**
         * Constructor to sign up a user.
         *
         * @param clubName   String Club Name
         * @param userName   String User Name
         * @param createUser Boolean to allow a new user to be created
         */
        SignInTask(String clubName, String userName, Boolean createUser) {
            this.clubName = clubName;
            this.userName = userName;
            this.createUser = createUser;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            AppInstance appInstance = AppInstance.getInstance();

            MobileClient client = new MobileClient(appInstance.getServer());

            try {

                if (createUser) {
                    asyncUser = client.signUp(clubName, userName);
                } else {
                    asyncUser = client.signIn(clubName, userName);
                }

                asyncMessage = "Sign in was successful.";
                asyncResult = true;

                Log.i(TAG, "sign in success");
            } catch (Exception e) {

                asyncUser = null;

                asyncMessage = e.getMessage();
                asyncResult = false;

                Log.e(TAG, "sign in failure", e);
            }

            return asyncResult;
        }

        @Override
        protected void onCancelled() {
            asyncResult = false;
            asyncMessage = "Sign in was canceled.";
            onPostExecute(false);
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            if (asyncCallback != null) {
                asyncCallback.onResult(asyncResult, asyncMessage, asyncUser);
            }
        }

        void setCallback(Callback callback) {
            this.asyncCallback = callback;
        }

        public interface Callback {

            void onResult(Boolean result, String message, User user);

        }

    }

}
