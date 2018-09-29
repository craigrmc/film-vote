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
 * Fragment to sign in / up for a user.
 */
public class SignInUserFragment extends Fragment {

    private static final String TAG = SignInUserFragment.class.getSimpleName();

    private Listener listener;

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
    public static SignInUserFragment newInstance() {
        return new SignInUserFragment();
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

        View view = inflater.inflate(R.layout.fragment_sign_in_user, container, false);

        final EditText userEditText = view.findViewById(R.id.sign_in_user_edit_text);

        final Button nextButton = view.findViewById(R.id.sign_in_user_button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userEditText.getText().toString();
                if (listener != null) {
                    listener.onUserNext(userName);
                }
            }
        });

        final Button backButton = view.findViewById(R.id.sign_in_user_button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onUserBack();
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
     * Interface when user hits next.
     */
    public interface Listener {

        void onUserBack();

        void onUserNext(String userName);

    }

}
