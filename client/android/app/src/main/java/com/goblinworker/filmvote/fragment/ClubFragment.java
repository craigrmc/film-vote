package com.goblinworker.filmvote.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goblinworker.filmvote.R;

/**
 * Fragment that shows User's name / club / theaters.
 */
public class ClubFragment extends Fragment {

    private static final String TAG = ClubFragment.class.getSimpleName();

    /**
     * Required empty public constructor
     */
    public ClubFragment() {
    }

    /**
     * Create a new instance of the fragment.
     *
     * @return ClubFragment
     */
    public static ClubFragment newInstance() {
        return new ClubFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_club, container, false);
    }

}
