package com.goblinworker.filmvote.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goblinworker.filmvote.R;

/**
 * Fragment that indicates network progress.
 */
public class SignInWorkFragment extends Fragment {

    private static final String TAG = SignInWorkFragment.class.getSimpleName();

    /**
     * Required empty public constructor
     */
    public SignInWorkFragment() {
    }

    /**
     * Create a new instance of the fragment.
     *
     * @return SignInWorkFragment
     */
    public static SignInWorkFragment newInstance() {
        return new SignInWorkFragment();
    }

    /**
     * On create of fragment
     *
     * @param bundle Bundle Saved Instance State
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    /**
     * On create view for fragment.
     *
     * @param inflater  LayoutInflater
     * @param container ViewGroup
     * @param bundle    Bundle Saved Instance State
     * @return View of Fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_sign_in_work, container, false);
    }

    /**
     * Update the Progress Bar without changing Text View.
     *
     * @param isWorking Enable / Disable loading animation
     */
    public void updateProgressView(boolean isWorking) {
        updateProgressView(isWorking, null);
    }

    /**
     * Update the Progress Bar and Text View.
     *
     * @param isWorking Enable / Disable loading animation
     * @param message   Update the text under loading animation
     */
    public void updateProgressView(boolean isWorking, String message) {

        View view = getView();
        if (view == null) {
            Log.w(TAG, "failed to update progress view, fragment view not found");
            return;
        }

        ProgressBar progressBar = view.findViewById(R.id.sign_in_work_progress_bar);
        if (isWorking) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }

        TextView textView = view.findViewById(R.id.sign_in_work_text_view);
        if (message != null) {
            textView.setText(message);
        }
    }

}
