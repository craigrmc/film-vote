package com.goblinworker.filmvote.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.goblinworker.filmvote.R;
import com.goblinworker.filmvote.app.AppInstance;
import com.goblinworker.filmvote.model.server.Theater;
import com.goblinworker.filmvote.network.MobileClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Activity to find and add new theater to the club.
 */
public class TheaterFindActivity extends AppCompatActivity {

    private static final String TAG = TheaterFindActivity.class.getSimpleName();

    private TheaterListAdapter theaterListAdapter;
    private FindTheaterTask findTheaterTask;
    private AddTheaterTask addTheaterTask;

    /**
     * Create a new intent for activity.
     *
     * @param context Activity Context
     * @return Intent to Start Activity
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, TheaterFindActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_find);

        SearchView searchView = findViewById(R.id.theater_find_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startFindTheaterTask(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        theaterListAdapter = new TheaterListAdapter();

        ListView theaterListView = findViewById(R.id.theater_find_list_view);
        theaterListView.setAdapter(theaterListAdapter);
        theaterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showItemDialog(theaterListAdapter.getItem(position).getTheater());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelFindTheaterTask();
        cancelAddTheaterTask();
    }

    protected void updateTheaterList(List<Theater> theaterList) {

        TextView findTextView = findViewById(R.id.theater_find_text_view);
        ListView findListView = findViewById(R.id.theater_find_list_view);

        if (theaterList == null || theaterList.isEmpty()) {
            findTextView.setVisibility(View.VISIBLE);
            findListView.setVisibility(View.GONE);
        } else {
            findTextView.setVisibility(View.GONE);
            findListView.setVisibility(View.VISIBLE);
        }

        theaterListAdapter.setItemList(theaterList);
        theaterListAdapter.notifyDataSetChanged();
    }

    protected void showItemDialog(final Theater theater) {

        String add = getString(R.string.add);
        String cancel = getString(R.string.cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(theater.getName());
        builder.setMessage(theater.getInfo());
        builder.setNegativeButton(add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                startAddTheaterTask(theater);
            }
        });
        builder.setPositiveButton(cancel, null);
        builder.show();
    }

    protected void showFailDialog(String message) {

        String title = getString(R.string.failed_to_send_request);
        String defaultMessage = getString(R.string.please_try_again_later);
        String ok = getString(R.string.ok);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        if (message == null || message.isEmpty()) {
            builder.setMessage(defaultMessage);
        } else {
            builder.setMessage(message);
        }

        builder.setPositiveButton(ok, null);
        builder.show();
    }

    protected void startFindTheaterTask(String location) {

        cancelFindTheaterTask();

        findTheaterTask = new FindTheaterTask(location);
        findTheaterTask.setCallback(new FindTheaterTask.Callback() {
            @Override
            public void onResult(Boolean result, String message, List<Theater> theaterList) {
                if (result) {
                    updateTheaterList(theaterList);
                } else {
                    showFailDialog(message);
                }
            }
        });
        findTheaterTask.execute();
    }

    protected void cancelFindTheaterTask() {
        if (findTheaterTask != null) {
            findTheaterTask.cancel(true);
            findTheaterTask = null;
        }
    }

    protected void startAddTheaterTask(Theater theater) {

        cancelAddTheaterTask();

        addTheaterTask = new AddTheaterTask(theater);
        addTheaterTask.setCallback(new AddTheaterTask.Callback() {
            @Override
            public void onResult(Boolean result, String message) {
                if (result) {
                    finish();
                } else {
                    showFailDialog(message);
                }
            }
        });
        addTheaterTask.execute();
    }

    protected void cancelAddTheaterTask() {
        if (addTheaterTask != null) {
            addTheaterTask.cancel(true);
            addTheaterTask = null;
        }
    }

    /**
     * Adapter that handles theater list view.
     */
    public class TheaterListAdapter extends BaseAdapter {

        private final List<TheaterListItem> itemList = new ArrayList<>();

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public TheaterListItem getItem(int position) {

            if (itemList.size() < position) {
                return null;
            }

            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(R.layout.item_theater, parent, false);
            }

            TheaterListItem item = getItem(position);

            TextView headerTextView = convertView.findViewById(R.id.text_view_theater_item_header);
            headerTextView.setText(item.getHeader());

            TextView detailTextView = convertView.findViewById(R.id.text_view_theater_item_detail);
            detailTextView.setText(item.getDetail());

            return convertView;
        }

        void setItemList(List<Theater> theaterList) {
            if (theaterList != null) {
                itemList.clear();
                for (Theater theater : theaterList) {
                    itemList.add(new TheaterListItem(theater));
                }
            }
        }

    }

    /**
     * Object that hold information for list items.
     */
    public class TheaterListItem {

        private final Theater theater;

        TheaterListItem(Theater theater) {
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
     * Task that requests location based collection of theaters.
     */
    public static class FindTheaterTask extends AsyncTask<Void, Void, Boolean> {

        private final String location;

        private Callback asyncCallback;
        private Boolean asyncResult;
        private String asyncMessage;
        private final List<Theater> asyncTheaterList = new ArrayList<>();

        FindTheaterTask(String location) {
            this.location = location;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            AppInstance appInstance = AppInstance.getInstance();

            MobileClient client = new MobileClient(appInstance.getServer());

            asyncTheaterList.clear();

            try {

                Map<String, Theater> theaterMap = client.getTheatersForLocation(location);

                asyncTheaterList.addAll(theaterMap.values());

                asyncResult = true;
                asyncMessage = "Request was successful";
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
                asyncCallback.onResult(asyncResult, asyncMessage, asyncTheaterList);
            }
        }

        void setCallback(Callback callback) {
            this.asyncCallback = callback;
        }

        interface Callback {
            void onResult(Boolean result, String message, List<Theater> theaterList);
        }

    }

    /**
     * Task that sends add theater request to server.
     */
    public static class AddTheaterTask extends AsyncTask<Void, Void, Boolean> {

        private final Theater theater;

        private Callback asyncCallback;
        private Boolean asyncResult;
        private String asyncMessage;

        AddTheaterTask(Theater theater) {
            this.theater = theater;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            AppInstance appInstance = AppInstance.getInstance();

            MobileClient client = new MobileClient(appInstance.getServer());

            try {

                client.addTheater(appInstance.getClubName(), theater);

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
                asyncCallback.onResult(asyncResult, asyncMessage);
            }
        }

        void setCallback(Callback callback) {
            this.asyncCallback = callback;
        }

        interface Callback {
            void onResult(Boolean result, String message);
        }

    }

}
