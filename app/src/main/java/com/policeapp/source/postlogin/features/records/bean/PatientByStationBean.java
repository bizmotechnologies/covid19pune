package com.policeapp.source.postlogin.features.records.bean;

import java.io.Serializable;

public class PatientByStationBean implements Serializable {
    private String station_id;
    private String patient_id;
    private String patientName;
    private String stationName;
    private String address;
    private String emailId;
    private String city;
    private String pincode;
    private String latitude;
    private String longitude;
    private String patient_age;
    private String health_issue;
    private String status;

    public String getStation_id() {
        return station_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getStationName() {
        return stationName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPatient_age() {
        return patient_age;
    }

    public String getHealth_issue() {
        return health_issue;
    }

    public String getStatus() {
        return status;
    }
}
