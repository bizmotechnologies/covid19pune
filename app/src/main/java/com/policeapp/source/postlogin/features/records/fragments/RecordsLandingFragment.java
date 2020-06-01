package com.policeapp.source.postlogin.features.records.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.policeapp.R;
import com.policeapp.framework.Fragments.TouchSupressListner;
import com.policeapp.framework.network.AppNetworkManager;
import com.policeapp.framework.network.GenericResponseHandler;
import com.policeapp.framework.network.Interface.NetworkResponseHandler;
import com.policeapp.framework.storage.DataCacheManager;
import com.policeapp.source.postlogin.HomeActivity;
import com.policeapp.source.postlogin.features.home.interfaces.HomeNetworkAPIInterface;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationRecordBean;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationRecordResponseBean;
import com.policeapp.source.postlogin.features.locate_patient.fragments.LocatePatientMasterFragment;
import com.policeapp.source.postlogin.features.records.adapter.RecordsAdapter;
import com.policeapp.source.postlogin.features.records.interfaces.RecordInterface;
import com.policeapp.source.prelogin.LoginActivity;

import java.util.ArrayList;
import java.util.Objects;

import retrofit.RestAdapter;

public class RecordsLandingFragment extends Fragment implements NetworkResponseHandler, RecordInterface {
    private RecyclerView mRecordListView;
    private View mParentView;
    private RecordsAdapter recordsAdapter;
    private SwipeRefreshLayout refreshLayout;
    private RecordsMasterFragment mMasterFragment;
    public RecordsLandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fragment_records_landing, container, false);
        mParentView.setOnTouchListener(new TouchSupressListner());
        return mParentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) Objects.requireNonNull(getActivity())).showBottomMenu();
        mMasterFragment = (RecordsMasterFragment)( getActivity().getSupportFragmentManager().findFragmentByTag(RecordsMasterFragment.class.getSimpleName()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecordListView = view.findViewById(R.id.record_recycler);
        mRecordListView.setLayoutManager(new LinearLayoutManager(getContext()));
        recordsAdapter = new RecordsAdapter(new ArrayList<StationRecordBean>(),this);
        mRecordListView.setAdapter(recordsAdapter);
        refreshLayout = view.findViewById(R.id.swipe_refresh);

        view.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCacheManager.getInstance(getContext()).clearAllData();
                Intent postLoginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(postLoginIntent);
                getActivity().finish();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecords();
            }
        });
        getRecords();
    }

    private void getRecords(){
        AppNetworkManager networkManager = new AppNetworkManager(new GenericResponseHandler(this, getActivity()));
        RestAdapter adapter = networkManager.getBaseAdapter(getActivity());
        HomeNetworkAPIInterface service = adapter.create(HomeNetworkAPIInterface.class);
        service.getDashboardStationWiseList(networkManager);
    }

    @Override
    public void onSuccess(Object responseBean) {
        refreshLayout.setRefreshing(false);
        if (responseBean instanceof StationRecordResponseBean){
            if(((StationRecordResponseBean)responseBean).isStatus()){
                recordsAdapter.setRecordList(((StationRecordResponseBean)responseBean).getData());
            }
        }
    }

    @Override
    public void onError(Object errorBean) {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(StationRecordBean bean) {
        mMasterFragment.loadFragment(new PatientDetailsFragment(bean),true);
    }
}
