package com.policeapp.source.postlogin.features.logout.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.policeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutLandingFragment extends Fragment {

    public LogoutLandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout_landing, container, false);
    }
}
