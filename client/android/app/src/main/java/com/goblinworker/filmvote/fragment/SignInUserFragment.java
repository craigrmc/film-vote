package com.goblinworker.filmvote.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goblinworker.filmvote.R;

/**
 * Fragment to sign in / up for a user.
 */
public class SignInUserFragment extends Fragment {

    private static final String TAG = SignInUserFragment.class.getSimpleName();

    private static final String ARG_CLUB_NAME = "ARG_CLUB_NAME";

    private OnInteractionListener listener;

    private String clubName;

    /**
     * Required empty public constructor.
     */
    public SignInUserFragment() {
    }

    /**
     * Create a new instance of the fragment.
     *
     * @return SignInUserFragment
     */
    public static SignInUserFragment newInstance(String clubName) {
        SignInUserFragment fragment = new SignInUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CLUB_NAME, clubName);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * On create for fragment.
     *
     * @param bundle Saved Instance State Bundle
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            clubName = getArguments().getString(ARG_CLUB_NAME);
        }
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

        View view = inflater.inflate(R.layout.fragment_sign_in_user, container, false);

        Button signInButton = view.findViewById(R.id.sign_in_user_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = ((Button) view).getText().toString();
                if (listener != null) {
                    listener.onSignInUser(clubName, userName);
                }
            }
        });

        Button signUpButton = view.findViewById(R.id.sign_up_user_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = ((Button) view).getText().toString();
                if (listener != null) {
                    listener.onSignUpUser(clubName, userName);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            listener = (OnInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * Interface when user hits next.
     */
    public interface OnInteractionListener {

        void onSignInUser(String clubName, String userName);

        void onSignUpUser(String clubName, String userName);

    }

}
