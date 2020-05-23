package com.policeapp.source.prelogin.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.policeapp.R;

public class SplashFragment extends Fragment {
    private static int SPLASH_TIME_OUT = 2000;
    private PreLoginMasterFragment mMasterFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash_fragment, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMasterFragment = (PreLoginMasterFragment)( getActivity().getSupportFragmentManager().findFragmentByTag(PreLoginMasterFragment.class.getSimpleName()));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMasterFragment.loadFragment(new LoginFragment(),true);
            }
        }, SPLASH_TIME_OUT);
    }
}
