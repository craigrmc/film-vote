package com.goblinworker.filmvote.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.goblinworker.filmvote.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that displays list of theaters in the club.
 */
public class TheaterFragment extends Fragment {

    private static final String ARG_THEATER_LIST = "ARG_THEATER_LIST";

    private OnFragmentInteractionListener listener;

    private TheaterListAdapter theaterListAdapter;

    private String theaterList;

    /**
     * Required empty public constructor.
     */
    public TheaterFragment() {
    }

    /**
     * Create a new instance of the fragment.
     *
     * @param theaterList List of Theaters
     * @return TheaterFragment
     */
    public static TheaterFragment newInstance(String theaterList) {
        TheaterFragment fragment = new TheaterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_THEATER_LIST, theaterList);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initialize Fragment.
     *
     * @param bundle Saved Bundle State
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            theaterList = getArguments().getString(ARG_THEATER_LIST);
        }

        List<TheaterListItem> theaterList = new ArrayList<>();
        theaterList.add(new TheaterListItem("The Grand 16 - Pier Park", "Pier Park"));
        theaterList.add(new TheaterListItem("Martin Theatre", "409 Harrison Ave"));
        theaterList.add(new TheaterListItem("AMC Panama City 10", "4049 W 23rd St"));

        // TODO: add real list
        theaterListAdapter = new TheaterListAdapter(theaterList);
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

        View view = inflater.inflate(R.layout.fragment_theater, container, false);

        ListView listView = view.findViewById(R.id.list_view_theater);
        listView.setAdapter(theaterListAdapter);

        FloatingActionButton addTheaterButton = view.findViewById(R.id.floating_action_button_theater_add);
        addTheaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAddTheater();
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
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
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
    public class TheaterListAdapter extends BaseAdapter {

        private final List<TheaterListItem> itemList;

        /**
         * Constructor to initialize items.
         *
         * @param itemList List of Theaters
         */
        public TheaterListAdapter(List<TheaterListItem> itemList) {
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
        public TheaterListItem getItem(int index) {

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
                view = inflater.inflate(R.layout.item_theater, root, false);
            }

            TheaterListItem item = getItem(index);

            TextView headerTextView = view.findViewById(R.id.text_view_theater_item_header);
            headerTextView.setText(item.getHeader());

            TextView detailTextView = view.findViewById(R.id.text_view_theater_item_detail);
            detailTextView.setText(item.getDetail());

            return view;
        }

    }

    /**
     * Class that holds theater information for the list.
     */
    public class TheaterListItem {

        private final String header;
        private final String detail;

        public TheaterListItem(String header, String detail) {
            this.header = header;
            this.detail = detail;
        }

        public String getHeader() {
            return header;
        }

        public String getDetail() {
            return detail;
        }

    }

    /**
     * Listener that handles when the user taps Add Theater button.
     */
    public interface OnFragmentInteractionListener {
        void onAddTheater();
    }

}
