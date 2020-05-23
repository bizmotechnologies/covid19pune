package com.policeapp.source.prelogin.fragment;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.policeapp.framework.Fragments.MasterFragment;

public class PreLoginMasterFragment extends MasterFragment {

    @Override
    public void initialize() {
        loadFragment(new SplashFragment(),false);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboard(getActivity());
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
