package com.policeapp.source.postlogin.features.records.fragments;

import com.policeapp.framework.Fragments.MasterFragment;

public class RecordsMasterFragment extends MasterFragment {
    /**
     * Use this method to initlialize the child fragments
     */
    @Override
    public void initialize() {
        loadFragment(new RecordsLandingFragment(),true);
    }
}
