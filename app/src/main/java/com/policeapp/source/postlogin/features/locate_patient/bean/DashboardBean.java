package com.policeapp.source.postlogin.features.locate_patient.bean;

import com.policeapp.framework.network.Bean.CommonResponse;

import java.io.Serializable;

public class DashboardBean extends CommonResponse implements Serializable {
    private String patients;
    private String stations;

    public String getPatients() {
        return patients;
    }

    public String getStations() {
        return stations;
    }
}
