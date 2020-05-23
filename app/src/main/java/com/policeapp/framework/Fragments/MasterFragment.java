package com.policeapp.framework.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.policeapp.R;
import com.policeapp.framework.Interfaces.OplatoBackEventListner;

/**
 * Created by Bizmo Technologies
 */
public abstract class MasterFragment extends Fragment implements OplatoBackEventListner {

    private View mParentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mParentView = inflater.inflate(R.layout.master_fragment,null);
        init();
        return mParentView;
    }

    private void init() {
        if(getActivity()!= null) {
            if(getChildFragmentManager().getBackStackEntryCount()>0)
            {
                getChildFragmentManager().popBackStackImmediate(getChildFragmentManager().getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            initialize();
        }
    }

    public void loadFragment(Fragment targetFragmet, boolean addtoHistory)
    {
        FragmentManager manager = getChildFragmentManager();
        manager.addOnBackStackChangedListener(getListener());
        FragmentTransaction transaction = manager.beginTransaction();

        if (addtoHistory) {
            //This will add the fragment to the history for back navigation
            transaction.add(R.id.master_container, targetFragmet,targetFragmet.getClass().getSimpleName());
            transaction.addToBackStack(targetFragmet.getClass().getSimpleName());
        }else
        {
            transaction.add(R.id.master_container, targetFragmet);
        }
        transaction.commit();
        //relaease the resources
        manager = null;
        transaction = null;
    }

    @Override
    public boolean onBackPressed() {

        FragmentManager manager = getChildFragmentManager();
        if(manager.getBackStackEntryCount() >1 )
        {

            //handle the backstack from the master fragment now by delegating the control to Fragment
            if(getCurrentFragment() instanceof OplatoBackEventListner)
            {
                if(!((OplatoBackEventListner)getCurrentFragment()).onBackPressed())
                {
                    if(manager.getBackStackEntryCount()>1)
                        manager.popBackStack();
                    else if (manager.getBackStackEntryCount()==1)
                        return false;
                }
            }else
            {
                manager.popBackStack();
            }

        }else
        {
            return false;
        }

        return true;
    }

    /**
     * Use this method to initlialize the child fragments
     */
    public abstract void initialize();

    public Fragment getCurrentFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        Fragment currentFragment = fragmentManager
                .findFragmentByTag(fragmentTag);
        return currentFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager manager = getChildFragmentManager();
        if (manager != null) {
            int index = getChildFragmentManager().getBackStackEntryCount() - 1;
            if(index >=0) {
                FragmentManager.BackStackEntry backEntry = getChildFragmentManager().getBackStackEntryAt(index);
                String tag = backEntry.getName();
                Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
                fragment.onResume();
            }
        }
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getChildFragmentManager();
                if (manager != null) {
                    int index = getChildFragmentManager().getBackStackEntryCount() - 1;
                    if(index >=0) {
                        FragmentManager.BackStackEntry backEntry = getChildFragmentManager().getBackStackEntryAt(index);
                        String tag = backEntry.getName();
                        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
                        //removed on resume call from here and is handled from the SNA for trending.Other [ages dont need this call as they are created everytime
                        if(fragment != null) {
                            fragment.onResume();
                        }
                    }
                }
            }
        };
        return result;
    }


}
