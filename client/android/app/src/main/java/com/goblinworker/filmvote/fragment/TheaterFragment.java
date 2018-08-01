package com.goblinworker.filmvote.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.goblinworker.filmvote.R;
import com.goblinworker.filmvote.model.server.Theater;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that displays list of theaters in the club.
 */
public class TheaterFragment extends Fragment {

    private static final String TAG = TheaterFragment.class.getSimpleName();

    private static final String ARG_THEATER_LIST = "ARG_THEATER_LIST";

    private OnInteractionListener listener;

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

        List<TheaterListItem> theaterList = makeTheaterList();

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                if (listener != null) {
                    Theater theater = theaterListAdapter.getItem(index).getTheater();
                    showItemDialog(theater);
                }
            }
        });

        FloatingActionButton addTheaterButton = view.findViewById(R.id.floating_action_button_theater_add);
        addTheaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onTheaterFind();
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
     * Show a dialog for a specific theater info.
     * Allow user to delete theater.
     */
    protected void showItemDialog(final Theater theater) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(theater.getName());
        builder.setMessage(theater.getInfo());
        builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                if (listener != null) {
                    listener.onTheaterDelete(theater);
                }
            }
        });
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    /**
     * Make fake data until networking is added.
     */
    private List<TheaterListItem> makeTheaterList() {

        Theater theater1 = new Theater();
        theater1.setName("The Grand 16 - Pier Park");
        theater1.setLocation("Pier Park");
        theater1.setPhone("555-555-5555");
        theater1.setAddress("555 St. Road");
        theater1.setCity("Panama City");
        theater1.setState("FL");
        theater1.setZipcode("55555");

        Theater theater2 = new Theater();
        theater2.setName("Martin Theatre");
        theater2.setLocation("409 Harrison Ave");
        theater2.setAddress("555 St. Road");
        theater2.setCity("Panama City");
        theater2.setState("FL");
        theater2.setZipcode("55555");

        Theater theater3 = new Theater();
        theater3.setName("AMC Panama City 10");
        theater3.setLocation("4049 W 23rd St");
        theater3.setPhone("555-555-5555");
        theater3.setZipcode("55555");

        List<TheaterListItem> theaterList = new ArrayList<>();
        theaterList.add(new TheaterListItem(theater1));
        theaterList.add(new TheaterListItem(theater2));
        theaterList.add(new TheaterListItem(theater3));

        return theaterList;
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

        private final Theater theater;

        public TheaterListItem(Theater theater) {
            this.theater = theater;
        }

        public String getHeader() {

            if (theater == null) {
                return null;
            }

            return theater.getName();
        }

        public String getDetail() {

            if (theater == null) {
                return null;
            }

            return theater.getLocation();
        }

        // Getter / Setter

        public Theater getTheater() {
            return theater;
        }

    }

    /**
     * Listener that handles when the user taps Add Theater button.
     */
    public interface OnInteractionListener {

        void onTheaterFind();

        void onTheaterDelete(Theater theater);

    }

}
