package com.policeapp.source.postlogin.features.logout.fragments;

import com.policeapp.framework.Fragments.MasterFragment;

public class LogoutMasterFragment extends MasterFragment {
    /**
     * Use this method to initlialize the child fragments
     */
    @Override
    public void initialize() {
        loadFragment(new LogoutLandingFragment(),true);
    }
}
