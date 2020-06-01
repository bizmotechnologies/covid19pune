package com.policeapp.source.postlogin.features.records.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.policeapp.R;
import com.policeapp.framework.Fragments.TouchSupressListner;
import com.policeapp.framework.Utils.Utils;
import com.policeapp.framework.network.AppNetworkManager;
import com.policeapp.framework.network.GenericResponseHandler;
import com.policeapp.framework.network.Interface.NetworkResponseHandler;
import com.policeapp.source.postlogin.HomeActivity;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationRecordBean;
import com.policeapp.source.postlogin.features.records.adapter.PatientListAdapter;
import com.policeapp.source.postlogin.features.records.bean.PatientByStationBean;
import com.policeapp.source.postlogin.features.records.bean.PatientByStationResponse;
import com.policeapp.source.postlogin.features.records.bean.PatientsContactedPersonsResponse;
import com.policeapp.source.postlogin.features.records.dialogs.ContactedPersonDialog;
import com.policeapp.source.postlogin.features.records.interfaces.PatientInterface;
import com.policeapp.source.postlogin.features.records.interfaces.RecordNetworkInterface;

import java.util.ArrayList;
import java.util.Objects;

import retrofit.RestAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDetailsFragment extends Fragment implements NetworkResponseHandler, PatientInterface {

    private View mParentView;
    private TextView mUserMessage,mTitle,mSubTitle;
    private RecyclerView mPatientListView;
    private StationRecordBean bean;
    private PatientListAdapter adapter;
    private String contactPersonName;
    public PatientDetailsFragment(StationRecordBean bean) {
        // Required empty public constructor
        this.bean = bean;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fragment_patinet_details, container, false);
        mParentView.setOnTouchListener(new TouchSupressListner());
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserMessage = view.findViewById(R.id.empty_message);
        mPatientListView = view.findViewById(R.id.recycler);
        mTitle = view.findViewById(R.id.title);
        mSubTitle = view.findViewById(R.id.sub_title);
        view.findViewById(R.id.close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mTitle.setText(bean.getStationName());
        mSubTitle.setText("Total cases found "+bean.getPatientCount());
        mPatientListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PatientListAdapter(new ArrayList<PatientByStationBean>(),this);
        mPatientListView.setAdapter(adapter);
        getRecords();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) Objects.requireNonNull(getActivity())).hideBottomMenu();
    }

    @Override
    public void onSuccess(Object responseBean) {
        if (responseBean instanceof PatientByStationResponse){
            if(((PatientByStationResponse)responseBean).isStatus()){
                adapter.setList(((PatientByStationResponse)responseBean).getData());
                if (adapter.getItemCount()>0){
                    mUserMessage.setVisibility(View.GONE);
                }
            }
        }else  if (responseBean instanceof PatientsContactedPersonsResponse){
            if(((PatientsContactedPersonsResponse)responseBean).isStatus()){

                if (((PatientsContactedPersonsResponse)responseBean).getData().size()>0){
                    ContactedPersonDialog dialog = new ContactedPersonDialog(((PatientsContactedPersonsResponse)responseBean).getData(),contactPersonName);
                    dialog.show(getFragmentManager(),dialog.getTag());
                }
            }else{
                Utils.showSnackBarNotificationError(((PatientsContactedPersonsResponse)responseBean).getMessage(),mParentView);
            }
        }
    }

    @Override
    public void onError(Object errorBean) {

    }

    @Override
    public void onClickPatient(String patientId, String name) {
        contactPersonName = name;
        getContactedRecords(patientId);
    }

    private void getRecords(){
        AppNetworkManager networkManager = new AppNetworkManager(new GenericResponseHandler(this, getActivity()));
        RestAdapter adapter = networkManager.getBaseAdapter(getActivity());
        RecordNetworkInterface service = adapter.create(RecordNetworkInterface.class);
        service.getPatientsByPoliceStation(bean.getId(),networkManager);
    }
    private void getContactedRecords(String patientId){
        AppNetworkManager networkManager = new AppNetworkManager(new GenericResponseHandler(this, getActivity()));
        RestAdapter adapter = networkManager.getBaseAdapter(getActivity());
        RecordNetworkInterface service = adapter.create(RecordNetworkInterface.class);
        service.getPatientsContactedPersons(patientId,networkManager);
    }
}
