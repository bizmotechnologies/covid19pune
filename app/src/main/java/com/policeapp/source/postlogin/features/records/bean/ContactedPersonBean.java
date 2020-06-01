package com.policeapp.source.postlogin.features.records.bean;

import java.io.Serializable;

public class ContactedPersonBean implements Serializable {
    private String patient_id;
    private String police_station;
    private String patientName;
    private String address;
    private String emailId;
    private String status;

    public String getPatient_id() {
        return patient_id;
    }

    public String getPolice_station() {
        return police_station;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getStatus() {
        return status;
    }
}
