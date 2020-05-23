package com.policeapp.source.prelogin.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.policeapp.R;
import com.policeapp.framework.Utils.Utils;
import com.policeapp.framework.network.AppNetworkManager;
import com.policeapp.framework.network.Bean.CommonResponse;
import com.policeapp.framework.network.Bean.ErrorBean;
import com.policeapp.framework.network.GenericResponseHandler;
import com.policeapp.framework.network.Interface.NetworkResponseHandler;
import com.policeapp.framework.storage.Constants;
import com.policeapp.framework.storage.DataCacheManager;
import com.policeapp.source.postlogin.HomeActivity;
import com.policeapp.source.prelogin.interfaces.PreLoginServiceInterface;

import retrofit.RestAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements NetworkResponseHandler, View.OnClickListener {
    private EditText mUserName,mPassword;
    private PreLoginMasterFragment mMasterFragment;
    private Button mLoginButton;
    int PERMISSION_ID = 44;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginButton    = view.findViewById(R.id.btn_login);
        mUserName       = view.findViewById(R.id.user_name);
        mPassword       = view.findViewById(R.id.password);
        mLoginButton.setOnClickListener(this);
        requestLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMasterFragment = (PreLoginMasterFragment)( getActivity().getSupportFragmentManager().findFragmentByTag(PreLoginMasterFragment.class.getSimpleName()));
    }

    @Override
    public void onSuccess(Object responseBean) {
        if(responseBean instanceof CommonResponse)
        {
            if (((CommonResponse) responseBean).isStatus()){
                DataCacheManager.getInstance(getContext()).storeData(Constants.IS_LOGIN,"LOGIN");
                Intent postLoginIntent = new Intent(getActivity(), HomeActivity.class);
                startActivity(postLoginIntent);
                getActivity().finish();
            }else {
                Utils.showSnackBarNotificationError(((CommonResponse) responseBean).getMessage(),mPassword);
            }
        }
    }

    @Override
    public void onError(Object errorBean) {
        if(errorBean instanceof ErrorBean){
            Utils.showSnackBarNotificationError(((ErrorBean) errorBean).getMessage(),mPassword);
        }
    }

    private void signing(){
        AppNetworkManager networkManager = new AppNetworkManager(new GenericResponseHandler(this, getActivity()));
        RestAdapter adapter = networkManager.getBaseAdapter(getActivity());
        PreLoginServiceInterface service = adapter.create(PreLoginServiceInterface.class);
        service.signing(mUserName.getText().toString().trim(),mPassword.getText().toString().trim(), networkManager);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){
            if (Utils.isEmptyOrNull(mUserName)){
                Utils.showSnackBarNotificationError("Username is required",mUserName);
                return;
            }

            if (Utils.isEmptyOrNull(mPassword)){
                Utils.showSnackBarNotificationError("Password is required",mPassword);
                return;
            }
            signing();
        }
    }

    private void requestLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
            } else {
                Utils.showSnackBarNotificationGreen("For tracking patient location, GPS needs to be turned on from system settings menu. Please enable GPS and try again",mUserName);
            }
        } else {
            requestPermissions();
        }
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    private void requestPermissions(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_Dialog)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for tracking patient location/area. Without this permission you can't track patient area")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(
                                    getActivity(),
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                    PERMISSION_ID
                            );
                        }})
                    .setNegativeButton("No", null).show();
        }else{
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ID
            );
        }
    }
}
