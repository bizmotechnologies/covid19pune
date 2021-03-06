package com.policeapp.source.postlogin.features.locate_patient.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.policeapp.R;
import com.policeapp.framework.Fragments.TouchSupressListner;
import com.policeapp.framework.Utils.Utils;
import com.policeapp.framework.network.AppNetworkManager;
import com.policeapp.framework.network.Bean.CommonResponse;
import com.policeapp.framework.network.Bean.ErrorBean;
import com.policeapp.framework.network.GenericResponseHandler;
import com.policeapp.framework.network.Interface.NetworkResponseHandler;
import com.policeapp.framework.storage.DataCacheManager;
import com.policeapp.source.postlogin.HomeActivity;
import com.policeapp.source.postlogin.features.locate_patient.adapter.ContactedPersonAdapter;
import com.policeapp.source.postlogin.features.locate_patient.bean.ContactedPersonBean;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationBean;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationResponseBean;
import com.policeapp.source.postlogin.features.locate_patient.interfaces.ContactPersonApiInterface;
import com.policeapp.source.postlogin.features.locate_patient.interfaces.ContactPersonListener;
import com.policeapp.source.prelogin.LoginActivity;

import java.util.ArrayList;
import java.util.Objects;

import retrofit.RestAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocatePatientLandingFragment extends Fragment  implements View.OnClickListener, NetworkResponseHandler, ContactPersonListener {
    private EditText mPatientName, mPatientAddress1, mPatientAddress2, mPatientIssue,  mPatientAge, mCity, mPincode, mEmail, mContactNumber;
    private Spinner mPoliceStation;
    private TextView mLatitude, mLongitude;
    private Button mAddContactedPerson, mSavePerson, mGetLocation;
    private RecyclerView mContactedPersonList;
    private ArrayList<ContactedPersonBean> contactPersonList;
    private LocatePatientMasterFragment mMasterFragment;
    private ContactedPersonAdapter adapter;
    private String Latitude,Longitude;
    private LinearLayout mLocationContainer;
    private GoogleMap googleMap;
    private ArrayList<StationBean> stationBeans;
    private View mParentView;
    private Spinner gender;
    private final int LOCATION_PERMISSION = 1002;
    public LocatePatientLandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fragment_locate_patient_landing, container, false);
        mParentView.setOnTouchListener(new TouchSupressListner());
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPatientName            = view.findViewById(R.id.patient_name);
        mPatientAddress1        = view.findViewById(R.id.txt_address_line1);
        mPatientAddress2        = view.findViewById(R.id.txt_address_line2);
        mPoliceStation          = view.findViewById(R.id.txt_police_station);
        mPatientAge             = view.findViewById(R.id.txt_patient_age);
        mPatientIssue           = view.findViewById(R.id.txt_health_issue);
        mLatitude               = view.findViewById(R.id.txt_latitude);
        mLongitude              = view.findViewById(R.id.txt_longitude);
        mAddContactedPerson     = view.findViewById(R.id.btn_add_contacted_person);
        mSavePerson             = view.findViewById(R.id.btn_save);
        mContactedPersonList    = view.findViewById(R.id.recycler);
        mGetLocation            = view.findViewById(R.id.btn_generate_location);
        mLocationContainer      = view.findViewById(R.id.txtPersonLocationContainer);
        mEmail                  = view.findViewById(R.id.txt_email_id);
        mContactNumber          = view.findViewById(R.id.txt_contact_number);
        mCity                   = view.findViewById(R.id.txt_city);
        mPincode                = view.findViewById(R.id.txt_pincode);
        gender                  = view.findViewById(R.id.gender);

        mAddContactedPerson.setOnClickListener(this);
        mSavePerson.setOnClickListener(this);
        mGetLocation.setOnClickListener(this);
        stationBeans = new ArrayList<>();
        contactPersonList = new ArrayList<>();
        adapter = new ContactedPersonAdapter(this,contactPersonList,false);
        mContactedPersonList.setLayoutManager(new LinearLayoutManager(getContext()));
        mContactedPersonList.setAdapter(adapter);
        getAllStations();
//        mLocationContainer.setVisibility(View.GONE);

        view.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCacheManager.getInstance(getContext()).clearAllData();
                Intent postLoginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(postLoginIntent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) Objects.requireNonNull(getActivity())).showBottomMenu();

        mMasterFragment = (LocatePatientMasterFragment)( getActivity().getSupportFragmentManager().findFragmentByTag(LocatePatientMasterFragment.class.getSimpleName()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_contacted_person:
                addContactPerson();
                break;
            case R.id.btn_save:
                actionSavePerson();
                break;
            case R.id.btn_generate_location:
                requestPermissions();
                break;
        }
    }

    private void requestPermissions(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert).create(); //Read Update
            alertDialog.setTitle("Permission needed");
            alertDialog.setMessage("This permission is needed for tracking patient location/area. Without this permission you can't track patient area");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
                }
            });
            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();

        }else {
            mMasterFragment.loadFragment(new LocationFragment(this),true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION) {
            if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED &&  grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                mMasterFragment.loadFragment(new LocationFragment(this),true);
            }else {
                Utils.showSnackBarNotificationError("Permission denied. To grant permission go to app settings and enable location permission",mPatientName);
            }
        }
    }

    @Override
    public void onSuccess(Object responseBean) {
        if(responseBean instanceof StationResponseBean){
            ArrayList<String> stations = new ArrayList<>();
            if (((StationResponseBean) responseBean).isStatus()){
                stationBeans = ((StationResponseBean) responseBean).getData();
                for (int i=0; i<stationBeans.size(); i++){
                    stations.add(stationBeans.get(i).getStationName());
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getContext(), android.R.layout.simple_spinner_item,
                                stations); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                mPoliceStation.setAdapter(spinnerArrayAdapter);
            }
        }else if(responseBean instanceof CommonResponse)
        {
            if (((CommonResponse) responseBean).isStatus()){
                Utils.showSnackBarNotificationGreen(((CommonResponse) responseBean).getMessage(),mPatientName);
                resetField();
            }else {
                Utils.showSnackBarNotificationError(((CommonResponse) responseBean).getMessage(),mPatientName);
            }
        }
    }

    private void resetField() {
        mPatientName.setText("");
        mPatientAddress1.setText("");
        mPatientAddress2.setText("");
        mEmail.setText("");
        mContactNumber.setText("");
        mCity.setText("");
        mPincode.setText("");
        mPatientAge.setText("");
        mPatientIssue.setText("");
        adapter.setList(new ArrayList<ContactedPersonBean>());
    }

    @Override
    public void onError(Object errorBean) {
        if(errorBean instanceof ErrorBean){
            Utils.showSnackBarNotificationError(((ErrorBean) errorBean).getMessage(),mPatientAge);
        }
    }

    private void addContactPerson(){

        ContactPersonFragment fragment = new  ContactPersonFragment(adapter.getList(),this);
        fragment.show(getFragmentManager(),"");
//        mMasterFragment.loadFragment(new ContactPersonFragment(adapter.getList(),this),true);
    }

    private void actionSavePerson(){
        String gen = gender.getSelectedItemPosition() == 0?"M":gender.getSelectedItemPosition() == 1?"F":"O";

        String station = mPoliceStation.getSelectedItem().toString();
        String stationId = "0";
        for (int i=0 ; i<stationBeans.size(); i++){
            if(station.equals(stationBeans.get(i).getStationName())){
                stationId =stationBeans.get(i).getId();
                break;
            }
        }

        if (Utils.isEmptyOrNull(mPatientName)){
            Utils.showSnackBarNotificationError("Patient name is required",mPatientName);
            return;
        }

        if (Utils.isEmptyOrNull(mContactNumber)){
            Utils.showSnackBarNotificationError("Patient contact number is required",mPatientName);
            return;
        }

        if (Utils.isEmptyOrNull(mPatientAddress1)){
            Utils.showSnackBarNotificationError("Patient address required",mPatientName);
            return;
        }

        if (Utils.isEmptyOrNull(mPatientAge)){
            Utils.showSnackBarNotificationError("Patient age required",mPatientAge);
            return;
        }

        if (mLatitude.getText().toString().equals("-")){
            Utils.showSnackBarNotificationError("Patient location is required",mPatientAge);
            return;
        }

        String name = mPatientName.getText().toString().trim();
        String address = mPatientAddress1.getText().toString().trim();
        String address1 = mPatientAddress2.getText().toString().trim();
        String latitude = mLatitude.getText().toString().trim();
        String longitude = mLongitude.getText().toString().trim();
        String age = mPatientAge.getText().toString().trim();
        String health = mPatientIssue.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String contact = mContactNumber.getText().toString().trim();
        String city = mCity.getText().toString().trim();
        String pincode = mPincode.getText().toString().trim();

        Gson gson = new GsonBuilder().create();
        JsonArray contactPerson = gson.toJsonTree(adapter.getList()).getAsJsonArray();

        Log.v("name",name);
        Log.v("address",address);
        Log.v("address1",address1);
        Log.v("latitude",latitude);
        Log.v("longitude",longitude);
        Log.v("station",stationId);
        Log.v("age",age);
        Log.v("health",health);
        Log.v("email",email);
        Log.v("contact",contact);
        Log.v("city",city);
        Log.v("pincode",pincode);
        Log.v("contactPerson",contactPerson.toString());
        Log.v("gender",gen);

        AppNetworkManager networkManager = new AppNetworkManager(new GenericResponseHandler(this, getActivity()));
        RestAdapter adapter = networkManager.getBaseAdapter(getActivity());
        ContactPersonApiInterface service = adapter.create(ContactPersonApiInterface.class);
        service.addPatient(name,address,email,city,pincode,stationId,latitude,longitude,age,health,contactPerson.toString(),contact,address1,gen, networkManager);
    }

    private void selectPoliceStation(){

    }

    @Override
    public void onDelete(int position) {

    }

    @Override
    public void onEdit(int position, ContactedPersonBean bean) {

    }

    @Override
    public void onDialogDismiss(ArrayList<ContactedPersonBean> list) {
        this.contactPersonList = list;
        adapter.setList(list);
        ((HomeActivity) Objects.requireNonNull(getActivity())).showBottomMenu();
    }

    @Override
    public void onLocationCapture(String Latitude, String Longitude) {
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        mLatitude.setText(Latitude);
        mLongitude.setText(Longitude);
//        mLocationContainer.setVisibility(View.VISIBLE);
    }

    private void getAllStations(){
        AppNetworkManager networkManager = new AppNetworkManager(new GenericResponseHandler(this, getActivity()));
        RestAdapter adapter = networkManager.getBaseAdapter(getActivity());
        ContactPersonApiInterface service = adapter.create(ContactPersonApiInterface.class);
        service.getAllStations(networkManager);
    }
}
