package com.policeapp.framework;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.policeapp.R;
import com.policeapp.framework.Fragments.MasterFragment;
import com.policeapp.framework.Interfaces.LocationFetchListener;
import com.policeapp.framework.storage.Constants;
import com.policeapp.framework.storage.DataCacheManager;

public class BaseActivity extends AppCompatActivity {
    protected static final int NO_LAYOUT = -9;
    private final int FRAGMENT_STACK_COUNT = 1;
    private LayoutInflater mInflater;
    private FrameLayout mContainer;
    private RelativeLayout mParent;
    private Dialog progressDialog;
    private Toolbar mToolBar;
    private int mContainerId;
    private static int spinnerCount =0;
    private TextView mtitle,mLeftMenu; //,mRightMenu;
    private boolean savedInstanceStateDone;
    private ImageButton mBackChevron;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void initVariables() {
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mParent = (RelativeLayout) mInflater.inflate(R.layout.base_layout, null);
        mContainer = (FrameLayout) mParent.findViewById(R.id.app_container);
        mToolBar = mParent.findViewById(R.id.toolbar);
        mContainerId = R.id.app_container;
        mtitle = mParent.findViewById(R.id.page_title);
        mLeftMenu = mParent.findViewById(R.id.left_menu);
        mBackChevron = mParent.findViewById(R.id.back_chevron);
    }

    @Override
    public void setContentView(int layoutResID) {
        initVariables();
        if (layoutResID != NO_LAYOUT) {
            View childLayout = mInflater.inflate(layoutResID, null);
            mContainer.removeAllViews();
            mContainer.addView(childLayout);
            mContainerId = R.id.child_app_container;
        }
        super.setContentView(mParent);
    }

    protected void makeFragmentTransactionForMasterFrag(Fragment targetFragmet) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        manager.addOnBackStackChangedListener(getListener());

        if (!targetFragmet.isAdded() && manager.getBackStackEntryCount() > 0 && !manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1).getName().equalsIgnoreCase(targetFragmet.getClass().getSimpleName())) {
            transaction.add(mContainerId, targetFragmet, targetFragmet.getClass().getSimpleName());
            transaction.addToBackStack(targetFragmet.getClass().getSimpleName());
            transaction.commitAllowingStateLoss();
        } else if (!targetFragmet.isAdded() && manager.getBackStackEntryCount() == 0) {   //this is the case when no fragment is added to the stack.
            transaction.add(mContainerId, targetFragmet, targetFragmet.getClass().getSimpleName());
            transaction.addToBackStack(targetFragmet.getClass().getSimpleName());
            transaction.commitAllowingStateLoss();
        } else if (targetFragmet.isAdded() && !manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1).getName().equalsIgnoreCase(targetFragmet.getClass().getSimpleName())) {
            String backStateName = targetFragmet.getClass().getSimpleName();
                boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
                if (!fragmentPopped) {
                    transaction.replace(mContainerId, targetFragmet);
                    transaction.addToBackStack(backStateName);
                    transaction.commitAllowingStateLoss();
                }
        }
    }

    protected void makeFragmentTransaction(Fragment targetFragmet, boolean addToHistory) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(mContainerId, targetFragmet, targetFragmet.getClass().getSimpleName());
        if (addToHistory) {
            transaction.addToBackStack(targetFragmet.getClass().getSimpleName());
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() >= FRAGMENT_STACK_COUNT) {
            if(getCurrentFragment() instanceof MasterFragment)
            {
                if(!((MasterFragment) getCurrentFragment()).onBackPressed())
                {
                    if(manager.getBackStackEntryCount()>1)
                     manager.popBackStack();
                    else if (manager.getBackStackEntryCount()==1)
                        this.finish();
                }
            }
        }else
        {
            this.finish();
        }
    }

    protected Toolbar getToolbar() {
        return mToolBar;
    }

    public synchronized void showSpinner() {

        if(progressDialog == null) {
            progressDialog = new Dialog(this, android.R.style.Theme_Translucent);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rootView = inflater.inflate(R.layout.progress_layout,null);
            progressDialog.setContentView(rootView);
            progressDialog.setCancelable(false);
        }

        if(spinnerCount <0)
            spinnerCount =0;
        spinnerCount= spinnerCount+1;
        if(!this.isFinishing() && !progressDialog.isShowing() ) {
            progressDialog.show();
        }
    }

    public synchronized void hideSpinner() {
        spinnerCount = spinnerCount-1;
        if(progressDialog != null && progressDialog.isShowing() && spinnerCount==0) {
            progressDialog.dismiss();
        }
    }

    protected void setAppTitle(String title) {
        if(mtitle != null )
        {
            if(title != null )
            {
                mtitle.setText(title);
            }else
            {
                mtitle.setText("");
            }
        }
    }

    protected void setLeftMenu(String leftMenu, final Runnable runnable) {

        if(mBackChevron != null)
            mBackChevron.setVisibility(View.GONE);
        if(mLeftMenu != null )
        {
            mLeftMenu.setVisibility(View.VISIBLE);
            if(leftMenu != null )
            {
                mLeftMenu.setText(leftMenu);
            }else
            {
                mLeftMenu.setText("");
            }
            mLeftMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runnable.run();
                }
            });
        }
    }

    protected void clearLeftMenu()
    {
        mLeftMenu.setVisibility(View.GONE);
        mBackChevron.setVisibility(View.GONE);
    }


    protected void setLeftChevron(final Runnable runnable) {

        mLeftMenu.setVisibility(View.GONE);
        mBackChevron.setVisibility(View.VISIBLE);
        mBackChevron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               runnable.run();
            }
        });

    }

    protected void setLeftChevron() {

        mLeftMenu.setVisibility(View.GONE);
        mBackChevron.setVisibility(View.VISIBLE);
        mBackChevron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    protected Fragment getCurrentFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentManager.BackStackEntry backStackEntry = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1);
        Fragment fragmentOnTop = manager.findFragmentByTag(backStackEntry.getName());

        return fragmentOnTop;
    }

    protected Fragment findFragment(String fragmentName) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragmentOnTop = manager.findFragmentByTag(fragmentName);

        return fragmentOnTop;
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
                        if(fragment!= null)
                        fragment.onResume();
                    }
                }
            }
        };
        return result;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for(int i=0;i<permissions.length;i++)
        {
            switch (permissions[i])
            {
                case Manifest.permission.ACCESS_FINE_LOCATION:
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    boolean SHOULD_ASK_PERMISSION = DataCacheManager.getInstance(this).getData(Constants.CAN_CHECK_PERMISSION)==null || DataCacheManager.getInstance(this).getData(Constants.CAN_CHECK_PERMISSION)=="" ;
                    if(requestCode ==5 && SHOULD_ASK_PERMISSION) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            getLocationDetails();
                        } else {
                            DataCacheManager.getInstance(this).storeData(Constants.CAN_CHECK_PERMISSION, Constants.DENIED);
                        }
                    }
                    break;
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    {
                        getLocationDetails();
                    }
                    break;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void fetchLocation(LocationFetchListener fetchListener)
    {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        boolean CAN_ASK = DataCacheManager.getInstance(this).getData(Constants.CAN_CHECK_PERMISSION)!= Constants.DENIED;
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if(CAN_ASK) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 5);
            }
        }else {
            if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                Toast.makeText(BaseActivity.this,"Please enable Location Service for better accuracy.",Toast.LENGTH_SHORT).show();
            }

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //set the criteria for the location fetch
            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
                getLocationDetails();
            }
        }
    }


    private void getLocationDetails() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if((progressDialog != null) && progressDialog.isShowing() ){
            progressDialog.dismiss();
        }
    }

    @Override
    public void onStart() {
        savedInstanceStateDone = true;
        super.onStart();
    }

    @Override
    public void onStop() {
        savedInstanceStateDone = false;
        super.onStop();
    }

    public boolean isSavedInstanceStateDone() {
        return savedInstanceStateDone;
    }

    protected String ellipsize(String input, int maxLength) {
        String ellip = "...";
        if (input == null || input.length() <= maxLength
                || input.length() < ellip.length()) {
            return input;
        }
        return input.substring(0, maxLength).concat(ellip);
    }
}


