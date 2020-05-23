package com.policeapp.source.postlogin.features.locate_patient.bean;

import java.io.Serializable;

public class StationRecordBean implements Serializable {
    private String id;
    private String stationName;
    private String patientCount;

    public String getId() {
        return id;
    }

    public String getStationName() {
        return stationName;
    }

    public String getPatientCount() {
        return patientCount;
    }
}
