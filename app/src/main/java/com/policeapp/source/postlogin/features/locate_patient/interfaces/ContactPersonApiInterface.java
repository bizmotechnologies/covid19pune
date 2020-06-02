package com.policeapp.source.postlogin.features.locate_patient.interfaces;

import com.policeapp.framework.network.Bean.CommonResponse;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationResponseBean;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ContactPersonApiInterface {
    @FormUrlEncoded
    @POST("/Login_api/patientInfo")
    void addPatient(@Field("name") String name, @Field("address") String address, @Field("emailId") String emailId, @Field("city") String city, @Field("pincode") String pincode, @Field("police_station") String police_station, @Field("latitude") String latitude, @Field("longitude") String longitude, @Field("patient_age") String patient_age, @Field("health_issue") String health_issue, @Field("contacted_person") String contacted_person, @Field("contact_number") String contact_number, @Field("address_line2") String address_line2,@Field("gender") String gender, Callback<CommonResponse> response);

    @GET("/Dashboard_api/getPatientCountStationWise")
    void getAllStations(Callback<StationResponseBean> response);

}
