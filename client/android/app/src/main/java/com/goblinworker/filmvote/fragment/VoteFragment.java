package com.goblinworker.filmvote.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.goblinworker.filmvote.model.server.ServerDateTime;
import com.goblinworker.filmvote.model.server.Vote;
import com.goblinworker.filmvote.network.MobileClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that displays upcoming voting dates for the club.
 */
public class VoteFragment extends Fragment {

    private static final String TAG = VoteFragment.class.getSimpleName();

    private Listener listener;

    private VoteListAdapter listAdapter;

    private VoteListTask voteListTask;

    /**
     * Required empty public constructor
     */
    public VoteFragment() {
    }

    /**
     * Create a new instance of the fragment.
     *
     * @return VoteFragment
     */
    public static VoteFragment newInstance() {
        return new VoteFragment();
    }

    /**
     * Initialize Fragment.
     *
     * @param bundle Saved Bundle State
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    /**
     * Inflate the layout for this fragment.
     *
     * @param inflater  LayoutInflater
     * @param container ViewGroup Container
     * @param bundle    Saved Bundle State
     * @return View of Fragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        View view = inflater.inflate(R.layout.fragment_vote, container, false);

        listAdapter = new VoteListAdapter();

        ListView listView = view.findViewById(R.id.list_view_vote);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                if (listener != null) {
                    String date = listAdapter.getItem(index).getDate();
                    listener.onVoteDateTap(date);
                }
            }
        });

        return view;
    }

    /**
     * Attach Fragment Listener.
     *
     * @param context Activity Context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        }
    }

    /**
     * Detach Fragment Listener.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * Start the fragment.
     */
    @Override
    public void onStart() {
        super.onStart();
        startVoteListTask();
    }

    /**
     * Stop the fragment.
     */
    @Override
    public void onStop() {
        super.onStop();
        cancelVoteListTask();
    }

    public void updateVoteList(List<Vote> voteList) {
        listAdapter.setItemList(voteList);
        listAdapter.notifyDataSetChanged();
    }

    public void startVoteListTask() {

        cancelVoteListTask();

        // TODO: suppress data reloads

        long sixDaysFromNow = System.currentTimeMillis() + 86400000 * 6;

        ServerDateTime startDateTime = new ServerDateTime();
        ServerDateTime endDateTime = new ServerDateTime(sixDaysFromNow);

        voteListTask = new VoteListTask(startDateTime.getClientDate(), endDateTime.getClientDate());
        voteListTask.setCallback(new VoteListTask.Callback() {
            @Override
            public void onResult(Boolean result, String message, List<Vote> voteList) {
                if (result) {
                    updateVoteList(voteList);
                } else {
                    Log.w(TAG, "failed to get vote list: " + message);
                }
            }
        });
        voteListTask.execute();
    }

    public void cancelVoteListTask() {
        if (voteListTask != null) {
            voteListTask.cancel(true);
            voteListTask = null;
        }
    }

    /**
     * Adapter that handles items in List View.
     */
    public class VoteListAdapter extends BaseAdapter {

        private final List<VoteListItem> itemList = new ArrayList<>();

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public VoteListItem getItem(int index) {

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
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.item_vote, root, false);
            }

            VoteListItem item = getItem(index);

            TextView headerTextView = view.findViewById(R.id.text_view_vote_item_header);
            headerTextView.setText(item.getHeader());

            TextView detailTextView = view.findViewById(R.id.text_view_vote_item_detail);
            detailTextView.setText(item.getDetail());

            return view;
        }

        void setItemList(List<Vote> voteList) {
            if (voteList != null) {
                itemList.clear();
                for (Vote vote : voteList) {
                    itemList.add(new VoteListItem(vote));
                }
            }
        }

    }

    /**
     * Class that holds voting information for the list.
     */
    public class VoteListItem {

        private final Vote vote;

        VoteListItem(Vote vote) {
            this.vote = vote;
        }

        public String getHeader() {

            if (vote == null) {
                return null;
            }

            String date = getDisplayDate();

            Integer tally = vote.getTally();
            if (tally == null || tally < 0) {
                tally = 0;
            }

            String vote;
            if (tally == 1) {
                vote = getString(R.string.vote);
            } else {
                vote = getString(R.string.votes);
            }

            return date + " (" + tally + " " + vote + ")";
        }

        public String getDetail() {

            if (vote == null) {
                return getString(R.string.na);
            }

            String film = vote.getFilm();
            if (film == null) {
                film = getString(R.string.na);
            }

            String time = getDisplayTime();
            if (time == null) {
                time = getString(R.string.na);
            }

            return film + " - " + time;
        }

        public String getDisplayDate() {

            if (vote == null) {
                return null;
            }

            ServerDateTime dateTime = new ServerDateTime(vote.getDate(), null);

            return dateTime.getDisplayDate();
        }

        public String getDisplayTime() {

            if (vote == null) {
                return null;
            }

            ServerDateTime dateTime = new ServerDateTime(vote.getDate(), vote.getTime());

            return dateTime.getDisplayTime();
        }

        public String getDate() {

            if (vote == null) {
                return null;
            }

            return vote.getDate();
        }

        public Vote getVote() {
            return vote;
        }

    }

    /**
     * Listener that handles when the user taps on a date.
     */
    public interface Listener {
        void onVoteDateTap(String date);
    }

    /**
     * Task to get Vote Date List from server.
     */
    public static class VoteListTask extends AsyncTask<Void, Void, Boolean> {

        private final String startDate;
        private final String endDate;

        private Callback asyncCallback;
        private Boolean asyncResult;
        private String asyncMessage;
        private final List<Vote> asyncVoteList = new ArrayList<>();

        VoteListTask(String startDate, String endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            AppInstance appInstance = AppInstance.getInstance();

            MobileClient client = new MobileClient(appInstance.getServer());

            asyncVoteList.clear();

            try {

                List<Vote> voteList = client.getFilmVoteList(appInstance.getClubName(), startDate, endDate);

                asyncVoteList.addAll(voteList);

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
            asyncResult = false;
            asyncMessage = "Request was canceled.";
            onPostExecute(false);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (asyncCallback != null) {
                asyncCallback.onResult(asyncResult, asyncMessage, asyncVoteList);
            }
        }

        void setCallback(Callback callback) {
            this.asyncCallback = callback;
        }

        interface Callback {

            void onResult(Boolean result, String message, List<Vote> voteList);

        }

    }

}
