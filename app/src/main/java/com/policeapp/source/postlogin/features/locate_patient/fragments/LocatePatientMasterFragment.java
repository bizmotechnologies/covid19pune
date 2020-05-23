package com.policeapp.source.postlogin.features.locate_patient.fragments;

import com.policeapp.framework.Fragments.MasterFragment;

public class LocatePatientMasterFragment extends MasterFragment {
    /**
     * Use this method to initlialize the child fragments
     */
    @Override
    public void initialize() {
        loadFragment(new LocatePatientLandingFragment(),true);
    }
}
