package com.goblinworker.filmvote.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goblinworker.filmvote.R;

/**
 * Fragment that introduces user to app on sign in.
 */
public class SignInGetStartedFragment extends Fragment {

    private OnInteractListener listener;

    /**
     * Required empty public constructor.
     */
    public SignInGetStartedFragment() {
    }

    /**
     * Create a new instance of the fragment.
     *
     * @return SignInGetStartedFragment
     */
    public static SignInGetStartedFragment newInstance() {
        return new SignInGetStartedFragment();
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
     * @param inflater  Layout Inflater
     * @param container Container View Group
     * @param bundle    Saved Instance State Bundle
     * @return Fragment View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        View view = inflater.inflate(R.layout.fragment_sign_in_get_started, container, false);

        Button getStartedButton = view.findViewById(R.id.button_sign_in_get_started);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onGetStarted();
                }
            }
        });

        return view;
    }

    /**
     * On attach for fragment.
     *
     * @param context Activity Context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractListener) {
            listener = (OnInteractListener) context;
        }
    }

    /**
     * On detach for fragment.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * Interface when user hits Get Started.
     */
    public interface OnInteractListener {

        void onGetStarted();

    }

}
