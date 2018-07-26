package com.goblinworker.filmvote.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.goblinworker.filmvote.R;
import com.goblinworker.filmvote.model.server.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that allows user to vote for a film on a specific date.
 */
public class VoteDateActivity extends AppCompatActivity {

    private static final String TAG = VoteDateActivity.class.getSimpleName();

    private static final String EXTRA_DATE = "EXTRA_DATE";

    private VoteDateListAdapter listAdapter;

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

        List<VoteDateListItem> voteDateItemList = makeVoteDateList();

        // TODO: add real list
        listAdapter = new VoteDateListAdapter(voteDateItemList);

        ListView listView = findViewById(R.id.vote_date_list_view);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                onFilmSelect(listAdapter.getItem(index));
            }
        });
    }

    /**
     * Display show time selection dialog for a film.
     *
     * @param item Selected Theater / Film Item
     */
    protected void onFilmSelect(final VoteDateListItem item) {

        if (item == null) {
            return;
        }

        final VoteTimeListAdapter showTimeListAdapter = new VoteTimeListAdapter(item.getFilm());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(item.getHeader());
        builder.setAdapter(showTimeListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                onTimeSelect(item.getTheater(), item.getFilm(), showTimeListAdapter.getItem(index));
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    /**
     * Sends vote to server.
     *
     * @param film Selected Film
     * @param time Selected Show Time
     */
    protected void onTimeSelect(String theater, Film film, String time) {
        // TODO: finish him!!!
    }

    /**
     * Make fake data until networking is added.
     */
    private List<VoteDateListItem> makeVoteDateList() {

        List<VoteDateListItem> itemList = new ArrayList<>();

        Film film1 = new Film();
        film1.setName("The Matrix");
        film1.getShowTimeList().add("12:05 PM");
        film1.getShowTimeList().add("3:30 PM");
        film1.getShowTimeList().add("5:00 PM");
        film1.getShowTimeList().add("7:30 PM");
        film1.getShowTimeList().add("10:45 PM");

        Film film2 = new Film();
        film2.setName("Blade Runner");
        film2.getShowTimeList().add("12:00 PM");
        film2.getShowTimeList().add("3:30 PM");
        film2.getShowTimeList().add("5:30 PM");
        film2.getShowTimeList().add("7:15 PM");
        film2.getShowTimeList().add("10:05 PM");

        Film film3 = new Film();
        film3.setName("Blade Runner");
        film3.getShowTimeList().add("12:30 PM");
        film3.getShowTimeList().add("3:00 PM");
        film3.getShowTimeList().add("5:30 PM");
        film3.getShowTimeList().add("7:05 PM");
        film3.getShowTimeList().add("10:30 PM");

        itemList.add(new VoteDateListItem("The Grand 16", film1));
        itemList.add(new VoteDateListItem("The Grand 16", film2));
        itemList.add(new VoteDateListItem("The Grand 16", film3));
        itemList.add(new VoteDateListItem("AMC Panama City 10", film1));
        itemList.add(new VoteDateListItem("AMC Panama City 10", film2));
        itemList.add(new VoteDateListItem("AMC Panama City 10", film3));
        itemList.add(new VoteDateListItem("Martin Theater", film1));
        itemList.add(new VoteDateListItem("Martin Theater", film2));
        itemList.add(new VoteDateListItem("Martin Theater", film3));

        return itemList;
    }

    /**
     * Adapter that handles film items in List View.
     */
    public class VoteDateListAdapter extends BaseAdapter {

        private final List<VoteDateListItem> itemList;

        public VoteDateListAdapter(List<VoteDateListItem> itemList) {
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
        public VoteDateListItem getItem(int index) {

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

    }

    /**
     * Adapter that handles show times in List View.
     */
    public class VoteTimeListAdapter extends BaseAdapter {

        private final Film film;

        public VoteTimeListAdapter(Film film) {
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
     * Class that holds voting information for the list.
     */
    public class VoteDateListItem {

        // TODO: private final String date;???
        private final String theater;
        private final Film film;

        public VoteDateListItem(String theater, Film film) {
            this.theater = theater;
            this.film = film;
        }

        public String getHeader() {
            return theater + " - " + film.getName();
        }

        public String getDetail() {
            return film.getShowTimes();
        }

        // Getter / Setter

        public String getTheater() {
            return theater;
        }

        public Film getFilm() {
            return film;
        }

    }

}
