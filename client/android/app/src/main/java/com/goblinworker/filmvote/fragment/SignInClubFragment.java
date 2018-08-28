package com.goblinworker.filmvote.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.goblinworker.filmvote.R;

/**
 * Fragment to sign in / up to a club.
 */
public class SignInClubFragment extends Fragment {

    private static final String TAG = SignInClubFragment.class.getSimpleName();

    private Listener listener;

    /**
     * Required empty public constructor.
     */
    public SignInClubFragment() {
    }

    /**
     * Create a new instance of the fragment.
     *
     * @return SignInClubFragment
     */
    public static SignInClubFragment newInstance() {
        return new SignInClubFragment();
    }

    /**
     * On create for fragment.
     *
     * @param bundle Saved Instance State Bundle
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
     * @param bundle    Saved Instance State Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        View view = inflater.inflate(R.layout.fragment_sign_in_club, container, false);

        final EditText clubEditText = view.findViewById(R.id.sign_in_club_edit_text);

        final Button nextButton = view.findViewById(R.id.sign_in_club_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = clubEditText.getText().toString();
                if (listener != null) {
                    listener.onClubNext(clubName);
                }
            }
        });

        final Button backButton = view.findViewById(R.id.sign_in_club_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClubBack();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * Interface when the user hits next.
     */
    public interface Listener {

        void onClubBack();

        void onClubNext(String clubName);

    }

}
