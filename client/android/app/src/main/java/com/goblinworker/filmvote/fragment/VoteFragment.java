package com.goblinworker.filmvote.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.goblinworker.filmvote.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that displays upcoming voting dates for the club.
 */
public class VoteFragment extends Fragment {

    private static final String TAG = VoteFragment.class.getSimpleName();

    private OnInteractionListener listener;

    private VoteListAdapter listAdapter;

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

        List<VoteListItem> voteList = new ArrayList<>();
        voteList.add(new VoteListItem("Monday (1 Vote)", "The Matrix - 12:00 PM"));
        voteList.add(new VoteListItem("Tuesday (0 Votes)", "N/A"));
        voteList.add(new VoteListItem("Wednesday (0 Votes)", "N/A"));
        voteList.add(new VoteListItem("Thursday (1 Vote)", "Alien - 5:15 PM"));
        voteList.add(new VoteListItem("Friday (3 Votes)", "Fight Club - 7:30 PM"));
        voteList.add(new VoteListItem("Saturday (4 Vote)", "Blade Runner - 10:00 PM"));
        voteList.add(new VoteListItem("Sunday (1 Vote)", "Batman: Mask of the Phantasm - 12:00 PM"));

        // TODO: add real list
        listAdapter = new VoteListAdapter(voteList);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        View view = inflater.inflate(R.layout.fragment_vote, container, false);

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
        if (context instanceof OnInteractionListener) {
            listener = (OnInteractionListener) context;
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
     * Adapter that handles items in List View.
     */
    public class VoteListAdapter extends BaseAdapter {

        private final List<VoteListItem> itemList;

        public VoteListAdapter(List<VoteListItem> itemList) {
            this.itemList = itemList;
        }

        @Override
        public int getCount() {

            if (itemList == null) {
                return 0;
            }

            return itemList.size();
        }

        @Override
        public VoteListItem getItem(int index) {

            if (itemList == null || itemList.size() < index) {
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

    }

    /**
     * Class that holds voting information for the list.
     */
    public class VoteListItem {

        private final String header;
        private final String detail;

        public VoteListItem(String header, String detail) {
            this.header = header;
            this.detail = detail;
        }

        public String getDate() {
            // TODO: finish him!!!
            return "2000-01-01";
        }

        public String getHeader() {
            return header;
        }

        public String getDetail() {
            return detail;
        }

    }

    /**
     * Listener that handles when the user taps on a date.
     */
    public interface OnInteractionListener {
        void onVoteDateTap(String date);
    }

}
