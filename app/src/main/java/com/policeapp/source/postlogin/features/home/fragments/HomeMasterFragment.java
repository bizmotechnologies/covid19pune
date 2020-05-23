package com.policeapp.source.postlogin.features.home.fragments;

import com.policeapp.framework.Fragments.MasterFragment;

public class HomeMasterFragment extends MasterFragment {
    /**
     * Use this method to initlialize the child fragments
     */
    @Override
    public void initialize() {
        loadFragment(new HomeLandingFragment(),true);
    }
}
