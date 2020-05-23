package com.policeapp.source.postlogin.features.home.interfaces;

import com.policeapp.source.postlogin.features.locate_patient.bean.DashboardBean;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationRecordResponseBean;

import retrofit.Callback;
import retrofit.http.GET;

public interface HomeNetworkAPIInterface {

    @GET("/Dashboard_api/getDashboardStat")
    void getDashboardCount(Callback<DashboardBean> response);

    @GET("/Dashboard_api/getPatientCountStationWise")
    void getDashboardStationWiseList(Callback<StationRecordResponseBean> response);
}
