package com.policeapp.source.postlogin.features.home.fragments;

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

import com.google.android.gms.maps.model.Dash;
import com.policeapp.R;
import com.policeapp.framework.Fragments.TouchSupressListner;
import com.policeapp.framework.network.AppNetworkManager;
import com.policeapp.framework.network.GenericResponseHandler;
import com.policeapp.framework.network.Interface.NetworkResponseHandler;
import com.policeapp.source.postlogin.HomeActivity;
import com.policeapp.source.postlogin.features.home.StationCasesAdapter;
import com.policeapp.source.postlogin.features.home.interfaces.HomeNetworkAPIInterface;
import com.policeapp.source.postlogin.features.locate_patient.bean.DashboardBean;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationRecordBean;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationRecordResponseBean;
import com.policeapp.source.postlogin.features.locate_patient.interfaces.ContactPersonApiInterface;

import java.util.ArrayList;
import java.util.Objects;

import retrofit.RestAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeLandingFragment extends Fragment implements NetworkResponseHandler {
    private StationCasesAdapter stationCasesAdapter;
    private TextView stationCount,totalCases;
    private RecyclerView mRecordListView;
    private View mParentView;
    public HomeLandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fragment_home_landing, container, false);
        mParentView.setOnTouchListener(new TouchSupressListner());
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stationCount = view.findViewById(R.id.stationCount);
        totalCases = view.findViewById(R.id.totalCases);
        mRecordListView = view.findViewById(R.id.record_recycler);
        mRecordListView.setLayoutManager(new LinearLayoutManager(getContext()));
        stationCasesAdapter = new StationCasesAdapter(new ArrayList<StationRecordBean>());
        mRecordListView.setAdapter(stationCasesAdapter);
        stationCount.setText("-");
        totalCases.setText("-");

    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) Objects.requireNonNull(getActivity())).showBottomMenu();
        getDashboardCount();
        getDashboardStationWiseList();
    }

    private void getDashboardCount(){
        AppNetworkManager networkManager = new AppNetworkManager(new GenericResponseHandler(this, getActivity()));
        RestAdapter adapter = networkManager.getBaseAdapter(getActivity());
        HomeNetworkAPIInterface service = adapter.create(HomeNetworkAPIInterface.class);
        service.getDashboardCount(networkManager);
    }

    private void getDashboardStationWiseList(){
        AppNetworkManager networkManager = new AppNetworkManager(new GenericResponseHandler(this, getActivity()));
        RestAdapter adapter = networkManager.getBaseAdapter(getActivity());
        HomeNetworkAPIInterface service = adapter.create(HomeNetworkAPIInterface.class);
        service.getDashboardStationWiseList(networkManager);
    }

    @Override
    public void onSuccess(Object responseBean) {
        if (responseBean instanceof DashboardBean){
            if(((DashboardBean)responseBean).isStatus()){
                stationCount.setText(((DashboardBean)responseBean).getStations());
                totalCases.setText(((DashboardBean)responseBean).getPatients());
            }
        }else if (responseBean instanceof StationRecordResponseBean){
            if(((StationRecordResponseBean)responseBean).isStatus()){
                stationCasesAdapter.setRecordList(((StationRecordResponseBean)responseBean).getData());
            }
        }
    }

    @Override
    public void onError(Object errorBean) {

    }
}
