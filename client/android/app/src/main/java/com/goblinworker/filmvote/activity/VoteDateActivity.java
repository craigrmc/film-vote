package com.goblinworker.filmvote.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.goblinworker.filmvote.R;
import com.goblinworker.filmvote.app.AppInstance;
import com.goblinworker.filmvote.model.server.Film;
import com.goblinworker.filmvote.model.server.Theater;
import com.goblinworker.filmvote.model.server.Vote;
import com.goblinworker.filmvote.network.MobileClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity that allows user to vote for a film on a specific date.
 */
public class VoteDateActivity extends AppCompatActivity {

    private static final String TAG = VoteDateActivity.class.getSimpleName();

    private static final String EXTRA_DATE = "EXTRA_DATE";

    private String date;

    private VoteDateListAdapter listAdapter;
    private TheaterTask theaterTask;
    private VoteTask voteTask;

    /**
     * Create a new intent for activity on a specific date.
     *
     * @param context Activity Context
     * @param date    String yyyy-MM-dd
     * @return Intent to start Activity
     */
    public static Intent newIntent(Context context, String date) {
        Intent intent = new Intent(context, VoteDateActivity.class);
        intent.putExtra(EXTRA_DATE, date);
        return intent;
    }

    /**
     * Initialize Activity.
     *
     * @param bundle Saved Instance State
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_vote_date);

        if (getIntent() != null) {
            date = getIntent().getStringExtra(EXTRA_DATE);
        }

        // TODO: use relative date
        TextView headerTextView = findViewById(R.id.vote_date_header_text_view);
        headerTextView.setText(date);

        listAdapter = new VoteDateListAdapter();

        ListView listView = findViewById(R.id.vote_date_list_view);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                onFilmSelect(listAdapter.getItem(index));
            }
        });

        startTheaterTask();
    }

    /**
     * Display show time selection dialog for a film.
     *
     * @param item Selected Theater / Film Item
     */
    protected void onFilmSelect(final VoteDateListItem item) {

        if (item == null) {
            Log.w(TAG, "failed to select film, invalid item");
            return;
        }

        final VoteTimeListAdapter showTimeListAdapter = new VoteTimeListAdapter(item.getFilm());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(item.getHeader());
        builder.setAdapter(showTimeListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                onTimeSelect(item.getTheaterName(), item.getFilm(), showTimeListAdapter.getItem(index));
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    /**
     * Send vote to server.
     *
     * @param film Selected Film
     * @param time Selected Show Time
     */
    protected void onTimeSelect(String theater, Film film, String time) {

        if (film == null) {
            Log.w(TAG, "failed to select film time, invalid film");
            return;
        }

        Vote vote = new Vote(theater, date, film.getName(), time);

        startVoteTask(vote);
    }

    /**
     * Update list view with new theaters, films, and show times.
     *
     * @param theaterMap Theater Map
     */
    protected void updateVoteList(Map<String, Theater> theaterMap) {

        if (theaterMap == null) {
            Log.w(TAG, "failed to update vote list, invalid theater map");
            return;
        }

        List<VoteDateListItem> itemList = new ArrayList<>();

        for (Theater theater : theaterMap.values()) {
            for (Film film : theater.getFilmList(date)) {
                itemList.add(new VoteDateListItem(theater.getName(), film));
            }
        }

        listAdapter.setItemList(itemList);
        listAdapter.notifyDataSetChanged();
    }

    /**
     * Show dialog for Vote Task failures.
     *
     * @param message String
     */
    protected void showFailDialog(String message) {

        // TODO: move to strings.xml
        String title = "Failed to send vote";
        String defaultMessage = "Please try again later.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        if (message == null || message.isEmpty()) {
            builder.setMessage(defaultMessage);
        } else {
            builder.setMessage(message);
        }

        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    /**
     * Start network task to get list of theaters, films, and show times for date.
     */
    protected void startTheaterTask() {

        cancelTheaterTask();

        theaterTask = new TheaterTask(date);
        theaterTask.setCallback(new TheaterTask.Callback() {
            @Override
            public void onResult(Boolean result, String message, Map<String, Theater> theaterMap) {
                if (result) {
                    updateVoteList(theaterMap);
                } else {
                    Log.w(TAG, "failed to get theater: " + message);
                }
            }
        });
        theaterTask.execute();
    }

    /**
     * Cancel network task.
     */
    protected void cancelTheaterTask() {
        if (theaterTask != null) {
            theaterTask.cancel(true);
            theaterTask = null;
        }
    }

    /**
     * Start network task to cast vote.
     *
     * @param vote Vote
     */
    protected void startVoteTask(Vote vote) {

        cancelVoteTask();

        voteTask = new VoteTask(vote);
        voteTask.setCallback(new VoteTask.Callback() {
            @Override
            public void onResult(Boolean result, String message) {
                if (result) {
                    finish();
                } else {
                    showFailDialog(message);
                }
            }
        });
        voteTask.execute();
    }

    /**
     * Cancel network task.
     */
    protected void cancelVoteTask() {
        if (voteTask != null) {
            voteTask.cancel(true);
            voteTask = null;
        }
    }

    /**
     * Adapter that handles film items in List View.
     */
    public class VoteDateListAdapter extends BaseAdapter {

        private final List<VoteDateListItem> itemList = new ArrayList<>();

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public VoteDateListItem getItem(int index) {

            if (itemList.size() < index) {
                return null;
            }

            return itemList.get(index);
        }

        @Override
        public long getItemId(int index) {
            return 0;
        }

        @Override
        public View getView(int index, View view, ViewGroup root) {

            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                view = inflater.inflate(R.layout.item_film, root, false);
            }

            VoteDateListItem item = getItem(index);

            TextView headerTextView = view.findViewById(R.id.item_film_header_text_view);
            headerTextView.setText(item.getHeader());

            TextView detailTextView = view.findViewById(R.id.item_film_detail_text_view);
            detailTextView.setText(item.getDetail());

            return view;
        }

        void setItemList(List<VoteDateListItem> list) {
            if (list != null) {
                itemList.clear();
                itemList.addAll(list);
            }
        }

    }

    /**
     * Adapter that handles show times in List View.
     */
    public class VoteTimeListAdapter extends BaseAdapter {

        private final Film film;

        VoteTimeListAdapter(Film film) {
            this.film = film;
        }

        @Override
        public int getCount() {
            return film.getShowTimeList().size();
        }

        @Override
        public String getItem(int index) {
            return film.getShowTimeList().get(index);
        }

        @Override
        public long getItemId(int index) {
            return 0;
        }

        @Override
        public View getView(int index, View view, ViewGroup root) {

            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                view = inflater.inflate(R.layout.item_time, root, false);
            }

            String showTime = getItem(index);

            TextView headerTextView = view.findViewById(R.id.item_time_text_view);
            headerTextView.setText(showTime);

            return view;
        }

    }

    /**
     * Object that holds voting information for the list.
     */
    public class VoteDateListItem {

        private final String theaterName;
        private final Film film;

        VoteDateListItem(String theaterName, Film film) {
            this.theaterName = theaterName;
            this.film = film;
        }

        public String getHeader() {
            return theaterName + " - " + film.getName();
        }

        public String getDetail() {
            return film.getShowTimes();
        }

        // Getter / Setter

        public String getTheaterName() {
            return theaterName;
        }

        public Film getFilm() {
            return film;
        }

    }

    /**
     * Task that gets theaters, films, and show times from server.
     */
    public static class TheaterTask extends AsyncTask<Void, Void, Boolean> {

        private final String date;

        private Callback asyncCallback;
        private Boolean asyncResult;
        private String asyncMessage;
        private final Map<String, Theater> asyncTheaterMap = new HashMap<>();

        TheaterTask(String date) {
            this.date = date;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            AppInstance appInstance = AppInstance.getInstance();

            MobileClient client = new MobileClient(appInstance.getServer());

            asyncTheaterMap.clear();

            try {

                Map<String, Theater> theaterMap = client.getTheatersForDate(appInstance.getClubName(), date);

                asyncTheaterMap.putAll(theaterMap);

                asyncResult = true;
                asyncMessage = "Request was successful.";
            } catch (Exception e) {

                asyncResult = false;
                asyncMessage = e.getMessage();
            }

            return asyncResult;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            asyncResult = false;
            asyncMessage = "Request was canceled.";
            onPostExecute(false);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (asyncCallback != null) {
                asyncCallback.onResult(asyncResult, asyncMessage, asyncTheaterMap);
            }
        }

        void setCallback(Callback callback) {
            this.asyncCallback = callback;
        }

        public interface Callback {

            void onResult(Boolean result, String message, Map<String, Theater> theaterMap);

        }

    }

    /**
     * Task that allows user to send film vote to server.
     */
    public static class VoteTask extends AsyncTask<Void, Void, Boolean> {

        private final Vote vote;

        private Callback asyncCallback;
        private Boolean asyncResult;
        private String asyncMessage;

        VoteTask(Vote vote) {
            this.vote = vote;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            AppInstance appInstance = AppInstance.getInstance();

            MobileClient client = new MobileClient(appInstance.getServer());

            try {

                client.addVote(appInstance.getClubName(), appInstance.getUserName(), vote);

                asyncResult = true;
                asyncMessage = "Request was successful.";
            } catch (Exception e) {
                asyncResult = false;
                asyncMessage = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            asyncResult = false;
            asyncMessage = "Request was canceled.";
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
            this.asyncCallback = callback;
        }

        public interface Callback {

            void onResult(Boolean result, String message);

        }

    }

}
