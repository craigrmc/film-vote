package com.goblinworker.filmvote.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goblinworker.filmvote.R;

/**
 * Fragment that displays upcoming voting dates.
 */
public class VoteFragment extends Fragment {

    private static final String TAG = VoteFragment.class.getSimpleName();

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

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_vote, container, false);
    }

}
