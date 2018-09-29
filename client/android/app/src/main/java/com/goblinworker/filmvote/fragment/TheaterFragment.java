package com.goblinworker.filmvote.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.goblinworker.filmvote.app.AppInstance;
import com.goblinworker.filmvote.model.server.ServerDateTime;
import com.goblinworker.filmvote.model.server.Theater;
import com.goblinworker.filmvote.network.MobileClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fragment that displays list of theaters in the club.
 */
public class TheaterFragment extends Fragment {

    private static final String TAG = TheaterFragment.class.getSimpleName();

    private Listener listener;

    private TheaterListAdapter theaterListAdapter;
    private TheaterListTask theaterListTask;
    private RemoveTheaterTask removeTheaterTask;

    /**
     * Required empty public constructor.
     */
    public TheaterFragment() {
    }

    /**
     * Create a new instance of the fragment.
     *
     * @return TheaterFragment
     */
    public static TheaterFragment newInstance() {
        return new TheaterFragment();
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

        View view = inflater.inflate(R.layout.fragment_theater, container, false);

        theaterListAdapter = new TheaterListAdapter();

        ListView listView = view.findViewById(R.id.theater_list_view);
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

        FloatingActionButton addTheaterButton = view.findViewById(R.id.theater_floating_action_button);
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
        startTheaterListTask();
    }

    /**
     * Stop the fragment.
     */
    @Override
    public void onStop() {
        super.onStop();
        cancelTheaterListTask();
        cancelTheaterRemoveTask();
    }

    protected void updateTheaterList(List<Theater> theaterList) {

        View view = getView();
        if (view == null) {
            Log.w(TAG, "failed to update theater list, invalid list");
            return;
        }

        TextView messageTextView = view.findViewById(R.id.theater_text_view);
        ListView listLinearLayout = view.findViewById(R.id.theater_list_view);

        if (theaterList == null || theaterList.isEmpty()) {
            messageTextView.setVisibility(View.VISIBLE);
            listLinearLayout.setVisibility(View.GONE);
        } else {
            messageTextView.setVisibility(View.GONE);
            listLinearLayout.setVisibility(View.VISIBLE);
        }

        theaterListAdapter.setItemList(theaterList);
        theaterListAdapter.notifyDataSetChanged();
    }

    protected void showFailDialog(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.failed_to_send_request);

        if (message == null || message.isEmpty()) {
            builder.setMessage(R.string.please_try_again_later);
        } else {
            builder.setMessage(message);
        }

        builder.setPositiveButton(R.string.ok, null);
        builder.show();
    }

    protected void showItemDialog(final Theater theater) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(theater.getName());
        builder.setMessage(theater.getInfo());
        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                startTheaterRemoveTask(theater);
            }
        });
        builder.setPositiveButton(R.string.ok, null);
        builder.show();
    }

    protected void startTheaterListTask() {

        cancelTheaterListTask();

        // TODO: suppress data reloads

        theaterListTask = new TheaterListTask();
        theaterListTask.setCallback(new TheaterListTask.Callback() {
            @Override
            public void onResult(Boolean result, String message, List<Theater> theaterList) {
                if (result) {
                    updateTheaterList(theaterList);
                } else {
                    showFailDialog(message);
                }
            }
        });
        theaterListTask.execute();
    }

    protected void cancelTheaterListTask() {
        if (theaterListTask != null) {
            theaterListTask.cancel(true);
            theaterListTask = null;
        }
    }

    protected void startTheaterRemoveTask(Theater theater) {

        cancelTheaterRemoveTask();

        removeTheaterTask = new RemoveTheaterTask(theater);
        removeTheaterTask.setCallback(new RemoveTheaterTask.Callback() {
            @Override
            public void onResult(Boolean result, String message) {
                if (result) {
                    updateTheaterList(null);
                    startTheaterListTask();
                } else {
                    showFailDialog(message);
                }
            }
        });
        removeTheaterTask.execute();
    }

    protected void cancelTheaterRemoveTask() {
        if (removeTheaterTask != null) {
            removeTheaterTask.cancel(true);
            removeTheaterTask = null;
        }
    }

    /**
     * Adapter that handles items in List View.
     */
    public class TheaterListAdapter extends BaseAdapter {

        private final List<TheaterListItem> itemList = new ArrayList<>();

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public TheaterListItem getItem(int index) {

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
                view = inflater.inflate(R.layout.item_theater, root, false);
            }

            TheaterListItem item = getItem(index);

            TextView headerTextView = view.findViewById(R.id.theater_item_text_view_header);
            headerTextView.setText(item.getHeader());

            TextView detailTextView = view.findViewById(R.id.theater_item_text_view_detail);
            detailTextView.setText(item.getDetail());

            return view;
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
     * Class that holds theater information for the list.
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
     * Listener that handles when the user taps Add Theater button.
     */
    public interface Listener {

        void onTheaterFind();

    }

    /**
     * Task to get list of theaters from server.
     */
    public static class TheaterListTask extends AsyncTask<Void, Void, Boolean> {

        private Callback asyncCallback;
        private Boolean asyncResult;
        private String asyncMessage;
        private final List<Theater> asyncTheaterList = new ArrayList<>();

        @Override
        protected Boolean doInBackground(Void... voids) {

            AppInstance appInstance = AppInstance.getInstance();

            ServerDateTime dateTime = new ServerDateTime();

            MobileClient client = new MobileClient(appInstance.getServer());

            asyncTheaterList.clear();

            try {

                Map<String, Theater> theaterMap = client.getTheatersForDate(
                        appInstance.getClubName(), dateTime.getClientDate());

                asyncTheaterList.addAll(theaterMap.values());

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
                asyncCallback.onResult(asyncResult, asyncMessage, asyncTheaterList);
            }
        }

        void setCallback(Callback callback) {
            asyncCallback = callback;
        }

        interface Callback {

            void onResult(Boolean result, String message, List<Theater> theaterList);

        }

    }

    /**
     * Task that sends remove theater request to server.
     */
    public static class RemoveTheaterTask extends AsyncTask<Void, Void, Boolean> {

        private final Theater theater;

        private Callback asyncCallback;
        private Boolean asyncResult;
        private String asyncMessage;

        RemoveTheaterTask(Theater theater) {
            this.theater = theater;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            AppInstance appInstance = AppInstance.getInstance();

            MobileClient client = new MobileClient(appInstance.getServer());

            try {

                client.removeTheater(appInstance.getClubName(), theater.getName());

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
            onPostExecute(true);
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
