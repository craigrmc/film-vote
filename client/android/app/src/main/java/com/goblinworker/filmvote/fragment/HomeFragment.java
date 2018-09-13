package com.goblinworker.filmvote.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

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
        if (vote == null || vote.getDate() == null) {
            dateTextView.setText("");
        } else {
            dateTextView.setText(vote.getDayLegacy());
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
        if (vote == null || vote.getTime() == null) {
            timeTextView.setText("");
        } else {
            timeTextView.setText(vote.getLocalTimeLegacy());
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
            // TODO: move to strings.xml
            return "N/A";
        }

        return clubName;
    }

    public String getUserName() {

        String userName = AppInstance.getInstance().getUserName();
        if (userName == null || userName.isEmpty()) {
            // TODO: move to strings.xml
            return "N/A";
        }

        return userName;
    }

    public String getBuild() {

        // TODO: move to strings.xml
        String type;
        if (BuildConfig.DEBUG) {
            type = "(Debug)";
        } else {
            type = "(Release)";
        }

        return BuildConfig.VERSION_CODE + " " + type;
    }

    public String getVersion() {
        return BuildConfig.VERSION_NAME;
    }

    public String getVoteHeader(Integer tally) {

        // TODO: move to strings.xml

        if (tally == null || tally < 0) {
            return "No Film Votes";
        }

        String nextFilm = "Next Film";

        String vote;
        if (tally == 1) {
            vote = "vote";
        } else {
            vote = "votes";
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
                asyncMessage = "Success";

            } catch (Exception e) {

                asyncVote = null;

                asyncResult = false;
                asyncMessage = e.getMessage();
            }

            return asyncResult;
        }

        @Override
        protected void onCancelled() {
            if (asyncCallback != null) {
                asyncCallback.onResult(asyncResult, asyncMessage, asyncVote);
            }
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
