package com.goblinworker.filmvote.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goblinworker.filmvote.BuildConfig;
import com.goblinworker.filmvote.R;
import com.goblinworker.filmvote.app.AppInstance;
import com.goblinworker.filmvote.model.server.ServerDateTime;
import com.goblinworker.filmvote.model.server.Vote;
import com.goblinworker.filmvote.network.MobileClient;

/**
 * Fragment that displays the leading vote for the club.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private NextVoteTask nextVoteTask;

    /**
     * Required empty public constructor.
     */
    public HomeFragment() {
    }

    /**
     * Create a new instance of the fragment.
     *
     * @return HomeFragment
     */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView clubTextView = view.findViewById(R.id.text_view_home_club_value);
        clubTextView.setText(getClubName());

        TextView userTextView = view.findViewById(R.id.text_view_home_user_value);
        userTextView.setText(getUserName());

        TextView buildTextView = view.findViewById(R.id.text_view_home_build_value);
        buildTextView.setText(getBuild());

        TextView versionTextView = view.findViewById(R.id.text_view_home_version_value);
        versionTextView.setText(getVersion());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        startNextVoteTask();
    }

    @Override
    public void onStop() {
        super.onStop();

        cancelNextVoteTask();
    }

    public void updateVote(Vote vote) {

        View view = getView();
        if (view == null) {
            Log.w(TAG, "failed to update vote, fragment view not found");
            return;
        }

        ServerDateTime dateTime;
        if (vote == null) {
            dateTime = null;
        } else {
            dateTime = new ServerDateTime(vote.getDate(), vote.getTime());
        }

        LinearLayout voteLinearLayout = view.findViewById(R.id.linear_layout_home_vote);
        if (vote == null) {
            voteLinearLayout.setVisibility(View.GONE);
        } else {
            voteLinearLayout.setVisibility(View.VISIBLE);
        }

        LinearLayout voteNoneLinearLayout = view.findViewById(R.id.linear_layout_home_vote_none);
        if (vote == null) {
            voteNoneLinearLayout.setVisibility(View.VISIBLE);
        } else {
            voteNoneLinearLayout.setVisibility(View.GONE);
        }

        TextView headerTextView = view.findViewById(R.id.text_view_home_vote_header);
        if (vote == null || vote.getTally() == null) {
            headerTextView.setText(getVoteHeader(-1));
        } else {
            headerTextView.setText(getVoteHeader(vote.getTally()));
        }

        TextView dateTextView = view.findViewById(R.id.text_view_home_vote_date);
        if (dateTime == null || dateTime.getServerDate() == null) {
            dateTextView.setText("");
        } else {
            dateTextView.setText(dateTime.getDisplayDate());
        }

        TextView theaterTextView = view.findViewById(R.id.text_view_home_vote_theater);
        if (vote == null || vote.getTheater() == null) {
            theaterTextView.setText("");
        } else {
            theaterTextView.setText(vote.getTheater());
        }

        TextView filmTextView = view.findViewById(R.id.text_view_home_vote_film);
        if (vote == null || vote.getFilm() == null) {
            filmTextView.setText("");
        } else {
            filmTextView.setText(vote.getFilm());
        }

        TextView timeTextView = view.findViewById(R.id.text_view_home_vote_time);
        if (dateTime == null || dateTime.getServerTime() == null) {
            timeTextView.setText("");
        } else {
            timeTextView.setText(dateTime.getDisplayTime());
        }
    }

    public void startNextVoteTask() {

        cancelNextVoteTask();

        // TODO: suppress data reloads

        nextVoteTask = new NextVoteTask();
        nextVoteTask.setCallback(new NextVoteTask.Callback() {
            @Override
            public void onResult(Boolean result, String message, Vote vote) {
                if (result) {
                    updateVote(vote);
                } else {
                    Log.w(TAG, "failed to get next vote: " + message);
                }
            }
        });
        nextVoteTask.execute();
    }

    public void cancelNextVoteTask() {
        if (nextVoteTask != null) {
            nextVoteTask.cancel(true);
            nextVoteTask = null;
        }
    }

    public String getClubName() {

        String clubName = AppInstance.getInstance().getClubName();
        if (clubName == null || clubName.isEmpty()) {
            return getString(R.string.na);
        }

        return clubName;
    }

    public String getUserName() {

        String userName = AppInstance.getInstance().getUserName();
        if (userName == null || userName.isEmpty()) {
            return getString(R.string.na);
        }

        return userName;
    }

    public String getBuild() {

        String type;
        if (BuildConfig.DEBUG) {
            type = getString(R.string.debug);
        } else {
            type = getString(R.string.release);
        }

        return BuildConfig.VERSION_CODE + " " + type;
    }

    public String getVersion() {
        return BuildConfig.VERSION_NAME;
    }

    public String getVoteHeader(Integer tally) {

        if (tally == null || tally < 0) {
            return getString(R.string.no_film_votes);
        }

        String nextFilm = getString(R.string.next_film);

        String vote;
        if (tally == 1) {
            vote = getString(R.string.vote);
        } else {
            vote = getString(R.string.votes);
        }

        return nextFilm + " (" + tally + " " + vote + ")";
    }

    /**
     * Task to get next film vote from server.
     */
    public static class NextVoteTask extends AsyncTask<Void, Void, Boolean> {

        private Callback asyncCallback;
        private Boolean asyncResult;
        private String asyncMessage;
        private Vote asyncVote;

        @Override
        protected Boolean doInBackground(Void... voids) {

            AppInstance appInstance = AppInstance.getInstance();

            MobileClient client = new MobileClient(appInstance.getServer());

            try {

                asyncVote = client.getFilmVote(appInstance.getClubName());

                asyncResult = true;
                asyncMessage = "Request was successful.";

            } catch (Exception e) {

                asyncVote = null;

                asyncResult = false;
                asyncMessage = e.getMessage();
            }

            return asyncResult;
        }

        @Override
        protected void onCancelled() {
            asyncResult = false;
            asyncMessage = "Request was canceled.";
            onPostExecute(false);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (asyncCallback != null) {
                asyncCallback.onResult(asyncResult, asyncMessage, asyncVote);
            }
        }

        void setCallback(Callback callback) {
            this.asyncCallback = callback;
        }

        interface Callback {

            void onResult(Boolean result, String message, Vote vote);

        }

    }

}
