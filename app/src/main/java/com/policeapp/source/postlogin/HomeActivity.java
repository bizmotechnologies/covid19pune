package com.policeapp.source.postlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.policeapp.R;
import com.policeapp.framework.BaseActivity;
import com.policeapp.framework.Fragments.MasterFragment;
import com.policeapp.framework.storage.DataCacheManager;
import com.policeapp.source.postlogin.features.home.fragments.HomeMasterFragment;
import com.policeapp.source.postlogin.features.locate_patient.fragments.LocatePatientMasterFragment;
import com.policeapp.source.postlogin.features.logout.fragments.LogoutMasterFragment;
import com.policeapp.source.prelogin.LoginActivity;

import java.util.HashMap;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLowerMenuContainer;
    private HashMap<Object, Object> mNavigationMap;
    private Button mLocatePatient,mLogout;
    private ImageButton mHome;
    private enum TABS_NAMES{LOCATE_PATIENT,HOME,LOGOUT}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setStatusBarColor(getResources().getColor(R.color.colorWhite));
        initUiComponents();
    }


    private void initUiComponents() {
//        makeFragmentTransaction(new LocatePatientMasterFragment(), true);
        mLowerMenuContainer = findViewById(R.id.lower_menu_container);
        prepareNavigationMap();
        loadLowerStaticMenu();
        loadDefaultFragment();
    }

    private void loadLowerStaticMenu() {
        mLocatePatient  = findViewById(R.id.locate_patient);
        mHome           = findViewById(R.id.home_tab);
        mLogout         = findViewById(R.id.logout);
        mLocatePatient.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mLogout.setOnClickListener(this);
    }

    private void loadDefaultFragment() {
        highlightLowerMenu(TABS_NAMES.HOME);
        makeFragmentTransactionForMasterFrag((MasterFragment) mNavigationMap.get(TABS_NAMES.HOME));
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
    }

    private HashMap prepareNavigationMap() {

        if (mNavigationMap == null) {
            mNavigationMap = new HashMap<>();
            mNavigationMap.put(TABS_NAMES.LOCATE_PATIENT, new LocatePatientMasterFragment());
            mNavigationMap.put(TABS_NAMES.HOME, new HomeMasterFragment());
            mNavigationMap.put(TABS_NAMES.LOGOUT, new LogoutMasterFragment());
        }
        return mNavigationMap;
    }

    /**
     * This method will set the highlighted color to the lower menu
     * @param name name of the lower menu to be selected
     */
    private void highlightLowerMenu(TABS_NAMES name) {
        //reset all the backgrounds
        mLocatePatient.setSelected(false);
        mHome.setSelected(false);
        mLogout.setSelected(false);

        switch (name)
        {
            case LOCATE_PATIENT:
                mLocatePatient.setSelected(true);
                break;
            case HOME:
                mHome.setSelected(true);
                break;
            default:
                mLogout.setSelected(true);
                break;
        }
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();
                if (manager != null) {
                    int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
                    if(index >=0) {
                        FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
                        String tag = backEntry.getName();
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                        if(fragment instanceof LocatePatientMasterFragment){
                            highlightLowerMenu(TABS_NAMES.LOCATE_PATIENT);
                        }else if(fragment instanceof HomeMasterFragment){
                            highlightLowerMenu(TABS_NAMES.HOME);
                        }else if(fragment instanceof LogoutMasterFragment){
                            highlightLowerMenu(TABS_NAMES.LOGOUT);
                        }
                    }
                }
            }
        };
        return result;
    }

    public void setTitle(String title) {
        super.setAppTitle(title);
    }

    public void setLeftChevron() {
        super.setLeftChevron();
    }
    public void setLeftChevron(Runnable runnable) {
        super.setLeftChevron(runnable);
    }

    public void clearLeftMenu()
    {
        super.clearLeftMenu();
    }

    public void setLeftMenu(String leftMenu, Runnable runnable) {
        super.setLeftMenu(leftMenu, runnable);
    }


    public void hideRightMenu() {
//        super.setRightMenu(null, null);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void hideKeyboard()
    {
        try {
            InputMethodManager input = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public MasterFragment getCurrentFragment()
    {
        return (MasterFragment) super.getCurrentFragment();
    }

    /**
     * This method will recreate the lower menu and load it again .This is to
     * support the language translation when the language is changed from profile.
     */
    public void reloadLowerMenu()
    {
//        loadLowerMenu();
//        highlightLowerMenu("Family");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
    }

    public void hideBottomMenu() {
        mLowerMenuContainer.setVisibility(View.GONE);
        mHome.setVisibility(View.GONE);
    }

    public void showBottomMenu() {
        mLowerMenuContainer.setVisibility(View.VISIBLE);
        mHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.locate_patient:
                highlightLowerMenu(TABS_NAMES.LOCATE_PATIENT);
                navigateToLocatePatient();
                break;
            case R.id.home_tab:
                highlightLowerMenu(TABS_NAMES.HOME);
                navigateToHome();
                break;
            case R.id.logout:
                DataCacheManager.getInstance(this).clearAllData();
                Intent postLoginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(postLoginIntent);
                finish();
//                highlightLowerMenu(TABS_NAMES.LOGOUT);
//                navigateToLogout();
                break;
        }
    }

    public void navigateToLocatePatient() {
        makeFragmentTransactionForMasterFrag((Fragment) mNavigationMap.get(TABS_NAMES.LOCATE_PATIENT));
    }

    public void navigateToHome() {
        makeFragmentTransactionForMasterFrag((Fragment) mNavigationMap.get(TABS_NAMES.HOME));
    }

    public void navigateToLogout() {
        makeFragmentTransactionForMasterFrag((Fragment) mNavigationMap.get(TABS_NAMES.LOGOUT));
    }


    public void setStatusBarColor(int color)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
}
