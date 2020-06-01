package com.policeapp.source.postlogin.features.records.interfaces;

import com.policeapp.source.postlogin.features.records.bean.PatientByStationResponse;
import com.policeapp.source.postlogin.features.records.bean.PatientsContactedPersonsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface RecordNetworkInterface {
    @GET("/Dashboard_api/getPatientsByPoliceStation")
    void getPatientsByPoliceStation(@Query("station_id") String station_id, Callback<PatientByStationResponse> response);

    @GET("/Dashboard_api/getPatientsContactedPersons")
    void getPatientsContactedPersons(@Query("patient_id") String patient_id, Callback<PatientsContactedPersonsResponse> response);
}
