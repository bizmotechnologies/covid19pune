package com.policeapp.source.postlogin.features.home.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.policeapp.R;
import com.policeapp.source.postlogin.HomeActivity;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeLandingFragment extends Fragment {

    public HomeLandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_landing, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) Objects.requireNonNull(getActivity())).showBottomMenu();
    }
}
